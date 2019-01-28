/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.List;

import com.neuedu.maplestory.client.MapleStoryClient;
import com.neuedu.maplestory.util.ImageUtil;

/**
 * @author jssd 弓箭子弹类
 */
public class Bullet {
	private int x;
	private int y; // 坐标
	private int width; // 宽度
	private int height; // 高度
	private Image img; // 图像
	private int speed; // 速度
	private Direction dir; // 发射反向
	private boolean live = true; // 子弹是否存活
	private MapleStoryClient msc; // 客户端

	/**
	 * 默认构造方法, 初始化坐标, 图像, 速度
	 */
	public Bullet(MapleStoryClient msc) {
		this.msc = msc;
		this.img = ImageUtil.getImage("hero_shoot_bullet");
		this.width = img.getWidth(null);
		this.setHeight(img.getHeight(null));
		this.x = msc.hero.getX();
		this.y = msc.hero.getY();
		this.speed = 30;
	}

	/**
	 * 重载构造方法, 提供坐标, 发射发现选项
	 */
	public Bullet(MapleStoryClient msc, int x, int y, Direction dir) {
		this.msc = msc;
		this.img = ImageUtil.getImage("hero_shoot_bullet");
		this.width = img.getWidth(null);
		this.setHeight(img.getHeight(null));
		this.x = msc.hero.getX();
		this.y = msc.hero.getY();
		this.dir = dir;
		this.speed = 30;
	}

	/**
	 * 在客户端上画出图片, 假如朝向左边, 向左发射, 假如朝向右边, 向右发射
	 * 
	 * @param g
	 *            void
	 */
	public void draw(Graphics g) {
		if(this.isLive()) {
			if (this.dir == Direction.LEFT) {
				g.drawImage(img, x - this.width, y + msc.hero.getHeight() * 3 / 5, null);
			} else {
				g.drawImage(img, x + msc.hero.getWidth(), y + msc.hero.getHeight() * 3 / 5, null);
			}
			move();
			OutOfBound();
		} else {
			this.msc.bullets.remove(this);
		}
	}

	/**
	 * 子弹移动方法 void
	 */
	public void move() {
		if (this.dir == Direction.LEFT) {
			this.x -= speed;
		} else {
			this.x += speed;
		}
	}
	
	/**
	 * 当子弹划出屏幕的时候, 自动死亡
	 *  void
	 */
	public void OutOfBound() {
		if(this.dir == Direction.LEFT) {
			if(this.x <= 0) {
				this.setLive(false);
			}
		} else {
			if(this.x >= this.msc.back.getWidth()) {
				this.setLive(false);
			}
		}
	}

	/**
	 * 子弹击打一只怪物方法
	 * 
	 * @param mob
	 *            void
	 */
	public boolean hit(Mob mob) {
		if (this.live && mob.isLive() && this.getRectangle().intersects(mob.getRectangle())) {
			this.live = false;
			mob.hasHit();
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 击打一队怪物的方法
	 * @param buttlet void
	 */
	public void hit(List<Mob> mobs) {
		for(int i = 0; i < mobs.size(); i ++) {
			this.hit(mobs.get(i));
		}
	}
	
	/**
	 * 取得图片矩形
	 * @return Rectangle
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
