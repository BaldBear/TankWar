package com.erving.tank.nettyCodec;

public abstract class Msg {
    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes);

    public abstract void handle();

    public abstract MsgType getMsgTpye();
}
