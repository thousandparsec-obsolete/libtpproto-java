/*
 * A basic implementation of a blocking queue.
 * The blocking queue has a size and if elments are added when it is full, it blocks 
 * until elements are removed. If elements are removed when the blocking queue is empty
 * it also blocks.
 */

package net.thousandparsec.util;

import java.util.Vector;
/**
 * This code is an adaptation of Jakob Jenkov's Blocking Queue implementation.
 * @author Brendan
 */
public class BlockingQueue {
    private Vector queue = new Vector();
    private int  limit = 10;
    
    public BlockingQueue(int limit){
        this.limit = limit;
    }
    
    public synchronized void enqueue(Object item) throws InterruptedException {
        while(this.queue.size() == this.limit){
            wait();
        }
        if(this.queue.size() < limit) {
            notifyAll();
        }
        this.queue.addElement(item);
    }
    
    public synchronized Object dequeue() throws InterruptedException{
        while(this.queue.size() == 0){
            wait();
        }
        if(this.queue.size() > 0){
            notifyAll();
        }
        Object o = this.queue.elementAt(0);
        this.queue.removeElementAt(0);
        return o;
    }
}
