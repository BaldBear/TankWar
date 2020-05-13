package com.erving.tank.collides;

import com.erving.tank.AbstractGameObject;
import com.erving.tank.Bullet;
import com.erving.tank.Tank;
import com.erving.tank.nettyCodec.Client;
import com.erving.tank.nettyCodec.TankDieMsg;

public class TankBulletCollider implements Collider{


    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet)go1;
            Tank t = (Tank)go2;
            //如果坦克已经消失，不再检测碰撞
            if(!t.isAlive())return false;
            //发生碰撞，坦克和子弹都消亡
            if(b.getGroup() !=t.getGroup() && b.getBody().intersects(t.getBody())){
                b.die();
                t.die();

                Client.INSTANCE.send(new TankDieMsg(t.getID(), b.getId()));
                return false;
            }
        }else if(go2 instanceof Bullet && go1 instanceof Tank){
            collide(go2, go1);
        }
        return true;
    }


}
