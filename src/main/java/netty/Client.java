package netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[]args){
        ClientBootstrap bootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        bootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));

        //设置管道的工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipline = Channels.pipeline();
                pipline.addLast("decoder", new StringDecoder());
                pipline.addLast("encoder", new StringEncoder());
                pipline.addLast("hiHandler", new HiHandler());

                return pipline;
            }
        });

        ChannelFuture connect =bootstrap.connect(new InetSocketAddress("127.0.0.1",10101));
        Channel channel = connect.getChannel();

        System.out.println("client start");

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("请输入");
            channel.write(scanner.next());
        }

    }
}
