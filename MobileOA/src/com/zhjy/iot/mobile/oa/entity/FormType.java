package com.zhjy.iot.mobile.oa.entity;

/**
 * 
 * @项目名：MobileOA
 * @类名称：FormType
 * @类描述：表单类型
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-21 下午7:10:18
 * @version 1，1
 * 
 */
public enum FormType {
	CommonForm(1), WJPBDAN(2), QSPBDAN(3), QLDQSPBDAN(4);
	private int value;

	FormType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
