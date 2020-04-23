package com.erving.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
	加载图片资源，防止重复加载
 */
public class ResourceMgr {
	public static BufferedImage p1tankL, p1tankU, p1tankR, p1tankD;
	public static BufferedImage badTankL, badTankU, badTankR, badTankD; 
	public static BufferedImage bulletL, bulletU, bulletR, bulletD;
	public static int tankWidth, tankHeight;
	public static int bulletWidth, bulletHeight;
	public static BufferedImage[] explodes = new BufferedImage[16];
	
 	
	static {
		try {
			p1tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("img/GoodTank1.png"));
			p1tankL =  ImageUtil.rotateImage(p1tankU, -90);
			p1tankR =  ImageUtil.rotateImage(p1tankU, 90);
			p1tankD =  ImageUtil.rotateImage(p1tankU, 180);
			
			badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("img/BadTank1.png"));
			badTankL = ImageUtil.rotateImage(badTankU, -90);
			badTankR = ImageUtil.rotateImage(badTankU, 90);
			badTankD = ImageUtil.rotateImage(badTankU, 180);
			
			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("img/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU, -90);
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletU, 180);

			tankWidth = p1tankL.getWidth();
			tankHeight = p1tankL.getHeight();
			bulletWidth = bulletU.getWidth();
			bulletHeight = bulletU.getHeight();
			
			for(int i=0; i<16; i++)
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("img/e" + (i+1) + ".gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
