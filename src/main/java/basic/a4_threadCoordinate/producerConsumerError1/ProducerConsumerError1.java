package basic.a4_threadCoordinate.producerConsumerError1;


//注意，在分析了ProducerConsumerError的问题后，我们觉得问题是由于在wait后，没有再次判断标记所引起的，所以我们把if改成了while
//但是这个程序中4个全部都挂住了。


class Resource{
    String name;
    int count =1;
    boolean fullflag;

    public synchronized void set(String name){
        while(this.fullflag){
            try { this.wait();} catch (InterruptedException e) { e.printStackTrace(); }
        }


        this.name = name + count;
        count++;

        System.out.println("生产者" + this.name);

        this.fullflag = true;
        this.notify();
    }

    public synchronized void output(){
        while(!this.fullflag){
            try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        System.out.println("。。。。。消费者" + this.name );

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


public class ProducerConsumerError1 {
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
