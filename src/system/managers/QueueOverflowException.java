/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.managers;

import end.user.SystemClock;

/**
 *
 * @author A.Mabkhout
 */
public class QueueOverflowException extends Exception{
    public QueueOverflowException(processes.Process p){
        super("Queue Overflow Exception at t = " + SystemClock.getTime() + ": Process " + p.getP_ID() + "could not be "
                + "enqueued.");
    }
}
