package com.erving.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Explode {


    private int x,y;
    private int width, height;
    private int step = 0;
    private BufferedImage[] explodes;
    private boolean alive = true;

    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
        explodes = ResourceMgr.explodes;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(explodes[step],x,y,null);
        step++;
        if(step>=explodes.length){
            alive = false;
        }
    }

    public boolean isAlive(){
        return alive;
    }





}
