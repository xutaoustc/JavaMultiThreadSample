package basic.sellTicket.saleTicketSynchronizedBlockError;


class Ticket implements Runnable{
    private int num = 1000;

    public void run(){
        while(true){
            //注意这种同步的问题在于，多个线程使用的不是同一个锁
            Object obj = new Object();
            synchronized (obj){
                if(num>0){
                    //-->1  -->2   -->3  -->4
                    try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }

                    System.out.println(Thread.currentThread().getName() + "...sale...." + num--);
                }
            }
        }
    }
}

public class TicketDemoBasic {
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
