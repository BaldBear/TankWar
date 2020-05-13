package com.erving.tank;

import com.erving.tank.nettyCodec.Client;
import com.erving.tank.nettyCodec.NewBulletMsg;

import java.awt.*;
import java.util.UUID;

public class Bullet extends AbstractGameObject{
    private UUID playerId;
    private UUID id;
    private int x,y;
    private int w=ResourceMgr.bulletWidth;
    private int h = ResourceMgr.bulletHeight;
    private Dir dir;
    private Group group;
    private boolean alive = true;
    private static final int SPEED = 20;
    private Rectangle body;

    public Bullet(UUID playerId, int x, int y, Dir dir, Group group) {
        this.playerId = playerId;
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        body = new Rectangle(x,y,w,h);


    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Group getGroup() {
        return group;
    }

    public Rectangle getBody() {
        return body;
    }

    public void paint(Graphics g) {
        if(!this.alive)return;

        switch (dir){
            case L: g.drawImage(ResourceMgr.bulletL, x, y, null);break;
            case R: g.drawImage(ResourceMgr.bulletR, x, y, null);break;
            case U: g.drawImage(ResourceMgr.bulletU, x, y, null);break;
            case D: g.drawImage(ResourceMgr.bulletD, x, y, null);break;
        }
        move();
    }


    private void move(){
        switch (dir){
            case L: x -= SPEED;break;
            case R: x += SPEED;break;
            case U: y -= SPEED;break;
            case D: y += SPEED;break;
        }
        //更新用于碰撞检测的Rectangle对象坐标
        body.x = x;
        body.y = y;
        boundaryCheak();
    }

    private void boundaryCheak(){
        if(x<0 || x>TankFrame.INSTANCE.GAME_WIDTH || y<0 || y>TankFrame.INSTANCE.GAME_HEIGHT){
            alive = false;
        }
    }

    @Override
    public boolean isAlive(){
        return alive;
    }


    public void die() {
        this.alive=false;
    }


}
