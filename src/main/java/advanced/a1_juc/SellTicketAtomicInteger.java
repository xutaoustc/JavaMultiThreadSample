package advanced.a1_juc;

import java.util.concurrent.atomic.AtomicInteger;


class Ticket implements Runnable {
    public AtomicInteger num = new AtomicInteger(100000);
    public void run() {
        for(;;){
            int ticket = num.get();

            if( ticket>0 && num.compareAndSet(ticket,ticket-1) ){
                System.out.println("Thread" + Thread.currentThread().getId() + " sell ticket " + ticket);
            }else{
                if(ticket>0){
                    continue;
                }else{
                    break;
                }
            }
        }
    }
}

public class SellTicketAtomicInteger {
    public static void main(String[]args) throws InterruptedException {
        Ticket t = new Ticket();

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        Thread t4 = new Thread(t);

        t1.start();
        t2.start();
        t3.start();
        t4.start();


        t1.join();
        t2.join();

        System.out.println(t.num.get());
    }
}
