/**
 * 
 */
package com.neuedu.maplestory.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import com.neuedu.maplestory.client.MapleStoryClient;
import com.neuedu.maplestory.constant.Constant;
import com.neuedu.maplestory.util.ImageUtil;
import com.neuedu.maplestory.util.MusicUtil;

/**
 * @author jssd 英雄实体类, 封装了移动, 攻击, 初始位置等方法
 */
public class Hero extends Life {
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

	private boolean left, right, walk, jump, shoot; // 英雄移动方向, 是否正在移动
	private Direction dir; // 面向的方向
	private Action action; // 英雄的行动状态
	private int money; // 金币
	public int score; // 得分
	private MapleStoryClient msc; // 客户端

	/**
	 * 默认构造, 位置在x = 200px, y = 地面高度
	 */
	public Hero(MapleStoryClient msc) {
		this.msc = msc;
		this.width = actionImgs[0].getWidth(null);
		this.height = actionImgs[0].getHeight(null);
		this.dir = Direction.RIGHT;
		this.action = Action.STAND;
		this.speed = 10;
		this.x = 200;
		this.y = msc.back.getFloor() - this.height;
		this.HP = 5;
		this.live = true;
		this.money = 0;
		this.score = 0;
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
	public Hero(MapleStoryClient msc, int x, int y, int speed) {
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
		for (int i = 0; i < this.HP; i++) {
			g.drawImage(ImageUtil.getImage("red_heart"), 80 * (i + 1), 100, null);
		}

		Color c = g.getColor();
		Font f = g.getFont();
		g.setFont(new Font("微软雅黑", Font.BOLD, 40));
		g.setColor(Color.white);
		g.drawString("金币: " + this.money, 1000, 100);
		g.drawString("积分: " + this.score, 1000, 150);
		g.setColor(c);
		g.setFont(f);

		if (this.live) {
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
		} else {
			c = g.getColor();
			f = g.getFont();
			g.setFont(new Font("微软雅黑", Font.BOLD, 80));
			g.setColor(Color.red);
			g.drawString("GAME OVER!", 600, 500);
			g.setColor(c);
			g.setFont(f);
		}
	}

	/**
	 * 人物移动 void
	 */
	int count_speed_acc = 0; // 加速计时器
	boolean bool_speed_acc = false; // 是否加速

	public void move() {
		// 加速判断
		if (bool_speed_acc) {
			count_speed_acc++;
			if (count_speed_acc == 100) {
				count_speed_acc = 0;
				bool_speed_acc = false;
				this.speed -= 15;
			}
		}
		hasHit(msc.mobs); // 被怪物击中
		pickupDrop(msc.drops); // 捡起道具
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
		this.shoot = false;
		this.outOfBound();
	}

	/**
	 * 人物移动越界处理 void
	 */
	public void outOfBound() {
		if (x < 350 && msc.back.x < 0) {
			msc.back.x += this.speed;
			x = 350;
//			for(int i = 0; i < msc.mobs.size(); i ++) {
//				msc.mobs.get(i).speed -= this.speed;
//			}
		} else if (x < 10){
			x = 10;
//			for(int i = 0; i < msc.mobs.size(); i ++) {
//				msc.mobs.get(i).speed += this.speed;
//			}
		}else if (x > Constant.GAME_WIDTH - this.width - 350 && Math.abs(this.msc.back.x) <= msc.back.width - Constant.GAME_WIDTH) {
			x = Constant.GAME_WIDTH - this.width - 350;
			this.msc.back.x -= this.speed;
//			for(int i = 0; i < msc.mobs.size(); i ++) {
//				msc.mobs.get(i).speed += this.speed;
//			}
		} else if(x > Constant.GAME_WIDTH - this.width) {
			x = Constant.GAME_WIDTH - this.width;
//			for(int i = 0; i < msc.mobs.size(); i ++) {
//				msc.mobs.get(i).speed -= this.speed;
//			}
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
			if (v0 == 20) {
				new MusicUtil("com/neuedu/maplestory/music/jump.mp3").start();
			}
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
		new MusicUtil("com/neuedu/maplestory/music/hit.mp3").start();
		this.msc.bullets.add(new Bullet(this.msc, x, y, dir));
	}

	/**
	 * 英雄被击中
	 */
	private int count_hasHit = 0;
	private boolean bool_hasHit;

	public void hasHit(List<Mob> mobs) {
		count_hasHit++;
		if (count_hasHit == 20) {
			bool_hasHit = false;
		}
		for (int i = 0; i < mobs.size(); i++) {
			if (!bool_hasHit && mobs.get(i).live && this.getRectangle().intersects(mobs.get(i).getRectangle())) {
				this.HP--;
				bool_hasHit = true;
				count_hasHit = 0;
				if (this.HP == 0) {
					this.live = false;
					break;
				}
			}
		}
	}

	/**
	 * 凋落物拾取 void
	 */
	public void pickupDrop(List<Drop> drops) {
		for (int i = 0; i < drops.size(); i++) {
			if (this.getRectangle().intersects(drops.get(i).getRectangle())) {
				if (drops.get(i).type == 1) {
					this.money++;
				} else if (drops.get(i).type == 2) {
					this.HP++;
				} else if (drops.get(i).type == 3) {
					this.speed += 15;
					bool_speed_acc = true;
				}
				drops.remove(i);
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
			if (!right)
				walk = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			if (!left)
				walk = false;
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
	 * 射击单次按键
	 * 
	 * @param e
	 *            void
	 */
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_J:
			shoot = true;
			System.out.println("按下射击按键");
			break;
		case KeyEvent.VK_O:
			System.out.println("按下o键");
			break;

		default:
			System.out.println("maieyou");
			break;
		}
	}

	/**
	 * 获得图片矩形
	 * 
	 * @return Rectangle
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x + (this.width / 6), y + (this.height / 6), this.width * 2 / 3, this.height * 2 / 3);
	}

	/**
	 * 取得图像的高
	 * 
	 * @return int
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 取得图像的宽
	 * 
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
