package advanced.a0_interrupt;


//interrupt方法相当于给线程打一个招呼，线程会把自己的一个中断标志位给置上（线程就知道有人寻求过我）。
//但是响应或者不响应取决于程序设计
class StopThread implements Runnable{
    public synchronized void run(){
        while(true){
            if(Thread.currentThread().isInterrupted())
            {
                System.out.println("Interruted!");
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Interruted When Sleep"); //设置中断状态,抛出异常后会清除中断标记位
                Thread.currentThread().interrupt();
            }

            Thread.yield();
        }
    }
}


public class InterruptDemo {

}
