package com.example.multisendfile.application;

import android.app.Application;
/**
 * @项目名：
 * @类名称：
 * @类描述：MyApplication 常量类
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-5-5 下午8:52:30
 * @version
 */
public class MyApplication extends Application{
	
	private static MyApplication application;
	@Override
	public void onCreate() {
		super.onCreate();
		application=this;
	}
	
	
	public static MyApplication getApplication() {
		return application;
	}
	
	
	public static void setApplication(MyApplication application) {
		MyApplication.application = application;
	}
	
	
}
