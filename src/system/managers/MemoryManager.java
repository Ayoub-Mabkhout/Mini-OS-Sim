/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;

import end.user.SystemClock;
import java.util.PriorityQueue;
import java.util.Queue;
import processes.Frame;
import processes.MainMemory;
import processes.Page;
import gui.OSFrame;

/**
 *
 * @author A.Mabkhout
 */
public abstract class MemoryManager {
    public static double loadRate = 409.6; // kb per time quantum   
    public static Queue<Page> loadedPages;
    public static Frame currentFramePosition;
    public static int thrashing = 0;
    public static int loadRequests = 0; 
    
    public void loadPage(Page p) throws InterruptedException{
        //check if page is in frames 
        loadRequests = loadRequests+1; 
        if (p.isLoaded()){ // if page is already loaded, just update some values and then return
            p.update();
            return;
        }
        
        // if page is not currently loaded, load it in a free page or use a memory management algorithm to replace a loaded page
        
        Frame f;
        if(MainMemory.isFull()){
            System.out.println("Finding a page to replace");
            f = replace();
            thrashing = thrashing +1; 
            if(f == null)
                System.err.println("Null Frame REPLACE: Should not have reached here");
        }
        else{
            System.out.println("Finding next free frame");
            f = MainMemory.getNextFreeFrame();
            
            if(f == null)
                System.err.println("Null Frame FREE: Should not have reached here");
        }
        
        currentFramePosition = f;
        load(p,f); 
        enqueue(p);
    }
    
    public void load(Page p, Frame f) throws InterruptedException{
        
        if(OSFrame.verbose)
            System.out.println("<--- Loading page " + p.getP_ID() + " onto frame " + f.getF_ID() + " --->");
            System.out.println(MainMemory.showFrames());

                
        SystemClock.waitFor((long)(p.getSize()/loadRate));
        p.load(f);
    }
    
    public abstract Frame replace();
    
    public abstract void enqueue(Page p);
        
}
