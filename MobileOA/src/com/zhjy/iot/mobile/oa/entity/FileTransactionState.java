package com.zhjy.iot.mobile.oa.entity;

/**
 * 
 * @项目名：MobileOA
 * @类名称：文件传阅的状态
 * @类描述：文件传阅的状态： 文件传阅类别:1.已收普通文件2.已收重要文件3.已发文件
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-6下午12:11:53
 * @version
 * 
 */
public enum FileTransactionState {
	ORDINARY(1), INPORTANT(2), YIFA(3);
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	FileTransactionState(int value) {
		this.value = value;
	}
}
