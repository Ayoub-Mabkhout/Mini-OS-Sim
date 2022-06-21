/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers.comparators;

import java.util.Comparator;

/**
 *
 * @author A.Mabkhout
 */
public class LRUComparator implements Comparator <processes.Page>{
    public int compare (processes.Page p1, processes.Page p2){
        return Long.compare(p1.getLastUsed(), p1.getLastUsed()); 
    } 
}
