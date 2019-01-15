/**
 * 客户端文件
 */
package com.neuedu.maplestory.client;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.neuedu.maplestory.entity.BackGround;
import com.neuedu.maplestory.entity.Bullet;
import com.neuedu.maplestory.entity.Hero;
import com.neuedu.maplestory.entity.Mob;
import com.neuedu.maplestory.util.FrameUtil;

/**
 * 冒险岛项目客户端
 * 
 * @author jssd
 *
 */
public class MapleStoryClient extends FrameUtil {

	/**
	 * 加载地图背景
	 */
	private static final long serialVersionUID = 1L;

	// 加载地图
	public BackGround back = new BackGround();

	// 加载英雄
	public Hero hero = new Hero(this);

	// 子弹加载
	public List<Bullet> bullets = new ArrayList<>();

	// 怪物加载
	public List<Mob> mobs = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		
		back.draw(g); // 画背景
		hero.draw(g); // 画英雄
		
//		/**
//		 * 调试数据
//		 */
//		Color c = g.getColor();
//		Font f = g.getFont();
//		g.setFont(new Font("微软雅黑", Font.BOLD, 40));
//		g.setColor(Color.white);
//		g.drawString("子弹容器所有数据" + bullets.size(), 300, 400);
//		g.setColor(c);
//		g.setFont(f);
		
		// 画子弹
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).hit(mobs);
			bullets.get(i).draw(g);
		}
		// 画怪物
		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).draw(g);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neuedu.maplestory.util.FrameUtil#loadFrame() 重载父类loadFrame方法,
	 * 监听人物移动状态
	 */
	@Override
	public void loadFrame() {
		super.loadFrame();

		this.addKeyListener(new KeyAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				hero.keyPressed(e);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				hero.keyReleased(e);
			}
		});

		for (int i = 0; i < 5; i++) {
			mobs.add(new Mob(this, 400 + 130 * i));
		}
	}

	/**
	 * 主方法, 加载窗口
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new MapleStoryClient().loadFrame();
	}

}
