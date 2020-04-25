package com.erving.tank.collides;

import com.erving.tank.AbstractGameObject;

import java.io.Serializable;

public interface Collider extends Serializable {

    public boolean collide(AbstractGameObject go1, AbstractGameObject go2);
}
