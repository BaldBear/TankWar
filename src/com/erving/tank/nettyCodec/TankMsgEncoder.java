package com.erving.tank.nettyCodec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankMsgEncoder extends MessageToByteEncoder<Msg> {

//    @Override
//    protected void encode(ChannelHandlerContext channelHandlerContext, TankJoinMsg tankJoinMsg, ByteBuf byteBuf) throws Exception {
//        byte[] bytes = tankJoinMsg.toBytes();
//        byteBuf.writeInt(bytes.length);
//        byteBuf.writeBytes(bytes);
//    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        byte[] bytes = msg.toBytes();

        //前8个字节组成消息头
        byteBuf.writeInt(msg.getMsgTpye().ordinal());
        byteBuf.writeInt(bytes.length);

        byteBuf.writeBytes(bytes);

    }
}
