/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;
import gui.OSFrame;
import java.util.PriorityQueue;
import java.util.Queue;
import processes.Process;



/** 
 *
 * @author A.Mabkhout
 */
public abstract class Scheduler implements Runnable{
    int queueSize = 6;
    Queue<processes.Process> queue;
    public static processes.Process runningProcess;
    public static processes.Process lastEnqueued;
    public static long timeOut;
    
    public void enqueueProcess(processes.Process p) throws InterruptedException{      
        synchronized(queue){
            while(queue.size() == queueSize){
                System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: " + queue.size());
                queue.wait();
            }
            queue.add(p);
            lastEnqueued = p;
            queue.notifyAll();
        }
    }
    
    public processes.Process dequeueProcess() throws InterruptedException{
        synchronized(queue){
            while (queue.isEmpty())
             {
                System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + queue.size());
                queue.wait();
             }
            processes.Process process = (processes.Process) queue.remove();
            runningProcess = process;
            queue.notifyAll();

             return process;
        }
    }
    
    public processes.Process peekNext() throws InterruptedException{
        synchronized(queue){
            while (queue.isEmpty())
             {
                System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting , size: " + queue.size());
                queue.wait();
             }
            processes.Process process = (processes.Process) queue.peek();
            runningProcess = process;
            queue.notifyAll();

             return process;
        }
    }
    
    public abstract void runNext()  throws InterruptedException;
    
    public void run(){
        while(OSFrame.running){
            try{
                runningProcess = null;
                runNext();
            } catch(InterruptedException ie){
                    lastEnqueued = null;
                    Thread.currentThread().interrupt();
            }
        }
    }
}
