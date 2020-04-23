package com.erving.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Tank extends AbstractGameObject{

    private int x, y;
    private int oldX, oldY;     //记录上一步的位置，用于实现 back() 方法
    private int w=ResourceMgr.bulletWidth;
    private int h = ResourceMgr.bulletHeight;
    private Rectangle body;

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

    public Rectangle getBody() {
        return body;
    }

    private Dir dir = Dir.D;
    private static final int SPEED = 4;
    private Group group = Group.BAD;
    private boolean alive = true;

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        body = new Rectangle(x, y, w, h);
    }

    @Override
    public void paint(Graphics g) {

        if(!this.alive)return;

        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y, null);
                break;
        }

            move();
    }

    Random r = new Random();

    private void move() {
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
        RandomDir();
        if(r.nextInt(100) > 88){
            fire();

        }
    }

    private void fire() {
        //计算子弹发射位置
        int bx = x + ResourceMgr.tankWidth / 2 - ResourceMgr.bulletWidth / 2;
        int by = y + ResourceMgr.tankHeight / 2 - ResourceMgr.bulletHeight / 2;

        Bullet bullet = new Bullet(bx, by, dir, group);
        TankFrame.INSTANCE.add(bullet);
    }

    public void die() {
        this.alive = false;
        TankFrame.INSTANCE.add(new Explode(x, y));
    }

    private void RandomDir(){
        if(r.nextInt(100) > 77){
            this.dir = Dir.RandomDirection();
        }
    }

    private void boundaryCheak(){
        if(x<0 || x>TankFrame.GAME_WIDTH-ResourceMgr.tankWidth || y<30 || y>TankFrame.GAME_HEIGHT -ResourceMgr.tankWidth){
            back();
        }
    }


    private void back(){
        this.x = oldX;
        this.y = oldY;
    }

}
