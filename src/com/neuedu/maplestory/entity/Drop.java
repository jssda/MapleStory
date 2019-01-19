/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import com.neuedu.maplestory.util.ImageUtil;

/**
 * @author jssd 凋落物类
 */
public class Drop {
	private int x;
	private int y;
	public int type;
	private int width;
	private int height;
	private Image img;

	public Drop(int x, int y, int type) {
		this.x = x;
		this.y = y;
		switch (type) {
		case 1:
			img = ImageUtil.getImage("mob_drop_money");
			this.type = 1;
			break;
		case 2:
			img = ImageUtil.getImage("mob_drop_red");
			this.type = 2;
			break;
		case 3:
			img = ImageUtil.getImage("mob_drop_blue");
			this.type = 3;
			break;
		default:
			break;
		}
		
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}

	public void draw(Graphics g) {
//		System.out.println(img);
		g.drawImage(img, x, y, null);
	}
	
	/**
	 * 获得图片矩形
	 * @return Rectangle
	 */
	public Rectangle getRectangle() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}
}
