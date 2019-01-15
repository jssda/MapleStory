/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.neuedu.maplestory.client.MapleStoryClient;
import com.neuedu.maplestory.constant.Constant;
import com.neuedu.maplestory.util.ImageUtil;

/**
 * @author jssd 英雄实体类, 封装了移动, 攻击, 初始位置等方法
 */
public class Hero {
	public static Image actionImgs[] = new Image[100]; // 英雄状态图片

	static {
		// 右站立
		for (int i = 0; i < 4; i++) {
			actionImgs[i] = ImageUtil.getImage("hero_stand_right1_" + i);
		}
		// 左站立
		for (int i = 4; i < 8; i++) {
			actionImgs[i] = ImageUtil.getImage("hero_stand_left1_" + (i - 4));
		}
		// 右移动
		for (int i = 8; i < 13; i++) {
			actionImgs[i] = ImageUtil.getImage("hero_walk_right1_" + (i - 8));
		}
		// 左移动
		for (int i = 13; i < 18; i++) {
			actionImgs[i] = ImageUtil.getImage("hero_walk_left1_" + (i - 13));
		}
		// 向右跳动
		actionImgs[18] = ImageUtil.getImage("hero_jump_right");
		// 向左跳动
		actionImgs[19] = ImageUtil.getImage("hero_jump_left");
		// 向右射击
		for (int i = 20; i < 24; i++) {
			actionImgs[i] = ImageUtil.getImage("hero_shoot_right1_" + (i - 20));
		}
		// 向左射击
		for (int i = 24; i < 28; i++) {
			actionImgs[i] = ImageUtil.getImage("hero_shoot_left1_" + (i - 24));
		}

	}

	private int x; // 位置x坐标
	private int y; // 位置y坐标
	private Image img; // 英雄图片
	private int height; // 图像高度
	private int width; // 图像宽度
	private boolean left, right, walk, jump, shoot; // 英雄移动方向, 是否正在移动
	private double speed; // 英雄移动速度
	private Direction dir; // 面向的方向
	private Action action; // 英雄的行动状态
	private MapleStoryClient msc; // 客户端

	/**
	 * 默认构造, 位置在x = 200px, y = 地面高度
	 */
	public Hero(MapleStoryClient msc) {
		this.msc = msc;
		this.img = ImageUtil.getImage("hero_stand_right1_0");
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		this.dir = Direction.RIGHT;
		this.action = Action.STAND;
		this.speed = 10;
		this.x = 200;
		this.y = msc.back.getFloor() - this.height;
	}

	/**
	 * 重载构造, 添加初始话位置选项
	 * 
	 * @param x
	 * @param y
	 */
	public Hero(MapleStoryClient msc, int x, int y) {
		this(msc);
		this.x = x;
		this.y = y;
	}

	/**
	 * 重载构造函数, 提供位置与速度选项
	 * 
	 * @param x
	 * @param y
	 * @param speed
	 */
	public Hero(MapleStoryClient msc, int x, int y, double speed) {
		this(msc, x, y);
		this.speed = speed;
	}

	/**
	 * 画出人物, 监听人物移动状态
	 * 
	 * @param g
	 *            void
	 */
	private int count_stand_left = 0; // 人物左站立计数器
	private int count_stand_right = 4; // 人物右站立计数器
	private int count_walk_left = 8; // 人物左移动计数器
	private int count_walk_right = 13; // 人物有移动计数器
	private int count_shoot_left = 24; // 人物左射击计数器
	private int count_shoot_right = 20; // 人物右射击计数器

