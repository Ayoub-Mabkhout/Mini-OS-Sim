/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import processes.Frame;
import processes.Page;
import system.managers.comparators.*;

/**
 *
 * @author A.Mabkhout
 */
public class Clock extends MemoryManager {
    


    public Clock() {
        loadedPages = new LinkedList<Page>();
    }
    
    @Override
    public Frame replace ()
    {
        Page p = dequeue();
        Frame f = p.getFrame();
        p.unload();
        return f;
    }
         
    public void enqueue (Page p)
    {
        loadedPages.add(p);
        
    }
    public Page dequeue ()
    {
        try{
            Page p = null;
            
            do{
                if(p != null && p.isUsed()){
                    p.setUsed(false);
                    enqueue(p);
                }
                
                p = loadedPages.remove();
                currentFramePosition = p.getFrame();
            }while(p.isUsed());
            
            return p;
        } catch(NoSuchElementException nsee){
            System.err.println("EmptyQueueException: should not have reached here");
            return null;
        }
    }
    
}
