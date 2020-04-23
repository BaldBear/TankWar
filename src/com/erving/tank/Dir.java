package com.erving.tank;

import java.util.Random;

public enum  Dir {
    L,      //左
    R ,     //右
    U,      //上
    D;      //下

    public static Random random = new Random();

    public static Dir RandomDirection(){
        int num = random.nextInt(values().length);
        return values()[num];
    }


}
