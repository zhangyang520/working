package com.zhjy.iot.mobile.oa.dao;

import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.entity.SystemSetting;

/**
 * @author zhangyang
 * 
 * @日期 2016-3-27下午2:51:43
 * 
 * @描述 系统设置相关的dao
 */
public class SystemSettingDao {
	private static SystemSettingDao systemSettingDao;

	private  SystemSettingDao(){
		
	}
	
	/**
	 * 描述：单例模式
	 */
	public static SystemSettingDao getInstance(){
		if (systemSettingDao == null) {
			synchronized (SystemSettingDao.class) {
				if (systemSettingDao == null) {
					systemSettingDao = new SystemSettingDao();
				}
			}
		}
		return systemSettingDao;
	}
	
	
	/**
	 * 进行获取系统自动登录的状态
	 * @return
	 */
	public boolean getSystemSettingAutoLogin(){
		try {
			List<SystemSetting> setingDatas=MobileOaApplication.dbUtils.findAll(SystemSetting.class);
			if(setingDatas.size()==0){
				return false;
			}else{
				return setingDatas.get(0).isAutoLogin();
			}
		} catch (DbException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 进行设置自动登录的状态
	 * @param flag
	 */
	public void updateSettingAutoLogin(boolean autoFlag){
		try {
			List<SystemSetting> setingDatas=MobileOaApplication.dbUtils.findAll(SystemSetting.class);
			if(setingDatas.size()==0){
				SystemSetting setting=new SystemSetting();
				setting.setAutoLogin(autoFlag);
				MobileOaApplication.dbUtils.save(setting);
			}else{
				//如果长度>0
				setingDatas.get(0).setAutoLogin(autoFlag);
				MobileOaApplication.dbUtils.update(setingDatas.get(0),"isAutoLogin");
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
