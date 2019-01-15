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
	private int x; //坐标
	private int y;
	private int height; //图片高度
	private int width;
	private Image img; //背景图片
	private int floor; //地面y坐标
	
	/**
	 * 默认构造函数, 地图为雪地堡垒, 位置为零
	 */
	public BackGround() {
		this.img = ImageUtil.getImage("bg");
		this.x = 0;
		this.y = 0;
		this.height = img.getHeight(null);
		this.setWidth(img.getWidth(null));
		this.floor = this.height - 135;
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
	
	/**
	 * 画出背景的方法
	 * @param g void
	 */
	public void draw(Graphics g) {
		g.drawImage(img, this.x, this.y, null);
	}

	/**
	 * 取得地面坐标的方法
	 * @return int
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

}
