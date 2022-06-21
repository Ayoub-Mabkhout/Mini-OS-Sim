/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package end.user;
import gui.OSFrame;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Queue;
import processes.*;
import java.util.Random;
import system.managers.MemoryManager;
import system.managers.Scheduler;
/**
 *
 * @author A.Mabkhout
 */
public class ProcessGenerator implements Runnable{
    ArrayList<Object> processes;
    Queue<processes.Process> processQueue;
    int count;
    private final int queueSize = 6;
    private final int avgSubprocessLength = 22;
    private final int variation = 16;
    private final int avgPageNumber = 3;
    private final int avgSequenceNumber = 2 * avgPageNumber;
    private final int avgPageSize = 4096; // in kb
    
    
    public static processes.Process newestProcess;
            
    public ProcessGenerator(Queue<processes.Process> processQueue){
        processes = new ArrayList<Object>();
        this.processQueue = processQueue;
        count = 0;
    }
    
    public void run(){
        while(OSFrame.running){
            try{
                generate();
                //Thread.sleep(SystemClock.timeQuantum*50);
                count++;
            } catch(InterruptedException ie){
                newestProcess = null;
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public processes.Process generateProcess(){
        // initialization
        Random rand = new Random();
        String p_ID = "P_"+(count+1);
        
        // generating page table
        int pageNumber = rand.nextInt(2) + avgPageNumber - 1;
        PageTable pt = new PageTable(pageNumber);
        
        for(int i = 0; i < pageNumber; i++){
            int pageSize = avgPageSize;
            pt.addPage(new Page(pageSize,p_ID,(i+1)));
        }
        
        int sequenceNumber = rand.nextInt(4) + avgSequenceNumber - 2;
        // generating sequence
        ArrayList<long[]> sequence = new ArrayList<long[]>();
        for(int i = 0; i < sequenceNumber; i++){
            long pageIndex = rand.nextInt(pageNumber);
            long subprocessLength = abs(rand.nextLong() % (2*variation)) + (avgSubprocessLength - variation);
            sequence.add(new long[]{pageIndex,subprocessLength});
        }
        
        return new processes.Process(p_ID,pt,sequence);
    }
    
    
    public void generate() throws InterruptedException{
        Random rand = new Random();
        
        // Waiting for a random amount of time before generating the next process
        long avgWaitTime = avgSequenceNumber * avgSubprocessLength + 
            avgSequenceNumber * (long)(avgPageNumber * avgPageSize / MemoryManager.loadRate) + 8;
        long waitTime = 0;
        
        if(processQueue.size() > 0.75*queueSize)
            waitTime = abs(rand.nextLong() % ((int) (avgWaitTime*1.33))); // increase possible wait time if queue is near full
        // simula tes a user experiencing a slow PC that does not allow them to comfortably open many processes
        else
            waitTime = abs(rand.nextLong() % ((int) (avgWaitTime*0.35)));
        
        SystemClock.waitFor(waitTime);
        
        processes.Process p = generateProcess();
        newestProcess = p;
        
        synchronized(processQueue){
            while(processQueue.size() == queueSize){
            System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + processQueue.size());
            processQueue.wait();
            }
            processQueue.add(p);
            Scheduler.lastEnqueued = p;
            processQueue.notifyAll();
        }        
    }
    
    public void LoadProcesses() throws InterruptedException{
        SystemClock.waitFor(100);
        processes.Process p = null;
        ArrayList<long[]> sequence = new ArrayList<long[]>();            

            switch(count%3){
                case 0:
                    PageTable pt1 = new PageTable(4);
                    for(int i = 0; i < 4; i++){
                        Page pa = new Page();
                        pt1.addPage(pa);
                    }
                    sequence.add(new long[]{0,40});
                    sequence.add(new long[]{1,40});
                    sequence.add(new long[]{2,40});
                    sequence.add(new long[]{3,40});
                    
                    p = new processes.Process("1",pt1,sequence);

                    break;
                    
                case 1:
                    PageTable pt2 = new PageTable(4);
                    for(int i = 0; i < 4; i++){
                        Page pa = new Page();
                        pt2.addPage(pa);
                    }

                    sequence.add(new long[]{2,80});
                    sequence.add(new long[]{1,60});
                    sequence.add(new long[]{2,90});
                    sequence.add(new long[]{1,50});
                    
                    p = new processes.Process("2",pt2,sequence);

                    break;
                
                case 2:
                    PageTable pt3 = new PageTable(4);
                    for(int i = 0; i < 4; i++){
                        Page pa = new Page();
                        pt3.addPage(pa);
                    }
                    sequence.add(new long[]{3,50});
                    sequence.add(new long[]{2,40});
                    sequence.add(new long[]{3,30});
                    sequence.add(new long[]{0,20});
                   
                    
                    p = new processes.Process("3",pt3,sequence);
                   
                    break;
            }
        
        synchronized(processQueue){
            while(processQueue.size() == queueSize){
            System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + processQueue.size());
            processQueue.wait();
            }
            processQueue.add(p);
            processQueue.notifyAll();
        }
        
    }
}
