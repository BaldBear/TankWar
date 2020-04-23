package com.erving.tank.collides;

import com.erving.tank.AbstractGameObject;

public interface Collider {

    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
