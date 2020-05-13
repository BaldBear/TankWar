package com.erving.tank.nettyCodec;

import com.erving.tank.*;


import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg{
    private int x,y;
    private Dir dir;
    private boolean stop;
    private boolean alive;
    private Group group;

    private UUID id;

    public TankJoinMsg(){};

    public TankJoinMsg(Player player){
        this.x = player.getX();
        this.y = player.getY();
        this.dir = player.getDir();
        this.stop = false;
        this.group = player.getGroup();
        this.alive = player.isAlive();
        this.id = player.getId();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }

    public boolean isStop() {
        return stop;
    }

    public boolean isAlive() {
        return alive;
    }

    public Group getGroup() {
        return group;
    }

    public UUID getId() {
        return id;
    }

    /**
     *
     * @param bytes
     * load the datas in byte array into the TankJoinMsg
     */
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.stop = dis.readBoolean();
            this.alive = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(dis != null)
                    dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] toBytes(){
        byte[] bytes = null;
        DataOutputStream dos = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            dos = new DataOutputStream(bos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(stop);
            dos.writeBoolean(alive);
            dos.writeInt(group.ordinal());
            //写出UUID的高位和低位
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(bos!=null)
                    bos.close();
                if(dos != null)
                    dos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public  void handle(){

        // if this msg'id equals my tanks's id, return
        if(TankFrame.INSTANCE.getGameModel().findTankByUUID(id) != null) return;
        if(this.id.equals(TankFrame.INSTANCE.getGameModel().getPlayer().getId())) return;


        Tank t = new Tank(this);

        TankFrame.INSTANCE.getGameModel().add(t);

        Client.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getGameModel().getPlayer()));

    }

    @Override
    public MsgType getMsgTpye() {
        return MsgType.TankJoinMsg;
    }

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", stop=" + stop +
                ", alive=" + alive +
                ", group=" + group +
                ", id=" + id +
                '}';
    }
}
