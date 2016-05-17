package com.zhjy.iot.mobile.oa.entity;

import com.lidroid.xutils.db.annotation.Column;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-19上午9:49:49
 * 
 * @描述  系统设置表
 */
public class SystemSetting extends EntityBase{
 
	@Column(column="isAutoLogin",defaultValue="false")
	private boolean isAutoLogin;//是否自动登录

	public boolean isAutoLogin() {
		return isAutoLogin;
	}

	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
	
	
}
