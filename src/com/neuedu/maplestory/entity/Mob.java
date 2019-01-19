/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.neuedu.maplestory.client.MapleStoryClient;
import com.neuedu.maplestory.constant.Constant;
import com.neuedu.maplestory.util.ImageUtil;
import com.neuedu.maplestory.util.MusicUtil;

/**
 * @author jssd 怪物类
 */
public class Mob extends Life{

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

	MapleStoryClient msc; // 客户端引用
	private BloodBar bloodBar; // 怪物HP
	private Direction dir; // 怪物的反向
	private Action action; // 怪物的动作

	public Mob(MapleStoryClient msc) {
		this.msc = msc;
		this.height = mobsActionImg[0].getHeight(null);
		this.width = mobsActionImg[0].getWidth(null);
		this.x = 600;
		this.y = msc.back.getFloor() - this.height;
		this.HP = Constant.MOB_HP;
		this.dir = Direction.LEFT;
		this.action = Action.WALK;
		this.speed = 12;
		bloodBar = new BloodBar(this, 5, this.width);
		this.live = true;
	}

	public Mob(MapleStoryClient msc, int x) {
		this(msc);
		this.x = x;
	}

	/**
	 * 画出怪物
	 * 
	 * @param g
	 *            void
	 */

	// 图片轮播计数器
	private int count_stand_left = 0;
	private int count_walk_left = 6;
	private int count_die_left = 12;

	/**
	 * 画动物的方法
	 * 
	 * @param g
	 *            void
	 */
	public void draw(Graphics g) {
		bloodBar.draw(g);
		switch (dir) {
		case LEFT:
			if (this.isLive()) {
				switch (action) {
				case STAND:
					count_stand_left++;
					if (count_stand_left >= 6) {
						count_stand_left = 0;
					}
					g.drawImage(mobsActionImg[count_stand_left], x, y, null);
					break;
				case WALK:
					count_walk_left++;
					if (count_walk_left >= 12) {
						count_walk_left = 6;
					}
					g.drawImage(mobsActionImg[count_walk_left], x, y, null);
					break;
				default:
					break;
				}
			} else {
				count_die_left++;
				if (count_die_left < 25) {
					g.drawImage(mobsActionImg[count_die_left], x, y, null);
				} else {
					this.msc.mobs.remove(this);
				}
			}
			break;
		case RIGHT:
			if (this.isLive()) {
				switch (action) {
				case STAND:
					count_stand_left++;
					if (count_stand_left >= 6) {
						count_stand_left = 0;
					}
					g.drawImage(ImageUtil.flipHorizontalJ2D((BufferedImage) mobsActionImg[count_stand_left]), x, y,
							null);
					break;
				case WALK:
					count_walk_left++;
					if (count_walk_left >= 12) {
						count_walk_left = 6;
					}
					g.drawImage(ImageUtil.flipHorizontalJ2D((BufferedImage) mobsActionImg[count_walk_left]), x, y,
							null);
					break;
				default:
					break;
				}
			} else {
				count_die_left++;
				if (count_die_left < 25) {
					g.drawImage(ImageUtil.flipHorizontalJ2D((BufferedImage) mobsActionImg[count_die_left]), x, y, null);
				} else {
					this.msc.mobs.remove(this);
				}
			}
			break;
		default:
			break;
		}
		move();
	}

	/**
	 * 怪物移动的方法 void
	 */
	public void move() {
		if (this.x <= msc.back.x && this.dir == Direction.LEFT) {
			this.setLive(false);
		}
		if (this.x >= Constant.GAME_WIDTH - this.width && this.dir == Direction.RIGHT) {
			this.speed = -speed;
			this.dir = Direction.LEFT;
		}

		this.x -= this.speed;
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
		new MusicUtil("com/neuedu/maplestory/music/hashit.mp3").start();
		this.HP -= 10;
		if (this.HP <= 0) {
			this.setLive(false);
			double temp = new Random().nextDouble();
			int type = 0;
			if(temp >= 0 && temp < 0.5) {
				type = 0;
			} else if( temp >= 0.5 && temp < 0.8) {
				type = 1;			} else if (temp >= 0.8 && temp < 0.9) {
				type = 2;
			} else if(temp >= 0.9 && temp < 1) {
				type = 3;
			}
			if(type > 0 && type < 4) {
				this.msc.drops.add(new Drop(this.x, this.y, type));
			}
			msc.hero.score += 10;
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
