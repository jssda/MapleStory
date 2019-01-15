/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import com.neuedu.maplestory.client.MapleStoryClient;
import com.neuedu.maplestory.constant.Constant;
import com.neuedu.maplestory.util.ImageUtil;

/**
 * @author jssd 怪物类
 */
public class Mob {

	private static Image mobsActionImg[] = new Image[100];

	static {
		// 站立图片
		for (int i = 0; i < 6; i++) {
			mobsActionImg[i] = ImageUtil.getImage("mob_stand_left1_" + i);
		}
		// 移动图片
		for (int i = 6; i < 12; i++) {
			mobsActionImg[i] = ImageUtil.getImage("mob_move_left1_" + (i - 6));
		}
		// 死亡图片
		for (int i = 12; i < 25; i++) {
			mobsActionImg[i] = ImageUtil.getImage("mob_die_left1_" + (i - 12));
		}
	}

	private int x;
	private int y; //坐标
	private int height; //图片高度
	private int width; //图片宽度
	private boolean live = true; // 是否存活
	MapleStoryClient msc; //客户端引用
	private int HP; //怪物HP
	private BloodBar bloodBar; //怪物HP

	public Mob(MapleStoryClient msc) {
		this.msc = msc;
		this.height = mobsActionImg[0].getHeight(null);
		this.width = mobsActionImg[0].getWidth(null);
		this.x = 600;
		this.y = msc.back.getFloor() - this.height;
		this.HP = Constant.MOB_HP;
		bloodBar = new BloodBar(x, y, 5, this.width);
	}

	public Mob(MapleStoryClient msc, int x) {
		this(msc);
		this.x = x;
		bloodBar = new BloodBar(x, y, 5, this.width);
	}

	/**
	 * 画出怪物
	 * 
	 * @param g
	 *            void
	 */

	// 图片轮播计数器
	private int count_stand_left = 0;
	private int count_die_left = 12;

	/**
	 * 画动物的方法
	 * @param g void
	 */
	public void draw(Graphics g) {
		if (this.isLive()) {
			count_stand_left++;
			if (count_stand_left >= 6) {
				count_stand_left = 0;
			}
			g.drawImage(mobsActionImg[count_stand_left], x, y, null);
			bloodBar.draw(g);
		} else {
			count_die_left++;
			if (count_die_left < 25) {
				g.drawImage(mobsActionImg[count_die_left], x, y, null);
			} else {
				this.msc.mobs.remove(this);
			}

		}
	}

	/**
	 * 取得图片矩形
	 * 
	 * @return Rectangle
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * 怪物被击中, 掉血 void
	 */
	public void hasHit() {
		this.HP -= 10;
		if (this.HP <= 0) {
			this.setLive(false);
		}
	}

	/**
	 * @author jssd 血条内部类
	 */
	class BloodBar {
		private int x;
		private int y; // 坐标
		private int height; // 血条高度
		private int width; // 血条长度

		/**
		 * 默认构造函数
		 * @param x
		 * @param y
		 * @param height
		 * @param width
		 */
		public BloodBar(int x, int y, int height, int width) {
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
		}

		/**
		 * 画出血条的方法
		 * 
		 * @param g
		 *            void
		 */
		private void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x, y - 15, width, height);
			g.setColor(Color.GREEN);
			g.fillRect(x, y - 15, width * Mob.this.HP / Constant.MOB_HP, height);
			g.setColor(c);
		}
	}

	/**
	 * 是否存活getter方法
	 * 
	 * @return boolean
	 */
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
