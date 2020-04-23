package com.erving.tank;

import com.erving.tank.collides.Collider;

import java.awt.*;

public class Wall extends AbstractGameObject {

    private int x;
    private int y;
    private int w;
    private int h;
    private Rectangle body;

    public Wall(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        body = new Rectangle(x, y, w, h);
    }

    public Rectangle getBody() {
        return body;
    }

    @Override
    public void paint(Graphics g) {
        Color old = g.getColor();
        g.setColor(Color.green);
        g.fillRect(x,y,w,h);
        g.setColor(old);
        g.drawRect(x, y, w, h);

    }
}
