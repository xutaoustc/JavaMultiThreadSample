package advanced.a3_threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//为什么需要线程池：线程的创建和销毁是代价比较高的，且和业务没有太大关系，业务只关心线程执行的任务，因此我们希望把更多的CPU用在业务执行上，
//     而不是辅助性质的线程创建销毁上。

//Executors是工厂类
//ThreadPoolExecutor是线程池的实现
public class ThreadPoolDemo {

    public static class MyTask implements Runnable{
        public void run(){
            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        MyTask task = new MyTask();
        ExecutorService es = Executors.newFixedThreadPool(5);
        for(int i =0 ;i<10;i++){
//            es.submit(task);
            es.execute(task);
        }
    }
}
