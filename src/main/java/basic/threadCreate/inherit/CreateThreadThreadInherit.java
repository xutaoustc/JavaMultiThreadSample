package basic.threadCreate.inherit;


class Demo extends Thread{
    Demo(String name){
        super(name);
    }

    public void run(){
        for(int i=0;i<10;i++){
            for(int y=-9999999;y<999999999;y++){}
            System.out.println(" name=" + Thread.currentThread().getName() + "....i=" + i);
        }
    }
}

public class CreateThreadThreadInherit {
    public static void main(String[]args){
        Thread t1 = new Demo("旺财");
        Thread t2 = new Demo("xiaoqiang");
        t1.start();
        t2.start();
    }
}
