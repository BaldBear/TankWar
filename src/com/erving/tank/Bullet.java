package com.erving.tank;

import java.awt.*;

public class Bullet extends AbstractGameObject{
    private int x,y;
    private int w=ResourceMgr.bulletWidth;
    private int h = ResourceMgr.bulletHeight;
    private Dir dir;
    private Group group;
    private boolean alive = true;
    private static final int SPEED = 20;
    private Rectangle body;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        body = new Rectangle(x,y,w,h);
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

    //和坦克的碰撞检测
    public void collidesWithTank(Tank tank){

    }


    public void die() {
        this.alive=false;
    }


}
