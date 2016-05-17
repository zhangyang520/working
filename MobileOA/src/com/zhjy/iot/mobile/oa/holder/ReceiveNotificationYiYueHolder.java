package com.zhjy.iot.mobile.oa.holder;

import android.view.View;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ReceiveNotificationYiYueHolder   
 * @类描述：   公文通知已阅的holder管理类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-22 下午2:53:18  
 * @version    
 *
 */
public class ReceiveNotificationYiYueHolder extends BaseHolder<PolicyReceiveFile> {

	private View convertView;
	private TextView tvTitle;
	private TextView tvToUnit;
	private TextView displayTime;

	@Override
	public View initView() {
		convertView = UiUtils.inflate(R.layout.listview_item_policy_receive);
		tvTitle = (TextView) convertView.findViewById(R.id.policy_receive_title);
		tvToUnit = (TextView) convertView.findViewById(R.id.from_danwei);
		displayTime = (TextView) convertView.findViewById(R.id.tv_time);
		if (convertView.getTag() == null) {
			convertView.setTag(this);
		}
		return convertView;
	}

	@Override
	public void refreshView(PolicyReceiveFile data) {
		tvTitle.setText(data.getTitle());
		tvToUnit.setText(data.getToUnit());
		displayTime.setText(data.getReceFileTime());
	}

}
