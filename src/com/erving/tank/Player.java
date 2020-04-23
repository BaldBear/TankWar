package com.erving.tank;

import com.erving.tank.strategy.FireStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Dir dir;
    private boolean stop;
    private boolean bL, bR, bU, bD;
    private static final int SPEED = 8;
    private Group group = Group.GOOD;
    private boolean alive = true;

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

    public void paint(Graphics g) {

//        g.fillRect(x, y, 50, 50);

//        try {
//            BufferedImage tankL = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("img/p1tankL.gif"));
//            g.drawImage(tankL,x,y,null);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(this.alive == false)return;

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

        if (!stop) {
            move();
        }

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
        if (stop) return;
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
        boundaryCheak();
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
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }

        setMainDir();
    }

    private void setMainDir() {
        if (!bL && !bR && !bU && !bD)
            stop = true;
        if (bL && !bR && !bU && !bD) {
            dir = Dir.L;
            stop = false;
        }
        if (!bL && bR && !bU && !bD) {
            dir = Dir.R;
            stop = false;
        }
        if (!bL && !bR && bU && !bD) {
            dir = Dir.U;
            stop = false;
        }
        if (!bL && !bR && !bU && bD) {
            dir = Dir.D;
            stop = false;
        }
    }

    private void initFire() {
        String className = PropertiesMgr.get("fireStrategy");
        //选择子弹发射模式
        try {
            Class clazz = Class.forName("com.erving.tank.strategy."+ className);
            fireStrategy = (FireStrategy)clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fire(){
        fireStrategy.fire(this);
    }

    public void die() {
        this.alive = false;
    }

    private void boundaryCheak(){
        if(x<0 || x>TankFrame.GAME_WIDTH || y<0 || y>TankFrame.GAME_HEIGHT){
            this.stop = true;
        }
    }
}
