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
public class SRTComparator implements Comparator<processes.Process>{
    public int compare(processes.Process p1, processes.Process p2){
        return Long.compare(p1.getRemainingTime(), p2.getRemainingTime());
    }
}
