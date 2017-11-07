package basic.a9_badSuspend;

public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run(){
            synchronized (u){
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[]args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        t1.resume();
        t2.resume();

        t1.join();
        t2.join();

        //这段程序的问题是t2.resume先调用，t2的suspend才被调用
        //suspend和resume的问题在于，suspend不会释放锁，而resume如果先于suspend之前运行，那么这个持有锁的线程可能会永久占用资源
    }
}
