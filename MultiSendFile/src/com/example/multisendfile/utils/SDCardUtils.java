package com.example.multisendfile.utils;

import android.os.Environment;

/**
 * sdCard�Ĺ�����
 * @author zhangyang
 *
 */
public class SDCardUtils {
	
	private static SDCardUtils instance=new SDCardUtils();
	private String zipDir;
	private String rootPath;
	
	/*
	 * ˽�л����캯��
	 */
	private SDCardUtils(){
		//���г�ʼ������
		rootPath=getRootPath();
	}
	
	/**
	 * ��ȡʵ��
	 * @return
	 */
	public static SDCardUtils getInstance(){
		return instance;
	}
	
	
	/**
	 * ���л�ȡ��·��
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
