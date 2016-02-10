/**
 * 
 */
package com.qiyou.refresh.view;

/**
 * @齐游
 * @2016-2-8
 * @author 谞臣
 * @category 拖动刷新的接口
 */
public interface Pullable {
	/**
	 * 判断是否可以下拉刷新
	 * 
	 * @return
	 */
	boolean canPullDown();

	/**
	 * 判断是否可以上拉刷新
	 * 
	 * @return
	 */
	boolean canPullUp();
}
