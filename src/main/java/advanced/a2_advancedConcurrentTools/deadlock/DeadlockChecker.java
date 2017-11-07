package advanced.a2_advancedConcurrentTools.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadlockChecker {

    private final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
    final static Runnable deadlockCheck = new Runnable() {
        public void run() {
            while(true){
                long[] deadlockedThreadIds = mbean.findDeadlockedThreads();
                if(deadlockedThreadIds != null){
                    ThreadInfo[] threadInfos = mbean.getThreadInfo(deadlockedThreadIds);
                    for(Thread t: Thread.getAllStackTraces().keySet()){
                        for(int i=0;i<threadInfos.length;i++){
                            if(t.getId() == threadInfos[i].getThreadId()){
                                t.interrupt();
                            }
                        }
                    }
                }
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException e){}
            }
        }
    };

    public static void check(){
        Thread t = new Thread(deadlockCheck);
        t.setDaemon(true);
        t.start();
    }
}
