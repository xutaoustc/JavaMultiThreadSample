package advanced.a2_advancedConcurrentTools;

import advanced.a2_advancedConcurrentTools.deadlock.DeadlockChecker;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockIntDemo implements  Runnable{
    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();
    int lock;

    public ReenterLockIntDemo(int lock){
        this.lock=lock;
    }

    public void run(){
        try{
            if(lock == 1){
                lock1.lockInterruptibly();
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){}
                lock2.lockInterruptibly();
            }else{
                lock2.lockInterruptibly();
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){}
                lock1.lockInterruptibly();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            if(lock1.isHeldByCurrentThread())
                lock1.unlock();
            if(lock2.isHeldByCurrentThread())
                lock2.unlock();
            System.out.println(Thread.currentThread().getId() + ":线程退出");
        }
    }

    public static void main(String[]args) throws InterruptedException {
        ReenterLockIntDemo r1 =new ReenterLockIntDemo(1);
        ReenterLockIntDemo r2 =new ReenterLockIntDemo(2);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();t2.start();
        Thread.sleep(10000);

        DeadlockChecker.check();
    }
}
