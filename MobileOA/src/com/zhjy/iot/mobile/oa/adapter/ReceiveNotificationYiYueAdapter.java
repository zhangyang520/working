package com.zhjy.iot.mobile.oa.adapter;

import java.util.List;

import android.widget.ListView;

import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.Inner.DefaultAdapter;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.holder.ReceiveNotificationYiYueHolder;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ReceiveNotificationYiBanAdapter   
 * @类描述：   公文通知的已办
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-20 下午3:47:45  
 * @version    
 *
 */
public class ReceiveNotificationYiYueAdapter extends DefaultAdapter<PolicyReceiveFile> {

	public ReceiveNotificationYiYueAdapter(
			List<PolicyReceiveFile> datas, ListView lv) {
		super(datas, lv);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseHolder<PolicyReceiveFile> getHolder() {
		return new ReceiveNotificationYiYueHolder();
	}

	@Override
	protected void processDatasList() {
		// TODO Auto-generated method stub
		
	}

}
