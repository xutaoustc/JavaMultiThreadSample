package basic.a4_threadCoordinate.c4_producerConsumerLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//synchronized对于锁的操作是隐式的，jdk1.5之后将同步和锁封装成了对象，并将操作锁的隐式方式定义到了该对象中，将隐式动作变成了显示动作

//lock替代了synchonized方法和语句的使用，Condition替代了Object监视器方法的使用。
//之前一个锁上只能有一组wait,notify,notifyAll方法，现在一个Lock上可以挂多个Condition对象

// 去除synchronized,新建Lock(unlock需要写在finally中)
// wait和notifyAll都改写, 改用Condition
class Resource{
    String name;
    int count =1;
    boolean fullflag;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void set(String name){
        lock.lock();

        try{
            while(this.fullflag){
                //try { this.wait();} catch (InterruptedException e) { e.printStackTrace(); }
                try{ condition.await();}catch (InterruptedException e) { e.printStackTrace(); }
            }


            this.name = name + count;
            count++;

            System.out.println(Thread.currentThread().getName() + "...生产者..." + this.name);

            this.fullflag = true;
//            this.notifyAll();
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void output(){
        lock.lock();

        try{
            while(!this.fullflag){
//                try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                try{ condition.await();}catch (InterruptedException e) { e.printStackTrace(); }
            }

            System.out.println(Thread.currentThread().getName() + "..........消费者..." + this.name );

            this.fullflag=false;
//            this.notifyAll();
            condition.signalAll();
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


public class ProducerConsumerLock {
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
