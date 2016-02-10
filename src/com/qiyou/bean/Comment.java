/**
 * 
 */
package com.qiyou.bean;

import java.io.Serializable;

/**
 * @齐游
 * @2016-2-4
 * @author 谞臣
 * @category 评论的实体类
 */
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7339011257882000948L;
	private String content;
	private UserInfo userInfo;
	private boolean isLike;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}
}
