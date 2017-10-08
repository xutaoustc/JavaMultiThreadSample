package basic.a5_stopThread;

//线程中通常有循环结构，只要控制住循环就可以结束任务。控制循环通常用定义标记来完成。但如果线程处于冻结状态，无法读取标记。
//可以使用interrupt()方法将线程从冻结状态(wait,sleep...)强制恢复到运行状态中来，让线程具备CPU的执行资格。
//但是强制动作发生了InterruptedException，记得要处理
//当我们的一些线程处于冻结状态，但是程序准备结束的时候，我们有可能强制将这些线程从冻结状态拉回运行状态
class StopThread implements Runnable{
    private boolean flag = true;

    public synchronized void run(){
        while(flag){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "........." + e);
                flag =false;
            }

            System.out.println(Thread.currentThread().getName() + ".........");
        }
    }

    public void setFlag(){
        flag = false;
    }
}

public class StopThreadDemo {
    public static void main(String[]args){
        StopThread d = new StopThread();

        Thread t1 = new Thread(d);
        Thread t2 = new Thread(d);
        t1.start();
        t2.start();

        int num=1;
        for(;;){
            if(++num==50){
                t1.interrupt();
                t2.interrupt();
                break;
            }
            System.out.println("main....."+ num);
        }

        System.out.println("over");
    }
}
