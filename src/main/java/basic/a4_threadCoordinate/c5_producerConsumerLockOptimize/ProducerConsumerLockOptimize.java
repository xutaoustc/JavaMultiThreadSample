package basic.a4_threadCoordinate.c5_producerConsumerLockOptimize;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//之前的问题在于，notifyAll和notify所有的对象都可能被唤醒。我们希望生产者只能唤醒消费者;消费者只能唤醒生产者
//搞两个Condition，这回就不用signalAll了，直接signal就可以了。
class Resource{
    String name;
    int count =1;
    boolean fullflag;

    Lock lock = new ReentrantLock();
    Condition pro_condition = lock.newCondition();
    Condition con_condition = lock.newCondition();

    public void set(String name){
        lock.lock();

        try{
            while(this.fullflag){
                try{ pro_condition.await();}catch (InterruptedException e) { e.printStackTrace(); }
            }


            this.name = name + count;
            count++;

            System.out.println(Thread.currentThread().getName() + "...生产者..." + this.name);

            this.fullflag = true;
            con_condition.signal();
        }finally {
            lock.unlock();
        }
    }

    public void output(){
        lock.lock();

        try{
            while(!this.fullflag){
                try{ con_condition.await();}catch (InterruptedException e) { e.printStackTrace(); }
            }

            System.out.println(Thread.currentThread().getName() + "..........消费者..." + this.name );

            this.fullflag=false;
            pro_condition.signal();
        }finally {
            lock.unlock();
        }
    }
}

class Producer implements Runnable{
    Resource r = null;

    Producer(Resource r){
        this.r = r;
    }

    public void run(){
        while(true){
            r.set("烤鸭");
        }
    }
}

class Consumer implements Runnable{
    Resource r = null;

    Consumer(Resource r){
        this.r = r;
    }

    public void run(){
        while(true){
            r.output();
        }
    }
}


public class ProducerConsumerLockOptimize {
    public static void main(String[]args){
        Resource r = new Resource();
        Producer p = new Producer(r);
        Consumer c = new Consumer(r);

        Thread p1 = new Thread(p);
        Thread p2 = new Thread(p);
        Thread c1 = new Thread(c);
        Thread c2 = new Thread(c);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
    }
}
