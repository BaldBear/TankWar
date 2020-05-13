package com.erving.tank;

import com.erving.tank.nettyCodec.TankJoinMsg;
import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank extends AbstractGameObject{

    private int x, y;
    private int oldX, oldY;     //记录上一步的位置，用于实现 back() 方法
    private int w=ResourceMgr.bulletWidth;
    private int h = ResourceMgr.bulletHeight;
    private Rectangle body;
    private UUID id;
    private boolean moving = true;


    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.group = msg.getGroup();
        this.moving = msg.isStop();
        this.alive = msg.isAlive();
        this.id = msg.getId();

        this.body = new Rectangle(x, y, w, h);
    }

    public Group getGroup() {
        return group;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public UUID getID() {
        return id;
    }

    public Rectangle getBody() {
        return body;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    private Dir dir = Dir.D;
    private static final int SPEED = 6;
    private Group group = Group.BAD;
    private boolean alive = true;

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.body = new Rectangle(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {

        if(!this.alive)return;

        switch (dir) {
            case L:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankL:ResourceMgr.p1tankL, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankR:ResourceMgr.p1tankR, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankU:ResourceMgr.p1tankU, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankD:ResourceMgr.p1tankD, x, y, null);
                break;
        }

            move();
    }

    Random r = new Random();

    private void move() {
        if(!moving) return;

        oldX = x;
        oldY = y;
        switch (dir) {
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
        body.x = x;
        body.y = y;
        boundaryCheak();
//        RandomDir();
//        if(r.nextInt(100) > 88){
//            fire();
//        }
    }

    private void fire() {
        //计算子弹发射位置
        int bx = x + ResourceMgr.tankWidth / 2 - ResourceMgr.bulletWidth / 2;
        int by = y + ResourceMgr.tankHeight / 2 - ResourceMgr.bulletHeight / 2;

        Bullet bullet = new Bullet(this.id, bx, by, dir, group);
        TankFrame.INSTANCE.getGameModel().add(bullet);
    }

    public void die() {
        this.alive = false;
        TankFrame.INSTANCE.getGameModel().add(new Explode(x, y));

    }

    private void RandomDir(){
        if(r.nextInt(100) > 77){
            this.dir = Dir.RandomDirection();
        }
    }

    private void boundaryCheak(){
        if(x<0 || x>TankFrame.INSTANCE.GAME_WIDTH-ResourceMgr.tankWidth || y<30 || y>TankFrame.INSTANCE.GAME_HEIGHT -ResourceMgr.tankWidth){
            back();
        }
    }


    public void back(){
        this.x = oldX;
        this.y = oldY;
    }

}
