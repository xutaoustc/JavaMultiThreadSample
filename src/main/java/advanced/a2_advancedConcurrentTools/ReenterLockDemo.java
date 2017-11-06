package advanced.a2_advancedConcurrentTools;

import java.util.concurrent.locks.ReentrantLock;


//可重入
//可中断
//可限时
//公平锁
public class ReenterLockDemo implements  Runnable{
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    public void run(){
        for(int j=0;j<10000000;j++){
            lock.lock();
            try{
                i++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[]args) throws InterruptedException {
        ReenterLockDemo tl =new ReenterLockDemo();

        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);

        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
