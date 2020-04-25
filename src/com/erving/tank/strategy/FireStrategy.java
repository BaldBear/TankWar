package com.erving.tank.strategy;

import com.erving.tank.Player;

import java.io.Serializable;

public interface FireStrategy extends Serializable {

    public void fire(Player player);
}
