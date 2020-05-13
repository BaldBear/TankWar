package com.erving.tank.nettyCodec;

import com.erving.tank.Bullet;
import com.erving.tank.Tank;
import com.erving.tank.TankFrame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class TankDieMsg extends Msg {

    private UUID tankId;
    private UUID bulletId;

    public void setTankId(UUID tankId) {
        this.tankId = tankId;
    }

    public void setBulletId(UUID bulletId) {
        this.bulletId = bulletId;
    }

    public TankDieMsg() {
    }

    public TankDieMsg(UUID tankId, UUID bulletId) {
        this.tankId = tankId;
        this.bulletId = bulletId;
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(tankId.getMostSignificantBits());
            dos.writeLong(tankId.getLeastSignificantBits());

            dos.writeLong(bulletId.getMostSignificantBits());
            dos.writeLong(bulletId.getLeastSignificantBits());

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

            this.tankId = new UUID(dis.readLong(), dis.readLong());
            this.bulletId = new UUID(dis.readLong(), dis.readLong());

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
        Bullet b = TankFrame.INSTANCE.getGameModel().findBulletByUUID(this.bulletId);
        if(b!=null){
            b.die();
        }
        Tank t = TankFrame.INSTANCE.getGameModel().findTankByUUID(this.tankId);

        if(this.tankId.equals(TankFrame.INSTANCE.getGameModel().getPlayer().getId())){
            TankFrame.INSTANCE.getGameModel().getPlayer().die();
        }else {
            if(t != null){
                t.die();
            }
        }


    }

    @Override
    public MsgType getMsgTpye() {
        return MsgType.TankDieMsg;
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "tankId=" + tankId +
                ", bulletId=" + bulletId +
                '}';
    }
}
