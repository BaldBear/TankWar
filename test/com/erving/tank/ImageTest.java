package com.erving.tank;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageTest {

    @Test
    public void test(){

        try{
            BufferedImage img = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("img/GoodTank1.png"));
            BufferedImage bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("img/BadTank1.png"));
            Assert.assertNotNull(img);
            Assert.assertNotNull(bulletU);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotateImg(){

        try{
            BufferedImage tankU = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("img/BadTank1.png"));
            BufferedImage tankL = ImageUtil.rotateImage(tankU, 270);
            Assert.assertNotNull(tankL);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
