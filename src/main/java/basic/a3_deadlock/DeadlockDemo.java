package basic.a3_deadlock;


class LockA{}
class LockB{}

//常见情景之一，同步的嵌套
class Task1 implements  Runnable{
    public void run(){
        synchronized (LockA.class){
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (LockB.class){
                System.out.println("Task1 output");
            }
        }
    }
}

class Task2 implements  Runnable{
    public void run(){
        synchronized (LockB.class){
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
            synchronized (LockA.class){
                System.out.println("Task2 output");
            }
        }
    }
}


public class DeadlockDemo {
    public static void main(String[]args){
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();
    }
}
