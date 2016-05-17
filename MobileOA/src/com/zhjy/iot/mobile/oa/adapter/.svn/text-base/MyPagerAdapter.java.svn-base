package com.zhjy.iot.mobile.oa.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager.LoadResult;
import com.zhjy.iot.mobile.oa.manager.FileTransactionManager;
import com.zhjy.iot.mobile.oa.manager.PolicyPagerManager;
import com.zhjy.iot.mobile.oa.manager.ReceiveNotificationManager;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.view.FileTransSendFileLoadingPage;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-18上午11:39:16
 * 
 * @描述 viewPager的适配器
 */
public class MyPagerAdapter extends PagerAdapter{

    private ViewPagerType viewPagerType;
    
    
	public MyPagerAdapter(ViewPagerType viewPagerType) {
		super();
		this.viewPagerType = viewPagerType;
	}

	@Override
	public int getCount(){
		int count =0;
		switch(viewPagerType){
			case PolicyReceive:
				count=MobileOAConstant.POLICY_PAGER_COUNT;
				break;
			case ReceiveNotification:
				count = MobileOAConstant.RECEIVE_NOTIFICATION_COUNT;
				break;
				
			case FileTransaction:
				count=MobileOAConstant.FILETRANSACTION_PAGER_COUNT;
				break;
			case Mine:
				
				break;
		}
		return count;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position){
		LoadingPager loadPager=null;
		switch (viewPagerType) {
			case PolicyReceive:
				System.out.println("instantiateItem 行政收文...position:"+position);
				loadPager=PolicyPagerManager.getLoadingPager(position);
				break;
			case ReceiveNotification:
				System.out.println("instantiateItem 收文通知...position:"+position);
				loadPager=ReceiveNotificationManager.getLoadingPager(position);
				break;
				
			case FileTransaction:
				System.out.println("instantiateItem 文件传阅...position:"+position);
				loadPager=FileTransactionManager.getLoadingPager(position);
				if(position==3){
					//为发送文件:
					((FileTransSendFileLoadingPage)loadPager).setState(LoadResult.success.getValue());
					((FileTransSendFileLoadingPage)loadPager).showPage();
				}
				break;
			case Mine:
				System.out.println("instantiateItem 我的...position:"+position);
				break;
		default:
			break;
		}
		
		if(loadPager.getParent()!=null){
			((ViewGroup)loadPager.getParent()).removeView(loadPager);
		}
		container.addView(loadPager);
		return loadPager;
	}
	
	/**
	 * 页面类型的定义
	 * @author zhangyang
	 *
	 */
	public enum ViewPagerType{
		PolicyReceive,ReceiveNotification,FileTransaction,Mine;
	}
}
