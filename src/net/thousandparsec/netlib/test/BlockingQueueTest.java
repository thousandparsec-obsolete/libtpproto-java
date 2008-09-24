
package net.thousandparsec.netlib.test;
import net.thousandparsec.util.BlockingQueue;
/**
 *Tests the blocking queue class
 * @author Brendan
 */
public class BlockingQueueTest {
    BlockingQueue bq;
    BlockingQueueTest(BlockingQueue b){
        bq = b;
    }
    class Consume extends Thread{
        BlockingQueue bq;
        Consume(BlockingQueue b){
            bq = b;
        }
        public void run() {
            while(true){
                try{
                    System.out.println("Consuming: " + bq.take());
                }
                catch(InterruptedException e){
                    
                }
                
            }
        }
    }
    class Produce extends Thread{
        BlockingQueue bq;
        int count = 1;
        Produce(BlockingQueue b){
            bq = b;
        }
        public void run() {
            while(true){
                try{
                    bq.put("Produce: " + count);
                    System.out.println("Producing" + count);
                }
                catch(InterruptedException e){
                    
                    
                }
                
            }
        }
    }
    void start(){
        new Produce(bq).start();
        new Consume(bq).start();
    }
    public static void main(String[] args){
        BlockingQueue bq = new BlockingQueue(10);
        BlockingQueueTest bqt = new BlockingQueueTest(bq);
        bqt.start();
        
    }
}
