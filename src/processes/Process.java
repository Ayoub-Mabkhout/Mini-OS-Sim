/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processes;

import end.user.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import gui.OSFrame;
/**
 *
 * @author A.Mabkhout
 */
public class Process implements Runnable{
    public String p_ID;
    public enum Status{READY,WAITING,RUNNING,DONE};
    public Status status;
    public long endTime;
    public long responseTime;
    public long arrivalTime;
    long startTime; // start time of a the current subprocess, 
                    //used in case a process is interrupted in the middle of one of its subprocesses
    public long burstTime;
    public long remainingTime;
    public PageTable pageTable;
    // private long sequence[][] ;
    private ArrayList<long[]> sequence;
    Iterator<long[]> sequenceIterator;
    public long[] currentSubprocess;

    public String getP_ID() {
        return p_ID;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getBurstTime() {
        return burstTime;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public PageTable getPageTable() {
        return pageTable;
    }

    public ArrayList<long[]> getSequence() {
        return sequence;
    }

    public Status getStatus() {
        return status;
    }
    


    public Process(String ID, PageTable pageTable, ArrayList<long[]> sequence){
        p_ID = ID;
        arrivalTime = SystemClock.getTime();
        this.pageTable = pageTable;
        this.sequence = sequence;
        
        burstTime = 0;
        for(long[] s : sequence) burstTime += s[1];
        remainingTime = burstTime;
        
        sequenceIterator = sequence.iterator();
        currentSubprocess = sequenceIterator.next();
        
        if(OSFrame.verbose){
           System.out.println("Process " + p_ID + " arrived at " + arrivalTime);
       }
        
        status = Status.READY;
    }
    
    private long resumeSubprocessTime(){
        long timeSpent = burstTime - remainingTime;
        long timeSum = 0;
        for(long[] subprocess : sequence){
            timeSum += subprocess[1];
            if(subprocess == currentSubprocess){
                System.err.println("::: Remaining Burst is "+ (timeSum - timeSpent) +":::");
                return timeSum - timeSpent;
            }


        }
        return 0;
    }
    
    
    private void runSubprocess(long remainingBurst, long[] subprocess) throws InterruptedException{
        
        if(OSFrame.verbose)
            System.out.println("Loading page of process " + pageTable.getPage((int) subprocess[0]).getP_ID() + " at t = " + SystemClock.getTime());
        status = Status.WAITING;
        OSFrame.mg.loadPage(pageTable.getPage((int) subprocess[0]));
        
        if(OSFrame.verbose)
            System.out.println("Finished Loading page of process " + pageTable.getPage((int) subprocess[0]).getP_ID() + " at t = " +  SystemClock.getTime());
        
        status = Status.RUNNING;
        startTime = SystemClock.getTime();        
        SystemClock.waitFor(remainingBurst);
    }
    
    private void runSequence() throws InterruptedException{
        
        while(sequenceIterator.hasNext() || !isDone()){
            long remainingBurst = resumeSubprocessTime();
            if(remainingBurst == 0){
                currentSubprocess = sequenceIterator.next();
                remainingBurst = resumeSubprocessTime();                
            }


            
            /*
            if(operating.system.OperatingSystem.verbose)
                System.out.println("Running SUBPROCESS " + subprocess[0] + " for a duration of " + remainingBurst);
            */

            runSubprocess(remainingBurst,currentSubprocess);
            
            remainingTime -= remainingBurst;
            /*
            if(operating.system.OperatingSystem.verbose)
                System.out.println("Remaining time in process is " + remainingTime);
            */

        }
            remainingTime = 0;
            
            System.err.println("Finished running sequence at " + SystemClock.getTime());
            endTime = SystemClock.getTime();
            gui.ProgramState.turnaroundTimes.add(endTime-arrivalTime);
            gui.ProgramState.waitingTimes.add(endTime-arrivalTime-burstTime);
    }
    
    public boolean isDone(){
        boolean done = remainingTime == 0 || status == Status.DONE;
        return done;
    }
    
    public Page getCurrentPage(){
        return pageTable.getPage((int)currentSubprocess[0]);
    }
    
    @Override
    public synchronized void run() {
            if(!OSFrame.running)
                return;
            
            startTime = SystemClock.getTime();
            try{
                
                if(OSFrame.verbose){
                    System.out.println("\n>--------------------------------------------------------------------------------<");
                    
                    if(remainingTime == burstTime){
                        System.out.println("Running process " + p_ID + " at time t = " + SystemClock.getTime());
                        responseTime = SystemClock.getTime();
                        gui.ProgramState.responseTimes.add(responseTime-arrivalTime); 
                    }
                    else
                        System.out.println("Resuming process " + p_ID + " at time t = " + SystemClock.getTime() + 
                                " with a remaining burst time of " + remainingTime);
                    System.out.println(">--------------------------------------------------------------------------------<\n");
                }
                
                runSequence();
                status = Status.DONE;
                gui.OSFrame.completedProcesses = gui.OSFrame.completedProcesses + 1;
                notifyAll();
                
                 
            } catch(InterruptedException ie){
                long endTime = SystemClock.getTime();
                
                // System.err.println("start time = " + startTime + " and end time = " + endTime);
                if(status.equals(Status.RUNNING))
                    remainingTime = remainingTime - (endTime - startTime);
                
                if(OSFrame.verbose){
                    System.out.println("\n>--------------------------------------------------------------------------------<");                    
                    System.out.println("Process " + p_ID + " interrupted with remaining time " + remainingTime);
                    System.out.println(">--------------------------------------------------------------------------------<\n");                    
                }

                status = Status.READY;
                if(remainingTime < 0){
                    remainingTime = 0;
                    status = Status.DONE;
                    System.err.println("Process Interrupted but got done");
                }
                
                notifyAll();
                Thread.currentThread().interrupt();
            
        } 
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("------------------------------------------------------------------------");
        str.append("\nProcess " + p_ID + " with the following sequence:");
        int i = 0;
        str.append(sequenceString());
        str.append("\nTotal burst time is " + burstTime+"\n");
        str.append("------------------------------------------------------------------------");
        return str.toString();
    }
    
    public String sequenceString(){
        StringBuilder str = new StringBuilder();
        int i = 0;
        for(long[] sub : sequence){
            str.append("\nSubprocess " + i++ + " runs for " + sub[1] + " of time and requires " + pageTable.getPage((int)sub[0]).toString());
        }
        return str.toString();
    }
}
