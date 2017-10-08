package basic.a8_yield;

class Demo implements Runnable{
    public void run(){
        for(int x=0;x<50;x++){
            System.out.println(Thread.currentThread().getName()+"....." + x);
            Thread.yield();   //释放自己的执行资格，使得自己重新和其他线程竞争CPU
            //如果不加这句话，可能一个线程能够连续执行。
        }
    }
}


public class YieldDemo {
    public static void main(String[]args) throws InterruptedException {
        Demo d = new Demo();

        Thread t1= new Thread(d);
        Thread t2= new Thread(d);

        t1.start();
        t2.start();


    }
}
