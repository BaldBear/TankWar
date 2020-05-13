package com.erving.tank.nettyCodec;

import com.erving.tank.Bullet;
import com.erving.tank.Dir;
import com.erving.tank.Group;
import com.erving.tank.TankFrame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class NewBulletMsg extends Msg{

    private UUID playerId;
    private UUID id;
    private int x, y;
    private Dir dir;
    private Group group;

    public NewBulletMsg() {
    }

    public NewBulletMsg(Bullet bullet){
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }


    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());

            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());

            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

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

            this.playerId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];


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
        if(this.playerId.equals(TankFrame.INSTANCE.getGameModel().getPlayer().getId())){
            return;
        }

        Bullet bullet = new Bullet(this.playerId, this.x, this.y, this.dir, this.group);
        bullet.setId(this.id);
        TankFrame.INSTANCE.getGameModel().add(bullet);
    }

    @Override
    public MsgType getMsgTpye() {
        return MsgType.NewBulletMsg;
    }

    @Override
    public String toString() {
        return "NewBulletMsg{" +
                "playerId=" + playerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }
}
