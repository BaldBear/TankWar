package com.erving.tank;

import com.erving.tank.nettyCodec.Client;

public class Main {
    public static void main(String[] args) {

        TankFrame.INSTANCE.setVisible(true);

//        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TankFrame.INSTANCE.repaint();
            }
        }).start();

        Client.INSTANCE.connect();

    }
}
