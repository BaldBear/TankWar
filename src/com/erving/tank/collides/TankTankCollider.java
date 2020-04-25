package com.erving.tank.collides;

import com.erving.tank.AbstractGameObject;
import com.erving.tank.Tank;

public class TankTankCollider implements Collider {
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Tank && go2 instanceof Tank){
            Tank t1 = (Tank) go1;
            Tank t2 = (Tank)go2;
            //如果坦克已经消失，不再检测碰撞
            if(!t1.isAlive() || !t2.isAlive())return false;
            //发生碰撞，坦克和子弹都消亡
            if( t1.getBody().intersects(t2.getBody())){
                t1.back();
                t2.back();
            }
        }
        return true;
    }
}
