package basic.singletonInMultiThread;

class Singleton{
    private static Singleton s = null;

    private Singleton(){}

    public static Singleton getInstance(){
        if(s==null){
            synchronized (Singleton.class){
                if(s==null){
                    s = new Singleton();
                }
            }
        }

        return s;
    }
}

public class SingletonDemo {
    public static void main(String[]args){
        Singleton s = Singleton.getInstance();
    }
}
