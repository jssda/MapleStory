/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Color;
import java.awt.Graphics;

import com.neuedu.maplestory.constant.Constant;

/**
 * @author jssd
 *
 */
class BloodBar {
	private int height; // 血条高度
	private int width; // 血条长度
	private Life life; //实体

	/**
	 * 默认构造函数
	 * 
	 * @param height
	 * @param width
	 */
	public BloodBar(Life life, int height, int width) {
		this.height = height;
		this.width = width;
		this.life = life;
	}

	/**
	 * 画出血条的方法
	 * 
	 * @param g
	 *            void
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.drawRect(life.x, life.y - 15, width, height);
		g.setColor(Color.GREEN);
		g.fillRect(life.x,life.y - 15, width * life.HP / Constant.MOB_HP, height);
		g.setColor(c);
	}
}
