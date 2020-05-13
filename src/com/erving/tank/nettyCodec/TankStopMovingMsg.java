package com.erving.tank.nettyCodec;

import com.erving.tank.Dir;
import com.erving.tank.Tank;
import com.erving.tank.TankFrame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class TankStopMovingMsg extends Msg{

    private UUID id;
    private int x, y;

    public TankStopMovingMsg() {
    }

    public TankStopMovingMsg(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public UUID getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {

            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

            dos.writeInt(x);
            dos.writeInt(y);
            bytes = baos.toByteArray();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                dos.close();
                baos.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try{
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                dis.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        //自身发送的消息不处理
        if(this.id.equals(TankFrame.INSTANCE.getGameModel().getPlayer().getId())){
            return;
        }

        Tank t = TankFrame.INSTANCE.getGameModel().findTankByUUID(this.id);
        if(t != null){
            t.setMoving(false);
            t.setX(this.x);
            t.setY(this.y);
        }
    }

    @Override
    public MsgType getMsgTpye() {
        return MsgType.TankStopMovingMsg;
    }

    @Override
    public String toString() {
        return "TankStartMovingMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }


}
