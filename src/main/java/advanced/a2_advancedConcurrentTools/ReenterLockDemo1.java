package advanced.a2_advancedConcurrentTools;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockDemo1 implements  Runnable{
    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    public void run(){
        for(int j=0;j<10000000;j++){
            lock.lock();
            lock.lock();
            try{
                i++;
            }finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public static void main(String[]args) throws InterruptedException {
        ReenterLockDemo1 tl =new ReenterLockDemo1();

        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);

        t1.start();t2.start();
        t1.join();t2.join();
        System.out.println(i);
    }
}
