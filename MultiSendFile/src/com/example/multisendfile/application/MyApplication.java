package com.example.multisendfile.application;

import android.app.Application;
/**
 * @��Ŀ����
 * @�����ƣ�
 * @��������MyApplication ������
 * @�����ˣ�zhangyang
 * @�޸��ˣ�
 * @����ʱ�䣺2016-5-5 ����8:52:30
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
