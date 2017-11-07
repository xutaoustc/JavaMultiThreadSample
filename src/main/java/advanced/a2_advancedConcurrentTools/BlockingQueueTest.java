package advanced.a2_advancedConcurrentTools;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private static final AtomicInteger singleValue = new AtomicInteger(1);

    Producer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        while(true){
            try {
                int val = singleValue.incrementAndGet();
                queue.put(val);
//                System.out.println("current value " +  singleValue.get());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

/**
 * 消费者
 * @author wasw100
 */
class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    Consumer(BlockingQueue q) {
        queue = q;
    }

    public void run() {
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " Consume " + queue.take());
                //Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}


public class BlockingQueueTest {
    public static void main(String[]args){
        BlockingQueue<Integer> q = new LinkedBlockingQueue(1000);
        Producer p1 = new Producer(q);
        Producer p2 = new Producer(q);
        Consumer c1 = new Consumer(q);
        Consumer c2 = new Consumer(q);
        new Thread(p1,"Producer1").start();
        new Thread(p2,"Producer2").start();
        new Thread(c1,"Consumer1").start();
        new Thread(c2,"Consumer2").start();
    }
}
