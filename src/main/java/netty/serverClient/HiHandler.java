package netty.serverClient;

import org.jboss.netty.channel.*;

public class HiHandler extends SimpleChannelHandler {
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());

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
