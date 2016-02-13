/**
 * 
 */
package com.qiyou.bean;

/**
 * @齐游
 * @2016-2-14
 * @author 谞臣
 * 
 */
public class WuLiuData {
	private String maddress;
	private String mtime;

	public WuLiuData(String maddress, String mtime) {
		super();
		this.maddress = maddress;
		this.mtime = mtime;
	}

	public String getMaddress() {
		return maddress;
	}

	public void setMaddress(String maddress) {
		this.maddress = maddress;
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
}
