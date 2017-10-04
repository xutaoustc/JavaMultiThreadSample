package basic.sellTicket.saleTicketSynchronizedBlock;


class Ticket implements Runnable{
    private int num = 1000;
    Object obj = new Object();

    public void run(){
        while(true){
            //同步代码块的格式
            //synchronized(对象){
            //      需要被同步的代码
            // }
            //需要注意的是，使用obj这种机制，类似于是一种锁，进去了以后就把锁给上了，在里面sleep了也不会释放锁。只有出来了才会释放锁。
            //类似于火车上的卫生间
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
