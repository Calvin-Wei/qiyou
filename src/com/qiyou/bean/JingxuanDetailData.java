/**
 * 
 */
package com.qiyou.bean;

import java.io.Serializable;

/**
 * @齐游
 * @2016-2-6
 * @author 谞臣
 * 精选数据
 */
public class JingxuanDetailData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8080314598740979224L;

	private String image;
	private String poi;
	private String text;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPoi() {
		return poi;
	}

	public void setPoi(String poi) {
		this.poi = poi;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
