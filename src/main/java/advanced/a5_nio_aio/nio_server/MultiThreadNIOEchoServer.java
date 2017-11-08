package advanced.a5_nio_aio.nio_server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//NIO会将数据准备好后,再交由应用进行处理,数据的读取过程依然在应用线程中完成
//节省数据准备时间(因为Selector可以复用)
public class MultiThreadNIOEchoServer {
    //用于时间统计
    public static Map<Socket,Long> geym_time_stat = new HashMap<>(10240);

    class EchoClient{
        private LinkedList<ByteBuffer> outq;

        EchoClient(){
            outq = new LinkedList<ByteBuffer>();
        }

        public LinkedList<ByteBuffer> getOutputQueue(){
            return outq;
        }

        public void enqueue(ByteBuffer bb){
            outq.addFirst(bb);
        }
    }

    class HandleMsg implements Runnable{
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk,ByteBuffer bb){
            this.sk = sk;
            this.bb = bb;
        }

        public void run(){
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);

            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);   //修改感兴趣的东西
            selector.wakeup();
        }
    }

    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    private void doAccept(SelectionKey sk){
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;

        try{
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);

            //Register this channel for reading
            SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);
            //Allocate an EchoClient instance and attach it to this selection key.
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress());
        }catch(Exception e){
            System.out.println("failed to accept new client");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk){
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;

        try{
            len = channel.read(bb);
            if(len < 0) {
//                disconnect(sk);
                return;
            }
        } catch(Exception e){
            System.out.println("Failed to read from client.");
            e.printStackTrace();
//            disconnect(sk);
            return;
        }
        bb.flip();
        tp.execute(new HandleMsg(sk,bb));
    }

    private void doWrite(SelectionKey sk){
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

        ByteBuffer bb = outq.getLast();
        try{
            int len = channel.write(bb);
            if(len == -1){
//                disconnect(sk);
                return;
            }

            if(bb.remaining() == 0){
                outq.removeLast();
            }
        }catch(Exception e){
            System.out.println("Failed to write to client.");
            e.printStackTrace();
//            disconnect(sk);
        }

        if(outq.size()==0){
            sk.interestOps(SelectionKey.OP_READ);
        }
    }


    private void startServer() throws Exception{
        selector = SelectorProvider.provider().openSelector();

        //Create server socket channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //Bind ther server socket to localhost
        InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);


        //********Channel可以和SelectionKey相互关联， Channel关注了事件后就变成了SelectionKey
        //Register the socket for select events.
        ssc.register(selector,SelectionKey.OP_ACCEPT);

        for(;;){
            selector.select();

            Set readyKeys = selector.selectedKeys();
            Iterator i = readyKeys.iterator();

            while(i.hasNext()){
                SelectionKey sk = (SelectionKey) i.next();
                i.remove();

                if(sk.isAcceptable()){
                    doAccept(sk);
                }
                else if(sk.isValid() && sk.isReadable()){
                    if(!geym_time_stat.containsKey( ((SocketChannel)sk.channel()).socket() ))
                        geym_time_stat.put( ((SocketChannel)sk.channel()).socket(), System.currentTimeMillis() );

                    doRead(sk);
                }
                else if(sk.isValid() && sk.isWritable()){
                    doWrite(sk);
                    long e = System.currentTimeMillis();
                    long b = geym_time_stat.remove( ((SocketChannel)sk.channel()).socket() );
                    System.out.println("spend:" + (e-b)+"ms");
                }
            }

        }
    }

    public static void main(String[] args){
        MultiThreadNIOEchoServer echoServer = new MultiThreadNIOEchoServer();

        try{
            echoServer.startServer();
        }catch(Exception e){
            System.out.println("Exception caught, problem exiting...");
            e.printStackTrace();
        }
    }
}
