/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import com.neuedu.maplestory.constant.Constant;
import com.neuedu.maplestory.util.ImageUtil;

/**
 * @author jssd 英雄实体类, 封装了移动, 攻击, 初始位置等方法
 */
public class Hero {
	public static Image actionImgs[] = new Image[100];

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

	public int x; // 位置x坐标
	public int y; // 位置y坐标
	public Image img; // 英雄图片
	public boolean left, right, walk, jump, shoot; // 英雄移动方向, 是否正在移动
	public double speed; // 英雄移动速度
	public Direction dir; // 面向的方向
	public Action action; // 英雄的行动状态

	/**
	 * 默认构造, 位置在x = 200px, y = 200px
	 */
	public Hero() {
		this.x = 200;
		this.y = 200;
		this.dir = Direction.RIGHT;
		this.action = Action.STAND;
		this.speed = 10;
		this.img = ImageUtil.getImage("hero_stand_right1_0");
	}

	/**
	 * 重载构造, 添加初始话位置选项
	 * 
	 * @param x
	 * @param y
	 */
	public Hero(int x, int y) {
		this();
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
	public Hero(int x, int y, double speed) {
		this(x, y);
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
//		if (shoot) {
//			this.action = Action.SHOOT;
//		}
		if (jump) {
			this.action = Action.JUMP;
			this.jump();
		}
		if (shoot) {
			this.action = Action.SHOOT;
		}
		if(!walk && !jump && !shoot) {
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
	private double g = 0.8;
	private double v0 = 10;
	private double vt = 0;
	private double deltaHight = 0;
	private boolean jump_up = true;
	int i = 0;
	public void jump() {
		
		if (jump_up) { // 假如人物做垂直上抛运动
			vt = v0 - g * t;
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
			vt = v0 + g * t;
			v0 = vt;
			deltaHight = v0 * t;
			y += deltaHight;
			if (y >= 200) {
				y = 200;
				v0 = 10;
				vt = 0;
				deltaHight = 0;
				jump_up = true;
				jump = false;
				this.action = Action.STAND;
			}
		}
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
		case KeyEvent.VK_K:
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
		case KeyEvent.VK_J:
			jump = true;
			break;
		case KeyEvent.VK_K:
			shoot = true;
			break;
		case KeyEvent.VK_3:
			System.out.println(i);
			
		default:
			break;
		}
	}
}
