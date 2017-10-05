package basic.a4_threadCoordinate.threadCoordinateWaitNotifyOptimize;



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

    public synchronized void set(String name,String sex){
        if(this.fullflag){
            try { this.wait();} catch (InterruptedException e) { e.printStackTrace(); }
        }


        this.name = name;
        this.sex = sex;

        this.fullflag = true;
        this.notify();
    }

    public synchronized void output(){
        if(!this.fullflag){
            try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("-----name=" + this.name + "  sex=" +  this.sex);
        this.fullflag=false;
        this.notify();
    }
}

class Input implements Runnable{
    Resource r = null;

    Input(Resource r){
        this.r = r;
    }

    public void run(){
        int num = 0;

        while(true){
            if(num==0){
                r.set("mike","nan");
            }
            else{
                r.set("Lily","女女女");
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
            r.output();
//            for(int i=0;i<1000000000l;i++){}
        }
    }
}


public class ResourceDemoWaitNotifyOptimize {
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
