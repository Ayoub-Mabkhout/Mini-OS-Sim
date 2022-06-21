/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processes;

/**
 *
 * @author A.Mabkhout
 */
public class Frame {
    public int f_ID;
    static int size = 4096;
    private boolean full; 

    public Frame(int ID) {
        f_ID = ID;
        this.full = false; 
    }

    public int getF_ID() {
        return f_ID;
    }

    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }
}
