/**
 * 
 */
package com.neuedu.maplestory.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 游戏工具类, 加载游戏资源, 图片, 声音
 * @author jssd
 *
 */
public class GameUtil {
	
	/**
	 * 静态反射加载图片资源
	 * @param imgPath
	 * @return Image
	 */
	public static Image getImage(String imgPath) {
		URL u = GameUtil.class.getClassLoader().getResource(imgPath);
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(u);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img;
	}

}
