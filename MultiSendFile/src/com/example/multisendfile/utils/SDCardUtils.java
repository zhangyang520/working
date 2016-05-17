package com.example.multisendfile.utils;

import android.os.Environment;

/**
 * sdCard的工具类
 * @author zhangyang
 *
 */
public class SDCardUtils {
	
	private static SDCardUtils instance=new SDCardUtils();
	private String zipDir;
	private String rootPath;
	
	/*
	 * 私有化构造函数
	 */
	private SDCardUtils(){
		//进行初始化操作
		rootPath=getRootPath();
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static SDCardUtils getInstance(){
		return instance;
	}
	
	
	/**
	 * 进行获取根路径
	 * @return
	 */
	public String getRootPath(){
		String rootPath;
		if(Environment.getExternalStorageState()==Environment.MEDIA_MOUNTED){
			rootPath=Environment.getExternalStorageDirectory().getAbsolutePath();
		}else{
			rootPath=UiUtils.getContext().getFilesDir().getAbsolutePath();
		}
		return rootPath;
	}
}
