/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *
 * @author A.Mabkhout
 */
public class FCFS extends Scheduler{
    
    public FCFS(Queue<processes.Process> sharedQueue){
        queue = sharedQueue;
    }
    
    @Override
    public void runNext() throws InterruptedException{
        processes.Process process = peekNext();

        Thread processThread = new Thread(process);
        processThread.start();
        
        synchronized(processThread){
                   
            processThread.wait();            
           
            processThread.notifyAll();
        }
        dequeueProcess();
        if(!process.isDone())
            enqueueProcess(process); 
    }
}
