package com.erving.tank.collides;

import com.erving.tank.AbstractGameObject;
import com.erving.tank.PropertiesMgr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class CollideChain implements Serializable {

    private List<Collider> colliders;
    public void initColliders() {
        colliders = new ArrayList<>();
        String[] names = PropertiesMgr.get("colliders").split(",");
        for (String name : names) {
            try {
                Class clazz = Class.forName("com.erving.tank.collides." + name);
                colliders.add((Collider) clazz.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void collide(AbstractGameObject go1, AbstractGameObject go2){
        for(Collider collider : colliders){
            if(!collider.collide(go1, go2)){
                break;
            }
        }
    }

}
