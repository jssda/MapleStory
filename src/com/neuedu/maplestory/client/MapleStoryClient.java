/**
 * 客户端文件
 */
package com.neuedu.maplestory.client;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.neuedu.maplestory.entity.BackGround;
import com.neuedu.maplestory.entity.Hero;
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

	//加载英雄
	Hero hero = new Hero();
	
	//加载地图
	BackGround back = new BackGround();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		back.draw(g);
		hero.draw(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neuedu.maplestory.util.FrameUtil#loadFrame() 
	 * 重载父类loadFrame方法,
	 * 监听人物移动状态
	 */
	@Override
	public void loadFrame() {
		super.loadFrame();

		this.addKeyListener(new KeyAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				hero.keyPressed(e);
			}
			
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				hero.keyReleased(e);
			}
		});

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
