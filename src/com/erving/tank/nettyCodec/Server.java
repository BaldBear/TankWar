package com.erving.tank.nettyCodec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    //保存所有的客户端通道
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void ServerStart(){
        //负责响应请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(2);
        //负责服务
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try{
            //Server启动辅助类
            ServerBootstrap b = new ServerBootstrap();

            ChannelFuture channelFuture =
                    b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyChildInitializer())
                    .bind(8888)
                    .sync();

            ServerFrame.INSTANCE.updateServerMsg("server start sucessfully!");

            channelFuture.channel().closeFuture().sync();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private static class MyChildInitializer extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
//                    .addLast(new TankMsgDecoder())
                    .addLast(new MyChildHandler());
        }
    }

    private static class MyChildHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelActive(ChannelHandlerContext ctx){
            clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            ServerFrame.INSTANCE.updateServerMsg(msg.toString());
            clients.writeAndFlush(msg);
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
            cause.printStackTrace();
            clients.remove(ctx.channel());
            ctx.close();
        }
    }
}

