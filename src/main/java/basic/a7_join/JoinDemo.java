package basic.a7_join;

class Demo implements Runnable{
    public void run(){
        for(int x=0;x<50;x++){
            System.out.println(Thread.currentThread().getName()+"....." + x);
        }
    }
}


public class JoinDemo {
    public static void main(String[]args) throws InterruptedException {
        Demo d = new Demo();

        Thread t1= new Thread(d);
        Thread t2= new Thread(d);

        t1.start();

        t1.join();  //主线程等待t1执行结束，才能有机会去抢夺CPU执行权
        //调用到这句话的线程会冻结，调用join的线程会执行

        t2.start();

        for(int x=0;x<50;x++){
            System.out.println(Thread.currentThread().getName()+"....." + x);
        }
    }
}
