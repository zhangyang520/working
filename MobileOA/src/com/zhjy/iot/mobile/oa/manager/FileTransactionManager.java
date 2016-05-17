package com.zhjy.iot.mobile.oa.manager;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.fragment.FileTransactionFragment;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.FileTransImportantLoadingPager;
import com.zhjy.iot.mobile.oa.view.FileTransSendFileLoadingPage;
import com.zhjy.iot.mobile.oa.view.FileTransYifaLoadingPager;
import com.zhjy.iot.mobile.oa.view.FileTransYishouLoadingPager;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月18日上午9:18:47
 * 
 * @描述  行政收文的管理页
 */
public class FileTransactionManager {
	
	private static Activity activity;
	private static FileTransactionFragment fileTransactionFragment;
	
	

	
	public static void setActivity(Activity activity) {
		FileTransactionManager.activity = activity;
	}


	public static void setFileTransactionFragment(
			FileTransactionFragment fileTransactionFragment) {
		FileTransactionManager.fileTransactionFragment = fileTransactionFragment;
	}


	private static Map<Integer,LoadingPager> fileTransactionPagerManager=new HashMap<Integer, LoadingPager>();
	
	
	public static LoadingPager getLoadingPager(int i){
		
		if(fileTransactionPagerManager.get(i)==null){
			synchronized (FileTransactionManager.class) {
				if(fileTransactionPagerManager.get(i)==null){
					switch(i){
					case 0:
						fileTransactionPagerManager.put(i, new FileTransYishouLoadingPager(UiUtils.getContext()));
						break;
					case 1:
						fileTransactionPagerManager.put(i, new FileTransYifaLoadingPager(UiUtils.getContext()));
						break;
					case 2:
						fileTransactionPagerManager.put(i, new FileTransImportantLoadingPager(UiUtils.getContext()));
						break;
					case 3:
						FileTransSendFileLoadingPage ftsflp = new FileTransSendFileLoadingPage(UiUtils.getContext());
						ftsflp.setActivity(activity);
						fileTransactionPagerManager.put(i, ftsflp);
						break;
					}
				}
			}
			
		}else{
			if(i==3){
				((FileTransSendFileLoadingPage)fileTransactionPagerManager.get(i)).setActivity(activity);
				((FileTransSendFileLoadingPage)fileTransactionPagerManager.get(i)).setFileTransactionFragment(fileTransactionFragment);
			}
			return fileTransactionPagerManager.get(i);
		}
		return fileTransactionPagerManager.get(i);
	}
}
