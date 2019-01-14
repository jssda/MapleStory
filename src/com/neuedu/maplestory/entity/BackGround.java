/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Graphics;
import java.awt.Image;

import com.neuedu.maplestory.util.ImageUtil;

/**
 * @author jssd
 * 地图背景实体类, 封装了地图的位置
 */
public class BackGround {
	private int x;
	private int y;
	private Image img;
	
	/**
	 * 默认构造函数, 地图为雪地堡垒, 位置为零
	 */
	public BackGround() {
		this.img = ImageUtil.getImage("bg");
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * 重载构造函数,添加地图位置选项
	 * @param x
	 * @param y
	 */
	public BackGround(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * 重载构造函数, 提供换地图选项
	 * @param img
	 */
	public BackGround(Image img) {
		this.img = img;
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, this.x, this.y, null);
	}
}
