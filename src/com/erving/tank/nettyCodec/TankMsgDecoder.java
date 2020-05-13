package com.erving.tank.nettyCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;


public class TankMsgDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 8) return;

        byteBuf.markReaderIndex();

        MsgType msgType= MsgType.values()[byteBuf.readInt()];
        int len = byteBuf.readInt();

        //判断数据是否完整接收
        if(byteBuf.readableBytes() < len){
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[len];

        byteBuf.readBytes(bytes);

        Msg msg = null;

        //根据MsgType创建对应的msg对象
        msg = (Msg) Class.forName("com.erving.tank.nettyCodec." + msgType.toString())
                .getDeclaredConstructor()
                .newInstance();

        msg.parse(bytes);
        list.add(msg);
    }
}
