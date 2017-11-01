package advanced.a1_juc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


//An atomic reference is ideal to use when you need to share and change the state of an immutable object
//       between multiple threads. That is a super dense statement so I will break it down a bit.
//First, an immutable object is an object that is effectively not changed after construction. Frequently an
//       immutable object's methods return new instances of that same class. Some examples include the wrapper
//       classes of Long and Double, as well as String, just to name a few. (According to Programming Concurrency
//       on the JVM immutable objects are a critical part of modern concurrency).

//public final boolean compareAndSet(V expect, V update) {
//    return unsafe.compareAndSwapObject(this, valueOffset, expect, update);
//}

//如果有一个对象引用，希望在多个线程修改这个引用时，保持线程安全，可以使用AtomicReference

public class AtomicReferenceTest {
    public static AtomicReference<String> value = new AtomicReference("abc");

    public static void main(String[]args) throws InterruptedException {
        for(int i=0;i<10;i++){
            final int num = i ;
            new Thread(){
                public void run(){
                    try{
                        Thread.sleep(Math.abs((int)(Math.random()*100)));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if(value.compareAndSet("abc","def")){
                        System.out.println("Thread"+Thread.currentThread().getId()+" change value to " + value.get());
                    }else{
                        System.out.println("Thread"+Thread.currentThread().getId()+" change fail");
                    }
                }
            }.start();
        }





        String init="Inital Value";
        shared.set(init);
        //now we will modify that value
        boolean success=false;
        while(!success){
            String prevValue=shared.get();
            // do all the work you need to
            String newValue=shared.get()+"lets add something";
            // Compare and set
            success=shared.compareAndSet(prevValue,newValue);
        }
    }

    public static AtomicReference<String> shared = new AtomicReference();


    //Every time you want to modify the string referenced by that volatile field based on its current value,
    //  you first need to obtain a lock on that object. This prevents some other thread from coming in during the
    //  meantime and changing the value in the middle of the new string concatenation. Then when your thread
    //  resumes, you clobber the work of the other thread. But honestly that code will work, it looks clean,
    //  and it would make most people happy.
    volatile String sharedValue;
    static final Object lock=new Object();
    void modifyString(){
        synchronized(lock){
            sharedValue=sharedValue+"something to add";
        }
    }
}
