/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package end.user;
import java.lang.Thread;
/**
 *
 * @author A.Mabkhout
 */
public class SystemClock {
    
    public static long timeQuantum = 125; // system time quantum in milliseconds
    public static long bootTime; // main calls SystemClock.bootTime = SystemClock that System.currentTimeMillis() at  t = 0
    
    // A process may run for a certain amount of time
    public static void waitFor(long factor) throws InterruptedException{

            Thread.sleep(factor*timeQuantum);

    }
    
    public static long getTime(){
        long currentTime = System.currentTimeMillis();
        return (currentTime-bootTime)/timeQuantum;
    }
}
