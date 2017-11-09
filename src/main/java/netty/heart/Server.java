package netty.heart;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//对于服务端来说，用于清除闲置会话
//对于客户端来说，用于检测会话是否断开，是否重连。
public class Server {
    public static void main(String[]args){
        //服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //设置niosocket工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boss,worker));

        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();

        //设置管道的工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipline = Channels.pipeline();
                pipline.addLast("idle", new IdleStateHandler(hashedWheelTimer,5,5,10));
                pipline.addLast("decoder", new StringDecoder());
                pipline.addLast("encoder", new StringEncoder());
                pipline.addLast("helloHandler", new HelloHandler());

                return pipline;
            }
        });

        bootstrap.bind(new InetSocketAddress(10101));

        System.out.println("Start!");
    }
}
