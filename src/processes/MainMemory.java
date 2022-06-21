/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processes;

import java.util.ArrayList;


/**
 *
 * @author A.Mabkhout
 */
public class MainMemory {
    public static int totalMemory;
    public static ArrayList<Frame> frames;
    
    public static void init(){
        totalMemory = Frame.size * 8;
        frames = new ArrayList<Frame>(totalMemory/Frame.size);
        for (int i = 0; i < totalMemory/Frame.size; i++)
        {
            Frame f = new Frame (i+1);
            frames.add(f); 
        }
    }
    public static boolean isFull(){
        for(Frame f : frames){
            if (!f.isFull())
                return false;
        }
        return true;
    }
    public static Frame getNextFreeFrame(){ 
        for(Frame f : frames){
            if (!f.isFull())
                return f;
        }
        return null;
    }
    
    public static String showFrames(){
        StringBuilder str = new StringBuilder();
        str.append("[");
        for(Frame f : frames){
            str.append(f.isFull()? "full,":"empty,");
        }
        str.deleteCharAt(str.length()-1);
        str.append("]");
        return str.toString();
    }
}
