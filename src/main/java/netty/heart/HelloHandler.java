package netty.heart;

import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloHandler extends SimpleChannelHandler implements ChannelHandler {

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if(e instanceof IdleStateEvent){
            if( ((IdleStateEvent)e).getState() == IdleState.ALL_IDLE ){
                System.out.println("踢玩家下线");

                ChannelFuture write = ctx.getChannel().write("time out, you will close");
                write.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        ctx.getChannel().close();
                    }
                });
            }

//            SimpleDateFormat dateFormat = new SimpleDateFormat("ss");
//            IdleStateEvent event = (IdleStateEvent) e;
//            System.out.println(event.getState() + "  " + dateFormat.format(new Date()));
        } else {
            super.handleUpstream(ctx, e);
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println(e.getMessage());
    }
}
