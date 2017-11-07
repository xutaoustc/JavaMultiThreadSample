package advanced.a2_advancedConcurrentTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//synchronized是信号量数量（许可数量）为1的锁
//允许超过一个数量的线程进入临界区。超过许可范围的线程需要等待。允许多个线程同时进入临界区
//比如系统的功能非常有限，可以使用信号量，超过这个信号量的就进行排队
public class SemaphoreDemo implements Runnable{
    final Semaphore semp = new Semaphore(5);

    public void run(){
        try {
            semp.acquire();
//            semp.acquire(2);

            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + ":done!");
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            semp.release();
//            semp.release(2);
        }
    }

    public static void main(String[]args){
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final SemaphoreDemo demo = new SemaphoreDemo();
        for(int i=0;i<20;i++){
            exec.submit(demo);
        }
    }

}
