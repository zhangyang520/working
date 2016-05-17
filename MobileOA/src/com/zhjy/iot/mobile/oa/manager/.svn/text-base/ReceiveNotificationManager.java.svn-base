package com.zhjy.iot.mobile.oa.manager;

import java.util.HashMap;
import java.util.Map;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.ReceiveNotificationYiYueLoadingPager;
import com.zhjy.iot.mobile.oa.view.ReceiveNotificationDaiYueLoadingPager;

/**
 * @项目名：MobileOA 
 * @类名称：ReceiveNotificationManager   
 * @类描述：   公文通知的管理页
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-20 下午2:58:41  
 */
public class ReceiveNotificationManager {

	private static Map<Integer,LoadingPager> mReceiveNotificationManager = new HashMap<Integer, LoadingPager>();
	private ReceiveNotificationManager(){
		
	}
	
	
	public static LoadingPager getLoadingPager(int i){
		
		if(mReceiveNotificationManager.get(i)==null){
			synchronized (ReceiveNotificationManager.class) {
				if(mReceiveNotificationManager.get(i)==null){
					switch(i){
					case 0:
						mReceiveNotificationManager.put(i, new ReceiveNotificationDaiYueLoadingPager(UiUtils.getContext()));
						break;
					case 1:
						mReceiveNotificationManager.put(i, new ReceiveNotificationYiYueLoadingPager(UiUtils.getContext()));
						break;
					}
				}
			}
		}else{
			return mReceiveNotificationManager.get(i);
		}
		return mReceiveNotificationManager.get(i);
	}
	
}
