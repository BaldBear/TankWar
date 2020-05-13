package com.erving.tank;


import com.erving.tank.collides.Collider;
import com.erving.tank.nettyCodec.Client;
import com.erving.tank.nettyCodec.NewBulletMsg;
import com.erving.tank.nettyCodec.TankStartMovingMsg;
import com.erving.tank.nettyCodec.TankStopMovingMsg;
import com.erving.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Player extends AbstractGameObject {

    private int x, y;
    private Dir dir;
    private boolean moving = false;
    private boolean bL, bR, bU, bD;
    private static final int SPEED = 6;
    private Group group = Group.GOOD;
    private boolean alive = true;
    private UUID id = UUID.randomUUID();
    private FireStrategy fireStrategy;


    public Player(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        initFire();
    }


    public Dir getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
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


    public void paint(Graphics g) {

        if (!this.alive) return;

        //show id of the tank
        Color c = g.getColor();
        g.setColor(Color.red);
        g.drawString(id.toString(), x, y - 10);
        g.setColor(c);

        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.p1tankL, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.p1tankR, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.p1tankU, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.p1tankD, x, y, null);
                break;
        }
        if (moving) {
            move();
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public UUID getID() {
        return this.id;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }

        setMainDir();
    }

    private void move() {
        if (!moving) return;

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



    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_C:
                fire();
                break;
        }

        setMainDir();
    }

    private void setMainDir() {
        boolean oldMoving = moving;
        Dir oldDir = this.getDir();

        if (!bL && !bR && !bU && !bD){
            moving = false;
            Client.INSTANCE.send(new TankStopMovingMsg(this.id, this.x, this.y));
        }else {
            moving = true;

            if (bL && !bR && !bU && !bD) {
                dir = Dir.L;
            }
            if (!bL && bR && !bU && !bD) {
                dir = Dir.R;
            }
            if (!bL && !bR && bU && !bD) {
                dir = Dir.U;
            }
            if (!bL && !bR && !bU && bD) {
                dir = Dir.D;
            }

            if(!oldMoving || !this.dir.equals(oldDir)){
                Client.INSTANCE.send(new TankStartMovingMsg(this.id, this.x,this.y, this.dir));
            }

        }


    }

    private void initFire() {
        String className = PropertiesMgr.get("fireStrategy");
        //选择子弹发射模式
        try {
            Class clazz = Class.forName("com.erving.tank.strategy." + className);
            fireStrategy = (FireStrategy) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fire() {
        fireStrategy.fire(this);

    }

    public void die() {
        this.alive = false;
        TankFrame.INSTANCE.getGameModel().add(new Explode(x, y));
    }



}
