package com.erving.tank.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(worker);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new MyHandler());
            }
        });

        ChannelFuture channelFuture = b.connect("localhost", 8888).sync();

        //
        channelFuture.channel().closeFuture().sync();

        worker.shutdownGracefully();
    }

    private static class MyHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println(msg.toString());
            ByteBuf byteBuf = (ByteBuf)msg;

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception{
            ByteBuf byteBuf = Unpooled.copiedBuffer("erving".getBytes());
            ctx.writeAndFlush(byteBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            super.exceptionCaught(ctx, cause);
        }
    }

}
