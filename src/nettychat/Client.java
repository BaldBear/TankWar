package nettychat;

import com.erving.tank.nettyCodec.TankMsg;
import com.erving.tank.nettyCodec.TankMsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class Client {

    private Channel channel;

    public void send(String str){
        channel.writeAndFlush(Unpooled.copiedBuffer(str.getBytes()));
    }

    public void connect(){
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(worker);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new TankMsgEncoder())
                            .addLast(new MyHandler());
                }
            });

            ChannelFuture channelFuture = b.connect("localhost", 8881).sync();

            //

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }


    private static class MyHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf)msg;
            try{
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.getBytes(byteBuf.readerIndex(), bytes);
                String str = new String(bytes);
                ClientFrame.INSTANCE.updateText(str);
            } finally {
                //ByteBuf使用的是计算机的直接内存，需要手动释放引用
                if(byteBuf != null){
                    ReferenceCountUtil.release(byteBuf);
                }
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception{

            ctx.writeAndFlush(new TankMsg(100, 150));

//            ByteBuf byteBuf = Unpooled.copiedBuffer("A Client has conneted".getBytes());
//            ctx.writeAndFlush(byteBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            super.exceptionCaught(ctx, cause);
        }
    }

}
