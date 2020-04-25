package com.erving.tank.collides;

import com.erving.tank.AbstractGameObject;
import com.erving.tank.Bullet;
import com.erving.tank.Group;
import com.erving.tank.Wall;


public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObject go1, AbstractGameObject go2) {
        if(go1 instanceof Bullet && go2 instanceof Wall){
            Bullet b = (Bullet)go1;
            Wall w = (Wall)go2;

//            if(b.getGroup()== Group.GOOD){
//                System.out.println(b.hashCode() + ": " + b.getBody().x + ", "+b.getBody().y);
//                System.out.println("");
//            }

            if(b.getBody().intersects(w.getBody())){
                b.die();
                return false;
            }
        }else if(go2 instanceof Bullet && go1 instanceof Wall){
            collide(go2, go1);
        }

        return true;
    }
}
