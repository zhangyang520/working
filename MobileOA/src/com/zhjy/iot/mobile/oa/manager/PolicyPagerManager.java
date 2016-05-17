package com.zhjy.iot.mobile.oa.manager;

import java.util.HashMap;
import java.util.Map;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.PolicyReceiveWeibanLoadingPager;
import com.zhjy.iot.mobile.oa.view.PolicyReceiveYibanLoadingPager;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月18日上午9:18:47
 * 
 * @描述  行政收文的管理页
 */
public class PolicyPagerManager{

	private static Map<Integer,LoadingPager> policyPagerManager=new HashMap<Integer, LoadingPager>();
	private PolicyPagerManager(){
		
	}
	
	public static LoadingPager getLoadingPager(int i){
		
		if(policyPagerManager.get(i)==null){
			synchronized (PolicyPagerManager.class) {
				if(policyPagerManager.get(i)==null){
					switch(i){
						case 1:
							policyPagerManager.put(i, new PolicyReceiveYibanLoadingPager(UiUtils.getContext()));
							break;
						case 0:
							policyPagerManager.put(i, new PolicyReceiveWeibanLoadingPager(UiUtils.getContext()));
							break;
					}
				}
			}
			
		}else{
			return policyPagerManager.get(i);
		}
		return policyPagerManager.get(i);
	}
	
	/**
	 * 进行初始化行政收文pager的网络时间
	 */
	public static void initPolicyPagerNetowrkTime(){
		//进行循环遍历policyPagerManager设置初始化NetworkTime
		for(Map.Entry<Integer,LoadingPager> entity:policyPagerManager.entrySet()){
			entity.getValue().setNetworkTime(0);
		}
	}
}
