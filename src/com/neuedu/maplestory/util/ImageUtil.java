/**
 * 
 */
package com.neuedu.maplestory.util;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jssd
 * 加载游戏图片资源工具类
 */
public class ImageUtil {

	//存贮图片数据结构 哈希算法效率高
	public static Map<String, Image> imgs = new HashMap<>();
	
	//静态加载图片资源
	static {
		
		//背景图片
		imgs.put("bg", GameUtil.getImage("com/neuedu/maplestory/img/bg/雪地堡垒2.png"));
		
		//人物右站立图片
		for(int i = 0; i < 4; i ++) {
			imgs.put("hero_stand_right1_" + i, GameUtil.getImage("com/neuedu/maplestory/img/hero/stand_r/stand1_" + i + ".png"));
		}
		
		//人物左站立图片
		for(int i = 0; i < 4; i ++) {
			imgs.put("hero_stand_left1_" + i, GameUtil.getImage("com/neuedu/maplestory/img/hero/stand_l/stand1_" + i + ".png"));
		}
		
		//人物右移动图片
		for(int i = 0; i < 5; i ++) {
			imgs.put("hero_walk_right1_" + i, GameUtil.getImage("com/neuedu/maplestory/img/hero/walk_r/walk1_" + i + ".png"));
		}
		
		//人物左移动图片
		for(int i = 0; i < 5; i ++) {
			imgs.put("hero_walk_left1_" + i, GameUtil.getImage("com/neuedu/maplestory/img/hero/walk_l/walk1_" + i + ".png"));
		}
		
		//向左跳动图片
		imgs.put("hero_jump_left", GameUtil.getImage("com/neuedu/maplestory/img/hero/jump/jump_l.png"));
		
		//向右跳动图片
		imgs.put("hero_jump_right", GameUtil.getImage("com/neuedu/maplestory/img/hero/jump/jump_r.png"));
		
		//向左射击
		for(int i = 0; i < 4; i ++) {
			imgs.put("hero_shoot_left1_" + i, GameUtil.getImage("com/neuedu/maplestory/img/hero/shoot_l/shoot1_" + i + ".png"));
		}
		
		//向右射击
		for(int i = 0; i < 4; i ++) {
			imgs.put("hero_shoot_right1_" + i, GameUtil.getImage("com/neuedu/maplestory/img/hero/shoot_r/shoot1_" + i + ".png"));
		}
		
	}
	
	/**
	 * 取得图片资源方法
	 * @param key
	 * @return Image
	 */
	public static Image getImage(String key) {
		return imgs.get(key);
	}
}
