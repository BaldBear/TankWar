package com.erving.tank.strategy;

import com.erving.tank.*;

public class TwoBullets implements FireStrategy {

    @Override
    public void fire(Player player) {
        int bx = player.getX() + ResourceMgr.tankWidth / 2 - ResourceMgr.bulletWidth / 2 ;
        int by = player.getY() + ResourceMgr.tankHeight / 2 - ResourceMgr.bulletHeight / 2;
        Bullet bullet;
        Bullet bullet2;
        if(player.getDir()== Dir.L || player.getDir() == Dir.R){
            bullet = new Bullet(bx, by+10, player.getDir(), player.getGroup());
            bullet2 = new Bullet(bx, by-10, player.getDir(), player.getGroup());
        }else{
            bullet2 = new Bullet(bx-10, by, player.getDir(), player.getGroup());
            bullet = new Bullet(bx+10, by, player.getDir(), player.getGroup());

        }
        TankFrame.INSTANCE.add(bullet);
        TankFrame.INSTANCE.add(bullet2);




    }

}
