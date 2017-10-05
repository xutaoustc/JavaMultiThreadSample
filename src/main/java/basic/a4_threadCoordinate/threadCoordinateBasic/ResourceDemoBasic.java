package basic.a4_threadCoordinate.threadCoordinateBasic;



//注意，这个例子完成了一个基本的线程间通讯，但是其中会发现name和sex都是连续的一片片连续的，我们期望的是有连续切换的效果
class Resource{
    String name;
    String sex;
}

class Input implements Runnable{
    Resource r = null;

    Input(Resource r){
        this.r = r;
    }

    public void run(){
        int num = 0;

        while(true){
            synchronized (r){
                if(num==0){
                    r.name = "Mike";
                    r.sex = "Male";
                }
                else{
                    r.name = "Lily";
                    r.sex = "女女女";
                }
            }
            num= (num+1)%2;
        }
    }
}

class Output implements Runnable{
    Resource r = null;

    Output(Resource r){
        this.r = r;
    }

    public void run(){
        while(true){
            synchronized (r){
                System.out.println("name=" + r.name + "  sex=" +  r.sex);
            }
        }
    }
}


public class ResourceDemoBasic {
    public static void main(String[]args){
        Resource r = new Resource();
        Input in = new Input(r);
        Output out = new Output(r);

        Thread t1 = new Thread(in);
        Thread t2 = new Thread(out);

        t1.start();
        t2.start();
    }
}
