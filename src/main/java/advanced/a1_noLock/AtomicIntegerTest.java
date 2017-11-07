package advanced.a1_noLock;

import java.util.concurrent.atomic.AtomicInteger;

//public final boolean compareAndSet(int expect, int update) {
//    return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
//}


//public final int incrementAndGet() {
//    return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
//}

//public final int getAndAddInt(Object var1, long var2, int var4) {
//    int var5;
//    do {
//        var5 = this.getIntVolatile(var1, var2);
//    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
//
//    return var5;
//}


//  incrementAndGet->getAndAddInt->
//                                  compareAndSwapInt
//                  compareAndSet->

public class AtomicIntegerTest {
    static class Domain implements  Runnable{
        public AtomicInteger value = new AtomicInteger(0);

        public void run(){
            for(int i=0;i<10000;i++){
                value.incrementAndGet();
            }
        }
    }

    public static void main(String[]args) throws InterruptedException {
        Domain d = new Domain();

        Thread t1 = new Thread(d);
        Thread t2 = new Thread(d);
        Thread t3 = new Thread(d);

        t1.start();
        t2.start();
        t3.start();


        t1.join();
        t2.join();
        t3.join();


        System.out.println(d.value.get());

    }
}
