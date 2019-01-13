/**
 * 客户端文件
 */
package com.neuedu.maplestory.client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.neuedu.maplestory.constant.Constant;
import com.neuedu.maplestory.util.GameUtil;

/**
 * 冒险岛项目客户端
 * 
 * @author jssd
 *
 */
public class MapleStoryClient extends Frame {

	private static final long serialVersionUID = -4267814975591934779L;
	Image hero = GameUtil.getImage("com/neuedu/maplestory/img/hero/stand_l/stand1_0.png");

	/**
	 * 加载窗口方法 void 1. 设置窗口大小 2. 设置窗口位置 3. 设置窗口可见性
	 */
	public void loadFrame() {
		// 设置大小
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		// 设置窗口位置
		// this.setLocation(0, 0);
		// 相对屏幕居中
		this.setLocationRelativeTo(null);
		// 设置窗口可见性
		this.setVisible(true);
		// 添加屏幕关闭监听器
		this.addWindowListener(new WindowAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		// 设置窗口标题
		this.setTitle("东软夏令营-冒险岛");
		// 设置窗口背景颜色
		// this.setBackground(new Color(69,69,69));
		new MyThread().start();
	}

	int width = 200;
	int height = 200;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(hero, width, height, null);
		width += 2;
	}

	class MyThread extends Thread {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (;;) {
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// 解决图片闪烁的问题，用双缓冲方法解决闪烁问题
	Image backImg = null;

	// 重写update()方法，在窗口的里层添加一个虚拟的图片
	@Override
	public void update(Graphics g) {
		if (backImg == null) {
			// 如果虚拟图片不存在，创建一个和窗口一样大小的图片
			backImg = createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		}
		// 获取到虚拟图片的画笔
		Graphics backg = backImg.getGraphics();
		Color c = backg.getColor();
		backg.setColor(Color.white);
		backg.fillRect(0, 0, Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		backg.setColor(c);
		paint(backg);
		g.drawImage(backImg, 0, 0, null);
	}

	/**
	 * 主方法
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		new MapleStoryClient().loadFrame();

	}

}
