package com.erving.tank;

import java.awt.*;

public class Bullet {
    private int x,y;
    private Dir dir;
    private Group group;
    boolean alive = true;
    private static final int SPEED = 30;

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }


    public void paint(Graphics g) {


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
        boundaryCheak();
    }

    private void boundaryCheak(){
        if(x<0 || x>TankFrame.GAME_WIDTH || y<0 || y>TankFrame.GAME_HEIGHT){
            alive = false;
        }
    }

    public boolean getAlive(){
        return alive;
    }

    //和坦克的碰撞检测
    public void collidesWithTank(Tank tank){
        //如果坦克已经消失，不再检测碰撞
        if(!tank.isAlive())return;

        if(this.group == tank.getGroup())return;

        Rectangle bu = new Rectangle(x, y, ResourceMgr.bulletWidth, ResourceMgr.bulletHeight);
        Rectangle t = new Rectangle(tank.getX(), tank.getY(), ResourceMgr.tankWidth, ResourceMgr.tankHeight);

        //发生碰撞，坦克和子弹都消亡
        if(bu.intersects(t)){
            this.die();
            tank.die();
        }
    }

    private void die() {
        this.alive=false;
    }


}
