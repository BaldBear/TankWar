package com.erving.tank;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.UUID;


public class TankFrame extends Frame {
    //单例
    public static final TankFrame INSTANCE = new TankFrame();

    private GameModel gameModel = new GameModel();

    int GAME_WIDTH =Integer.parseInt(PropertiesMgr.get("GAME_WIDTH"));
    int GAME_HEIGHT = Integer.parseInt(PropertiesMgr.get("GAME_HEIGHT"));

    private TankFrame(){
        this.setTitle("Tank War");
        this.setLocation(100,100);
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
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



    private class TankKeyListener extends KeyAdapter implements Serializable{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_S){
                save();
            }else if(key == KeyEvent.VK_L){
                load();
            }else{
                gameModel.getPlayer().keyPressed(e);
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            gameModel.getPlayer().keyReleased(e);
        }
    }

    /**
     * 实现保存功能
     */
    private void save(){
        ObjectOutputStream oos = null;
        try {
            File file = new File("save/s.data");
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this.gameModel);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实现从文件中读取存档功能
     */
    private void load(){
        ObjectInputStream ois = null;
        try {
            File file = new File("save/s.data");
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            this.gameModel = (GameModel) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
