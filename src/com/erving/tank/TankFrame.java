package com.erving.tank;


import com.erving.tank.collides.CollideChain;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankFrame extends Frame {
    //单例
    public static final TankFrame INSTANCE = new TankFrame();

    private GameModel gameModel;
    private Player player;

    int GAME_WIDTH =Integer.parseInt(PropertiesMgr.get("GAME_WIDTH"));
    int GAME_HEIGHT = Integer.parseInt(PropertiesMgr.get("GAME_HEIGHT"));

    private TankFrame(){
        this.setLocation(100,100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        gameModel = new GameModel();
        player = gameModel.getPlayer();
        this.addKeyListener(new TankKeyListener());
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    @Override
    public void paint(Graphics g) {
        gameModel.paint(g);

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


    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }
    }

}
