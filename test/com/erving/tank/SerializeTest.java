package com.erving.tank;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SerializeTest {

    @Test
    public void TestSave(){
        File file = new File("/save/save.data");
        try {
            GameModel gameModel = new GameModel();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
