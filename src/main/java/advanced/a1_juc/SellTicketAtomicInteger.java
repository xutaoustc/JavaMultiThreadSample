package advanced.a1_juc;

import java.util.concurrent.atomic.AtomicInteger;


//用了AtomicInteger也不能保证完全的线程安全，还是要靠用法
class Ticket implements Runnable {
    private AtomicInteger num = new AtomicInteger(100);
    public void run() {
        while (true) {
            int ticket = num.get();

            if (ticket > 0) {
                //-->1  -->2   -->3  -->4
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }  // 强行模拟效果一波^_^

                System.out.println(Thread.currentThread().getName() + "...sale...." + num.getAndDecrement());
            }
        }
    }
}

public class SellTicketAtomicInteger {
    public static void main(String[]args){
        Ticket t = new Ticket();

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
