package advanced.a2_advancedConcurrentTools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;


//如果unpark发生在park之前，那么park并不会把线程阻塞住
//      LockSupport 提供park()和unpark()方法实现阻塞线程和解除线程阻塞，LockSupport和每个使用它的线程都与一个许可(permit)关联。
//      permit相当于1，0的开关，默认是0，调用一次unpark就加1变成1，调用一次park会消费permit, 也就是将1变成0，同时park立即返回。
//      再次调用park会变成block（因为permit为0了，会阻塞在这里，直到permit变为1）, 这时调用unpark会把permit置为1。
//      每个线程都有一个相关的permit, permit最多只有一个，重复调用unpark也不会积累。

public class LockSupportDemo {
    public static Object u = new Object();

    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends  Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        public void run(){
            synchronized (u){
                System.out.println("in " + getName());
                LockSupport.park();    //会响应中断，但是不会抛出中断异常，用Thread.interrupted()判断是否被中断
            }
        }
    }


    public static void main(String[]args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}
