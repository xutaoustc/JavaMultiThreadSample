package basic.a4_threadCoordinate.threadCoordinateWaitNotify;



//wait(): 让线程立刻处于冻结状态。
//        当前线程必须拥有此对象监视器。该线程发布对此监视器的所有权并等待，直到其他线程通过调用 notify 方法，或 notifyAll 方法通知在
//        此对象的监视器上等待的线程醒来。然后该线程将等到重新获得对监视器的所有权后才能继续执行。
//notify(): 唤醒线程池中的一个线程（任意）。
//        直到当前线程放弃此对象上的锁定，才能继续执行被唤醒的线程。被唤醒的线程将以常规方式与在该对象上主动同步的其他所有线程进行竞争；
//notifyAll(): 唤醒线程池中的所有线程
//这些方法都必须定义在同步中，因为他们必须要明确到底操作的是那个锁上的线程。

class Resource{
    String name;
    String sex;
    boolean fullflag;
}

class Input implements Runnable{
    Resource r = null;

    Input(Resource r){
        this.r = r;
    }

    public void run(){
        int num = 0;

        while(true){
            synchronized (r){
                if(r.fullflag){
                    try { r.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }

                if(num==0){
                    r.name = "Mike";
                    r.sex = "Male";
                }
                else{
                    r.name = "Lily";
                    r.sex = "女女女";
                }

                r.fullflag = true;
                r.notify();
            }
            num= (num+1)%2;
        }
    }
}

class Output implements Runnable{
    Resource r = null;

    Output(Resource r){
        this.r = r;
    }

    public void run(){
        while(true){
            synchronized (r){
                if(!r.fullflag){
                    try { r.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                }
                System.out.println("name=" + r.name + "  sex=" +  r.sex);
                r.fullflag=false;
                r.notify();
            }
        }
    }
}


public class ResourceDemoWaitNotify {
    public static void main(String[]args){
        Resource r = new Resource();
        Input in = new Input(r);
        Output out = new Output(r);

        Thread t1 = new Thread(in);
        Thread t2 = new Thread(out);

        t1.start();
        t2.start();
    }
}
