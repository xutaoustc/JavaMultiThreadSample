package advanced.a2_advancedConcurrentTools;


import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


// CountDownLatch : 一个线程(或者多个)， 等待另外N个线程完成某个事情之后才能执行。
// CyclicBarrier : N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
// 这样应该就清楚一点了，对于CountDownLatch来说，重点是那个“一个线程”, 是它在等待， 而另外那N的线程在把“某个事情”做完之后可以继续等待，可以终止。
// 而对于CyclicBarrier来说，重点是那N个线程，他们之间任何一个没有完成，所有的线程都必须等待。
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable{
        private String soldier;
        private final CyclicBarrier cyclic;

        Soldier(CyclicBarrier cyclic,String soldierName){
            this.soldier = soldierName;
            this.cyclic = cyclic;
        }

        public void run(){
            try {
                cyclic.await();  //等待所有士兵到齐
                doWork();
                cyclic.await();
            }catch(InterruptedException e){
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
        }

        void doWork(){
            try{
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(soldier + ":任务完成");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;
        public BarrierRun(boolean flag, int N){
            this.flag = flag;
            this.N = N;
        }

        public void run(){
            if(flag){
                System.out.println("司令:[士兵" + N + "个，任务完成！");
            }else{
                System.out.println("司令:[士兵" + N + "个，集合完毕！");
                flag = true;
            }
        }
    }


    public static void main(String[]args){
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag,N));

        System.out.println("集合队伍！");
        for(int i=0; i<N; i++){
            System.out.println("士兵" + i + "报道！");
            allSoldier[i] = new Thread(new Soldier(cyclic,"士兵"+i));
            allSoldier[i].start();

//            if(i==5){
//                allSoldier[0].interrupt();
//            }
        }

    }
}
