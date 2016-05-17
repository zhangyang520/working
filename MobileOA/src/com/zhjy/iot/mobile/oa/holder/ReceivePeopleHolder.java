package com.zhjy.iot.mobile.oa.holder;

import android.view.View;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.entity.ReceivePeople;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ReceivePeopleHolder   
 * @类描述：   文件传阅详情页的收件人管理类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-1 下午2:46:04  
 * @version    
 *
 */
public class ReceivePeopleHolder extends BaseHolder<ReceivePeople> {
	
	private View contentView;
	private TextView name;
	private TextView time;

	@Override
	public View initView() {
		contentView = UiUtils.inflate(R.layout.listview_item_shoujianren);
		name = (TextView) contentView.findViewById(R.id.shoujianren_name);
		time = (TextView) contentView.findViewById(R.id.shoudao_time);
		
		return contentView;
	}

	@Override
	public void refreshView(ReceivePeople data) {
		name.setText(data.getName());
		time.setText(data.getTime());
	}

}
