package basic.threadCreate.runnable;


class Demo implements Runnable{
    public void run(){
        for(int i=0;i<10;i++){
            for(int y=-9999999;y<999999999;y++){}
            System.out.println(" name=" + Thread.currentThread().getName() + "....i=" + i);
        }
    }
}

public class CreateThreadImplementRunnable {
    public static void main(String[]args){
        Demo d = new Demo();

        Thread t1 = new Thread(d,"旺财");
        Thread t2 = new Thread(d,"xiaoqiang");
        t1.start();
        t2.start();
    }
}
