package com.erving.tank;

import com.erving.tank.collides.BulletWallCollider;
import com.erving.tank.collides.CollideChain;
import com.erving.tank.collides.Collider;
import com.erving.tank.collides.TankBulletCollider;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    //单例
    public static final TankFrame INSTANCE = new TankFrame();

    private Player myTank=null;
    private List<AbstractGameObject> agos ;
    private CollideChain collideChain;
    private Wall wall;

    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 640;

    private TankFrame(){
        this.setLocation(100,100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener());
        initObject();
    }

    private void initObject(){
        myTank = new Player(200,200,Dir.R, Group.GOOD);

        agos = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertiesMgr.get("initTankNumber"));

        collideChain = new CollideChain();
        collideChain.initColliders();

        this.add(new Wall(200, 300, 300, 30));

        for(int i = 0; i< tankCount; i++){
            this.add(new Tank(500,500, Dir.RandomDirection(), Group.BAD));
        }
    }

    public void add(AbstractGameObject ago){
        this.agos.add(ago);
    }



    @Override
    public void paint(Graphics g) {

        //显示敌方数量
        Color c = g.getColor();
        g.setColor(Color.BLACK);
//        g.drawString("enemies: " + tanks.size(), 10, 50);
//        g.setColor(c);


        myTank.paint(g);

        for(int i=0; i<agos.size();i++){
            AbstractGameObject go1 = agos.get(i);
            for(int j=i+1; j<agos.size(); j++){
                AbstractGameObject go2 = agos.get(j);
                collideChain.collide(go1, go2);
            }
            agos.get(i).paint(g);
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



    /**
     * 使用内部类,高内聚、低耦合
     * 创建Listener类
     */
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



}
