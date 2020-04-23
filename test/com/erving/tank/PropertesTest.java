package com.erving.tank;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Properties;

public class PropertesTest {

    @Test
    public void test(){
        Properties properties = new Properties();
        try {
            properties.load(PropertesTest.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str = (String) properties.get("initTankNumber");
        System.out.println(str);
    }
}
