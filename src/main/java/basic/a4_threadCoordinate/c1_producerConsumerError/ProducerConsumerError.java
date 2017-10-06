package basic.a4_threadCoordinate.c1_producerConsumerError;


//多生产者多消费者问题
//注意：下面程序是有问题的！！


class Resource{
    String name;
    int count =1;
    boolean fullflag;

    public synchronized void set(String name){
        if(this.fullflag){
            try { this.wait();} catch (InterruptedException e) { e.printStackTrace(); }
        }


        this.name = name + count;
        count++;

        System.out.println(Thread.currentThread().getName() + "...生产者..." + this.name);

        this.fullflag = true;
        this.notify();
    }

    public synchronized void output(){
        if(!this.fullflag){
            try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println(Thread.currentThread().getName() + "..........消费者..." + this.name );

        this.fullflag=false;
        this.notify();
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


public class ProducerConsumerError {
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
