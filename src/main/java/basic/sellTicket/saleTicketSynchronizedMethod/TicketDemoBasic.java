package basic.sellTicket.saleTicketSynchronizedMethod;


class Ticket implements Runnable{
    private int num = 1000;

    public void run(){
        while(true){
           show();
        }
    }

    //同步函数使用的锁是this
    //静态同步函数使用的锁是Ticket.class 或者this.getClass()
    public synchronized void show(){
        //需要注意的是，使用obj这种机制，类似于是一种锁，进去了以后就把锁给上了，在里面sleep了也不会释放锁。只有出来了才会释放锁
        if(num>0){
            //-->1  -->2   -->3  -->4
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println(Thread.currentThread().getName() + "...sale...." + num--);
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
