package basic.a6_daemonThread;


//daemon线程，守护线程，也称后台线程
//与前台线程的区别在于，前台线程必须手动结束；如果所有的前台线程都结束了，后台线程无论处于什么状态都自动结束
//当正在运行的线程都是守护线程时，Java虚拟机退出。
//圣斗士守护雅典娜，如果雅典娜死了，那么圣斗士也都失业了。
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

public class DaemonThread {
    public static void main(String[]args){
        StopThread d = new StopThread();

        Thread t1 = new Thread(d);
        Thread t2 = new Thread(d);

        t1.start();
        t2.setDaemon(true);
        t2.start();

        int num=1;
        for(;;){
            if(++num==50){
                t1.interrupt();
//                t2.interrupt();
                break;
            }
            System.out.println("main....."+ num);
        }

        System.out.println("over");
    }
}
