package advanced.a2_advancedConcurrentTools;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockTimeDemo implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();

    public void run(){
        try{
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else{
                System.out.println("get lock failed");
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }

    public static void main(String[]args){
        ReenterLockTimeDemo tl = new ReenterLockTimeDemo();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);

        t1.start();t2.start();
    }
}
