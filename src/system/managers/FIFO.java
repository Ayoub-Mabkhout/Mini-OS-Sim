/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import processes.Frame;
import processes.Page;

/**
 *
 * @author A.Mabkhout
 */
public class FIFO extends MemoryManager{
    
    public FIFO(){
        loadedPages = new LinkedList<Page>();
    }
    
    public Frame replace(){
       Page p = dequeue();
       Frame f = p.getFrame();
       p.unload();
       return f;
    }
    
    public void enqueue(Page p){
        loadedPages.add(p);
    }
    
    private Page dequeue(){
        try{
            return loadedPages.remove();            
        } catch(NoSuchElementException nsee){
            System.err.println("EmptyQueueException: should not have reached here");
            return null;
        }
    }
}
