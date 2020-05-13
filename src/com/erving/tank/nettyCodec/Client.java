package com.erving.tank.nettyCodec;

import com.erving.tank.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;



public class Client {

    public static final Client INSTANCE = new Client();
    private Channel channel = null;
    private Client() {
    }

    public void send(Msg msg){
        channel.writeAndFlush(msg);
    }

    public void connect(){
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b.group(worker);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    channel = socketChannel;
                    socketChannel.pipeline()
                            .addLast(new TankMsgEncoder())
                            .addLast(new TankMsgDecoder())
                            .addLast(new MyHandler());
                }
            });

            ChannelFuture channelFuture = b.connect("localhost", 8888).sync();
            System.out.println("connected to server");

            channelFuture.channel().closeFuture().sync();
            System.out.println("go on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }


//    private static class MyHandler extends ChannelInboundHandlerAdapter{
//
//        @Override
//        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//            ByteBuf byteBuf = (ByteBuf)msg;
//            try{
//                byte[] bytes = new byte[byteBuf.readableBytes()];
//                byteBuf.getBytes(byteBuf.readerIndex(), bytes);
//                String str = new String(bytes);
//                ServerFrame.INSTANCE.updateClientMsg(msg.toString());
//            } finally {
//                //ByteBuf使用的是计算机的直接内存，需要手动释放引用
//                if(byteBuf != null){
//                    ReferenceCountUtil.release(byteBuf);
//                }
//            }
//        }
//
//        @Override
//        public void channelActive(ChannelHandlerContext ctx) throws Exception{
//            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGameModel().getPlayer()));
//        }
//
//        @Override
//        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
//            super.exceptionCaught(ctx, cause);
//        }
//    }

    private  class MyHandler extends SimpleChannelInboundHandler<Msg> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception{
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getGameModel().getPlayer()));
        }


        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            super.exceptionCaught(ctx, cause);
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            System.out.println(msg);
            msg.handle();
        }
    }


//    public static void main(String[] args) {
//        Client c = new Client();
//        c.connect();
//    }

}
