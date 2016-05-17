package com.zhjy.iot.mobile.oa.entity;
/**
 * 
 * @项目名：MobileOA
 * @类名称：行政收文的状态
 * @类描述：行政收文的状态常量的定义
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-6上午10:47:21
 * @version
 * 
 */
public enum ReceiveFileState {
	YIBAN(1),WEIBAN(2),DAIYUE(3),YIYUE(4);
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	ReceiveFileState(int value){
		this.value=value;
	}
}
