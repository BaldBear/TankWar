package com.erving.tank;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TankFrame extends Frame {
    //单例
    public static final TankFrame INSTANCE = new TankFrame();

    private Player myTank=null;
    private List<Bullet> bullets;
    private List<Tank> tanks;
    private List<Explode> explodes;

    int tankCount;

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 640;

    private TankFrame(){
        this.setLocation(100,100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());
        initObject();
    }

    private void initObject(){
        myTank = new Player(200,200,Dir.R, Group.GOOD);
        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        explodes = new ArrayList<>();
        tankCount = Integer.parseInt(PropertiesMgr.get("initTankNumber"));

        for(int i=0; i<tankCount; i++){
            tanks.add(new Tank(500,500, Dir.RandomDirection(), Group.BAD));
        }
    }

    public void add(Bullet bullet){
        bullets.add(bullet);
    }

    @Override
    public void paint(Graphics g) {

        //显示敌方数量
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.drawString("enemies: " + tanks.size(), 10, 50);
        g.setColor(c);


        myTank.paint(g);
        //绘制敌方坦克
        for(int i=0; i<tanks.size(); i++){
            if(!tanks.get(i).isAlive()){
                tanks.remove(i);
            }else{
                tanks.get(i).paint(g);
            }
        }

        //绘制子弹
        for(int i=0;i<bullets.size();i++){
            for(int j=0; j<tanks.size(); j++){

                bullets.get(i).collidesWithTank(tanks.get(j));
            }

            if(!bullets.get(i).getAlive()){
                bullets.remove(i);
            }else{
                bullets.get(i).paint(g);
            }
        }

        for(int i=0; i<explodes.size(); i++){
            if(!explodes.get(i).isAlive()){
                explodes.remove(i);
            }else{
                explodes.get(i).paint(g);
            }
        }


    }

    public void add(Explode explode) {
        explodes.add(explode);
    }


    //使用内部类,高内聚、低耦合
    //创建Listener类
    private class TankKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }


    private Image offScreenImage=null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.WHITE);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }


}
