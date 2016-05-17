package com.zhjy.iot.mobile.oa.holder;

import android.view.View;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.OperationProcess;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：OperationProcessHolder   
 * @类描述：   查看办理流程信息Holder管理类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-5 上午11:24:59  
 * @version    
 *
 */
public class OperationProcessHolder extends BaseHolder<OperationProcess> {

	private View convertView;
	private TextView nodeName;
	private TextView handleStage;
	private TextView name;
	private TextView completion;
	private TextView receiptTime;

	@Override
	public View initView() {
		convertView = UiUtils.inflate(R.layout.listview_item_operation_process);
		
		nodeName = (TextView) convertView.findViewById(R.id.nodeName);
		handleStage = (TextView) convertView.findViewById(R.id.handleStage);
		name = (TextView) convertView.findViewById(R.id.name);
		completion = (TextView) convertView.findViewById(R.id.completion);
		receiptTime = (TextView) convertView.findViewById(R.id.receiptTime);
		
		return convertView;
	}

	@Override
	public void refreshView(OperationProcess data) {
		StringBuilder sbDeptName=new StringBuilder();
		StringBuilder sbNodeName=new StringBuilder();
		for(Node node:data.getNodes()){
			if(sbDeptName.indexOf(node.getParent().getNodeName())==-1){
				sbDeptName.append(node.getParent().getNodeName()).append(",");
			}
			
			if(sbNodeName.indexOf(node.getNodeName())==-1){
				sbNodeName.append(node.getNodeName()).append(",");
			}
		}
		sbDeptName.deleteCharAt(sbDeptName.lastIndexOf(","));
		sbNodeName.deleteCharAt(sbNodeName.lastIndexOf(","));
		nodeName.setText(sbDeptName.toString());
		handleStage.setText(data.getHandleStage());
		name.setText(sbNodeName.toString());
		if(data.getCompletion().trim().equals(MobileOAConstant.CONSTANT_NULL_SOAP_STRING)){
			completion.setText("暂无");
		}else{
			completion.setText(DateUtil.dateFormat(DateUtil.parse2Date(data.getCompletion())));
		}
		
		if(data.getReceiptTime().trim().equals(MobileOAConstant.CONSTANT_NULL_SOAP_STRING)){
			receiptTime.setText("暂无");
		}else{
			receiptTime.setText(DateUtil.dateFormat(DateUtil.parse2Date(data.getReceiptTime())));
		}
	}

}
