/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;

import end.user.SystemClock;
import java.util.Queue;
import gui.OSFrame; 

/**
 *
 * @author A.Mabkhout
 */
public class RR extends Scheduler{
    
    //long timeOut = 50;

    public RR(Queue<processes.Process> sharedQueue){
        queue = sharedQueue;
    }
    
    public void runNext() throws InterruptedException{
        processes.Process process = peekNext();

        Thread processThread = new Thread(process);
        processThread.start();
        
        
        synchronized(processThread){
            
            if(OSFrame.verbose)
                System.out.println("before timeout, t = " + SystemClock.getTime());
            
            processThread.wait(timeOut);
            
            if(OSFrame.verbose)
                System.out.println("after timeout, t = "+ SystemClock.getTime());
            
            processThread.notifyAll();
            processThread.interrupt();
        }
        
        System.err.println(process.isDone());
        dequeueProcess();
        if(!process.isDone())
            enqueueProcess(process);       
    }
       
}
