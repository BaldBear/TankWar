package com.erving.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertiesMgr {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(PropertiesMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return (String)properties.get(key);
    }



}
