/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processes;

import end.user.SystemClock;

/**
 *
 * @author A.Mabkhout
 */
public class Page {
    public Frame frame;
    public int size;
    public boolean loaded;
    public long lastUsed;
    public int frequency;
    public boolean used;
    public String p_ID;
    
    public Page(){
        loaded = false;
        frequency = 0;
        size = Frame.size;
        p_ID = "default page id";
    }
    
    public Page(int size, String process_ID, int count){
        loaded = false;
        frequency = 0;
        this.size = size;
        this.p_ID = process_ID.substring(2) + getCharForNumber(count);
    }

    public String getP_ID() {
        return p_ID;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    

    public int getFrequency() {
        return frequency;
    }

    
        
    public Frame getFrame() {
        return frame;
    }

    public int getSize() {
        return size;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public long getLastUsed() {
        return lastUsed;
    }
    
    
    public void unload(){
        frequency = 0;
        this.frame.setFull(false);
        this.frame = null;
        loaded = false;
    }
    
    public void load(Frame f){
        this.frame = f;
        f.setFull(true);
        lastUsed = SystemClock.getTime();
        frequency++;
        used = false;
        loaded = true;
        
    }
    
    public void update(){
        lastUsed = SystemClock.getTime();
        frequency++;
        used = true;
        
    }
    
    public String toString(){
        return "page " + p_ID + " of size " + size;
    }
    
    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : "Default";
    }
    
}
