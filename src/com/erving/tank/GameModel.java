package com.erving.tank;

import com.erving.tank.collides.CollideChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel implements Serializable{

    private Player myTank=null;
    private List<AbstractGameObject> agos ;
    private CollideChain collideChain;

    private int GAME_WIDTH =Integer.parseInt(PropertiesMgr.get("GAME_WIDTH"));
    private int GAME_HEIGHT = Integer.parseInt(PropertiesMgr.get("GAME_HEIGHT"));

    public GameModel(){
        initObject();
    }

    public Player getPlayer() {
        return myTank;
    }

    private void initObject(){
        myTank = new Player(200,200,Dir.R, Group.GOOD);

        agos = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertiesMgr.get("initTankNumber"));

        collideChain = new CollideChain();
        collideChain.initColliders();

        this.add(new Wall(200, 300, 300, 30));

        Random r = new Random();

        for(int i = 0; i< tankCount; i++){
            int x = r.nextInt(GAME_WIDTH-100);
            int y = r.nextInt(GAME_HEIGHT-100);
            Tank newTank = new Tank(x,y, Dir.RandomDirection(), Group.BAD);
            boolean add = true;
            for(int j = 0; j<agos.size(); j++){
                AbstractGameObject ago = agos.get(j);
                if(ago instanceof Tank){
                    Tank t = (Tank)ago;
                    if(t.getBody().intersects(newTank.getBody()))
                        add = false;
                }
            }
            if(add){
                this.add(newTank);
            }
        }
    }

    public void add(AbstractGameObject ago){
        this.agos.add(ago);
    }

    public void paint(Graphics g) {

        //显示敌方数量
        Color c = g.getColor();
        g.setColor(Color.BLACK);
//        g.drawString("enemies: " + tanks.size(), 10, 50);
//        g.setColor(c);


        myTank.paint(g);

        int size = agos.size();

        for(int i=0; i<size;i++){
            AbstractGameObject go1 = agos.get(i);
            for(int j=i+1; j<agos.size(); j++){
                AbstractGameObject go2 = agos.get(j);
                collideChain.collide(go1, go2);
            }
            if(go1.isAlive())
                agos.get(i).paint(g);
        }
        for(int i=0; i<agos.size();i++) {
            AbstractGameObject go1 = agos.get(i);
            if (!go1.isAlive()) {
                agos.remove(i);
            }
        }

    }





}
