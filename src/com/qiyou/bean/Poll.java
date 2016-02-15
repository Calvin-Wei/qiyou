/**
 * 
 */
package com.qiyou.bean;

/**
 * @齐游
 * @2016-2-15
 * @author 谞臣
 * 
 */
public class Poll {
	public String optionName; // 当前选项
	public int currentNUm; // 当前选项投票数
	public double optionPercent;// 当前投票选项百分比
	public static boolean ischecked = false; // 投票按钮是否点击
	public int image; // 选项中的图片

	public Poll() {
		super();
	}

	public Poll(String optionName, int currentNUm, double optionPercent,
			boolean ischecked, int image) {
		super();
		this.optionName = optionName;
		this.currentNUm = currentNUm;
		this.optionPercent = optionPercent;
		this.ischecked = ischecked;
		this.image = image;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public int getCurrentNUm() {
		return currentNUm;
	}

	public void setCurrentNUm(int currentNUm) {
		this.currentNUm = currentNUm;
	}

	public double getOptionPercent() {
		return optionPercent;
	}

	public void setOptionPercent(double optionPercent) {
		this.optionPercent = optionPercent;
	}

	public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
}
