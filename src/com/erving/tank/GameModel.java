package com.erving.tank;

import com.erving.tank.collides.CollideChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameModel implements Serializable{

    private Player myTank;
    private List<AbstractGameObject> objects ;
    private CollideChain collideChain;

    private int GAME_WIDTH =Integer.parseInt(PropertiesMgr.get("GAME_WIDTH"));
    private int GAME_HEIGHT = Integer.parseInt(PropertiesMgr.get("GAME_HEIGHT"));

    private Random r = new Random();

    public GameModel(){
        initObject();
    }



    private void initObject(){


        myTank = new Player(r.nextInt(700),r.nextInt(400),
                Dir.values()[r.nextInt(Dir.values().length)],
                Group.values()[r.nextInt(Group.values().length)]);

        objects = new ArrayList<>();

        int tankCount = Integer.parseInt(PropertiesMgr.get("initTankNumber"));

        collideChain = new CollideChain();
        collideChain.initColliders();

//        this.add(new Wall(200, 300, 300, 30));

        Random r = new Random();

        for(int i = 0; i< tankCount; i++){
            int x = r.nextInt(GAME_WIDTH-100);
            int y = r.nextInt(GAME_HEIGHT-100);
            Tank newTank = new Tank(x,y, Dir.RandomDirection(), Group.BAD);
            boolean add = true;
            for(int j = 0; j<objects.size(); j++){
                AbstractGameObject ago = objects.get(j);
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
        objects.add(ago);
    }

    public void paint(Graphics g) {

        //显示敌方数量
        Color c = g.getColor();
        g.setColor(Color.BLACK);
//        g.drawString("enemies: " + tanks.size(), 10, 50);
//        g.setColor(c);


        myTank.paint(g);

        int size = objects.size();

        for(int i=0; i<size;i++){
            AbstractGameObject go1 = objects.get(i);
            for(int j=i+1; j<objects.size(); j++){
                AbstractGameObject go2 = objects.get(j);
                collideChain.collide(go1, go2);
            }
            if(go1.isAlive())
                objects.get(i).paint(g);
        }
        for(int i=0; i<objects.size();i++) {
            AbstractGameObject go1 = objects.get(i);
            if (!go1.isAlive()) {
                objects.remove(i);
            }
        }

    }
    public Player getPlayer() {
        return myTank;
    }



    public Tank findTankByUUID(UUID id) {
        for(int i=0; i<objects.size(); i++) {
            if(objects.get(i) instanceof Tank ) {
                Tank t = (Tank)objects.get(i);
                UUID tId = t.getID();
                if(tId.equals(id)){
                    return t;
                }
            }
        }
        return null;
    }

    public Bullet findBulletByUUID(UUID id) {
        for(int i=0; i<objects.size(); i++) {
            if(objects.get(i) instanceof Bullet) {
                Bullet b = (Bullet) objects.get(i);
                UUID tId = b.getId();
                if(tId.equals(id)){
                    return b;
                }
            }
        }
        return null;
    }
}
