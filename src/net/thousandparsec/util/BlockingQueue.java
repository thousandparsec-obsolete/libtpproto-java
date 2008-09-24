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
    private int  limit;
    private int current_size;
    public BlockingQueue(int limit){
        this.limit = limit;
        current_size=0;
    }
    
    public synchronized void put(Object item) throws InterruptedException {
        //if is full
        while(this.queue.size() == this.limit){
            System.out.println("Queue Full: Waiting to Put");
            wait();
        }
        //if not full anymore
        if(this.queue.size() < limit) {
            notifyAll();
        }
        System.out.println("Object Put " + item.toString());
        this.queue.addElement(item);
        current_size++;
    }
    
    public synchronized Object take() throws InterruptedException{
        //if is empty
        while(this.queue.size() == 0){
            System.out.println("Queue Empty: Waiting to Take");
            wait();
        }
        //if it isn't empty anymore
        if(this.queue.size() > 0){
            notifyAll();
        }
        Object o = this.queue.elementAt(0);
        this.queue.removeElementAt(0);
        current_size--;
        System.out.println("Object Taken " + o.toString());
        return o;
    }
    public int getCurrentSize(){
        return current_size;
    }
}
