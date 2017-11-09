package netty.serverClient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

public class HelloHandler extends SimpleChannelHandler{
    /*
    * 接收消息
    * */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("messageReceived");
        //在没有在pipline里面使用decoder时可以用这个方法
        //        ChannelBuffer message = (ChannelBuffer) e.getMessage();
        //        System.out.println(new String( message.array()) );
        //在pipline里面使用了decoder以后直接可以输出message
        System.out.println(e.getMessage());


        //回写数据
        //在没有在pipline里面使用encoder时可以用这个方法
        //        ctx.getChannel().write( ChannelBuffers.copiedBuffer("hi".getBytes()) );
        //在pipline里面使用了encoder后可以用
        ctx.getChannel().write("hi");

        super.messageReceived(ctx, e);
    }

    /*
    * 捕获异常
    * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println("exception caught");
        super.exceptionCaught(ctx, e);
    }

    /*
    * 新连接
    * */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel Connected");
        super.channelConnected(ctx, e);
    }


    /*
    * 必须是连接已经建立，关闭通道的时候才会触发
    * */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel disConnected");
        super.channelDisconnected(ctx, e);
    }


    /*
    * channel关闭的时候触发
    * */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("Channel Closed");
        super.channelClosed(ctx, e);
    }
}