	public void draw(Graphics g) {

		// 人物移动反向及状态判断判断
		switch (dir) {
		case LEFT:
			switch (action) {
			case STAND:
				count_stand_left++;
				if (count_stand_left >= 8) {
					count_stand_left = 4;
				}
				g.drawImage(actionImgs[count_stand_left], x, y, null);
				break;
			case WALK:
				count_walk_left++;
				if (count_walk_left >= 18) {
					count_walk_left = 13;
				}
				g.drawImage(actionImgs[count_walk_left], x, y, null);
				break;
			case JUMP:
				g.drawImage(actionImgs[19], x, y, null);
				break;
			case SHOOT:
				count_shoot_left++;
				if (count_shoot_left >= 28) {
					count_shoot_left = 24;
				}
				g.drawImage(actionImgs[count_shoot_left], x, y, null);
				break;
			default:
				break;
			}
			break;
		case RIGHT:
			switch (action) {
			case STAND:
				count_stand_right++;
				if (count_stand_right >= 4) {
					count_stand_right = 0;
				}
				g.drawImage(actionImgs[count_stand_right], x, y, null);
				break;
			case WALK:
				count_walk_right++;
				if (count_walk_right >= 13) {
					count_walk_right = 8;
				}
				g.drawImage(actionImgs[count_walk_right], x, y, null);
				break;
			case JUMP:
				g.drawImage(actionImgs[18], x, y, null);
				break;
			case SHOOT:
				count_shoot_right++;
				if (count_shoot_right >= 24) {
					count_shoot_right = 20;
				}
				g.drawImage(actionImgs[count_shoot_right], x, y, null);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

		this.move();
	}

	/**
	 * 人物移动 void
	 */
	public void move() {
		if (left) {
			this.dir = Direction.LEFT;
		} else if (right) {
			this.dir = Direction.RIGHT;
		}

		if (walk) {
			action = Action.WALK;
			if (left) {
				x -= this.speed;
			}
			if (right) {
				x += this.speed;
			}
		}
		// if (shoot) {
		// this.action = Action.SHOOT;
		// }
		if (jump) {
			this.action = Action.JUMP;
			this.jump();
		}
		if (shoot) {
			this.action = Action.SHOOT;
			this.shoot();
		}
		if (!walk && !jump && !shoot) {
			this.action = Action.STAND;
		}
		this.outOfBound();
	}

	/**
	 * 人物移动越界处理 void
	 */
	public void outOfBound() {
		if (x < 0) {
			x = 5;
		}
		if (x > Constant.GAME_WIDTH - img.getWidth(null) - 5) {
			x = Constant.GAME_WIDTH - img.getWidth(null) - 5;
		}
	}

	/**
	 * 人物跳动算法 void
	 */
	private double t = 1;
	private double v0 = 20;
	private double vt = 0;
	private double deltaHight = 0;
	private boolean jump_up = true;
	int i = 0;

	public void jump() {

		if (jump_up) { // 假如人物做垂直上抛运动
			vt = v0 - Constant.G * t;
			v0 = vt;
			deltaHight = v0 * t;
			y -= deltaHight;
			i++;
			if (vt <= 0) {
				vt = 0;
				v0 = 0;
				jump_up = false;

			}
		} else {
			vt = v0 + Constant.G * t;
			v0 = vt;
			deltaHight = v0 * t;
			y += deltaHight;
			if (y >= this.msc.back.getFloor() - this.height) {
				y = this.msc.back.getFloor() - this.height;
				v0 = 20;
				vt = 0;
				deltaHight = 0;
				jump_up = true;
				jump = false;
				this.action = Action.STAND;
			}
		}
	}

	/**
	 * 人物射击方法 void
	 */
	public void shoot() {
		this.msc.bullets.add(new Bullet(this.msc, x, y, dir));
	}

	/**
	 * 人物移动状态监听-释放按键
	 * 
	 * @param e
	 *            void
	 */
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = false;
			walk = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			walk = false;
			break;
		case KeyEvent.VK_J:
			shoot = false;
			break;
		default:
			break;
		}
	}

	/**
	 * 人物移动状态监听-按下按键
	 * 
	 * @param e
	 *            void
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			left = true;
			walk = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			walk = true;
			break;
		case KeyEvent.VK_K:
			jump = true;
			break;
		case KeyEvent.VK_J:
			shoot = true;
			break;
		default:
			break;
		}
	}
	
	/**
	 * 取得图像的高
	 * @return int
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 取得图像的宽
	 * @return int
	 */
	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
