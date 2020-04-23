package com.erving.tank.strategy;

import com.erving.tank.Bullet;
import com.erving.tank.Player;
import com.erving.tank.ResourceMgr;
import com.erving.tank.TankFrame;

public class Default implements FireStrategy{
    @Override
    public void fire(Player player) {
        int bx = player.getX() + ResourceMgr.tankWidth / 2 - ResourceMgr.bulletWidth / 2;
        int by = player.getY() + ResourceMgr.tankHeight / 2 - ResourceMgr.bulletHeight / 2;
        Bullet bullet = new Bullet(bx, by, player.getDir(), player.getGroup());
        TankFrame.INSTANCE.add(bullet);
    }
}
