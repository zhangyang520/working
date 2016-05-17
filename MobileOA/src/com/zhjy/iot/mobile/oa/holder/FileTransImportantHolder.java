package com.zhjy.iot.mobile.oa.holder;

import java.util.LinkedHashMap;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.activity.LoadingActivity;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.fragment.FileTransactionFragment;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransImportantHolder   
 * @类描述：   文件传阅-重要文件的Holder
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-20 下午2:15:39  
 * @version    
 *
 */
public class FileTransImportantHolder extends BaseHolder<FileTransaction> {

	private View convertView;
	private TextView name;
	private TextView content;
	private TextView contentType;
	private Button btnType;
	public static int clickIndex;
	
	@Override
	public View initView() {
		convertView = UiUtils.inflate(R.layout.listview_item_file_trans);
		name = (TextView) convertView.findViewById(R.id.file_trans_name);
		content = (TextView) convertView.findViewById(R.id.file_trans_content);
		contentType = (TextView) convertView.findViewById(R.id.file_trans_yidu);
		btnType = (Button) convertView.findViewById(R.id.file_trans_button);
		
		btnType.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clickIndex=getPosition();
				Intent intent=new Intent(UiUtils.getContext(),LoadingActivity.class);
				intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.FileTransInteractProtocol");
				intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY,"转为重要中...");
				UrlParamsEntity urlParamsEntity=new UrlParamsEntity();
				//后台的方法名(后台定义好的)
				urlParamsEntity.setMethodName(MobileOAConstant.SWITCH_DOC_TYPE);
				//(参数要有顺序)
				LinkedHashMap<String,String> paramsHashMap=new LinkedHashMap<String, String>();
				paramsHashMap.put("tranCode", "tran00011");
				paramsHashMap.put("docId",getData().getDocumentUserId());
				paramsHashMap.put("Trans2Type","0");
				
				urlParamsEntity.setParamsHashMap(paramsHashMap);
				intent.putExtra("urlParamsEntitiy", urlParamsEntity);
				
				FileTransactionFragment.instance.startActivityForResult(intent, MobileOAConstant.FILE_TRANS_2_PUTONG_REQUEST_CODE);
			}
		});
		if (convertView.getTag() == null) {
			convertView.setTag(this);
		}
		return convertView;
	}

	@Override
	public void refreshView(FileTransaction data) {
		name.setText(data.getSender());
		content.setText(data.getTitle());
		contentType.setText(data.isReading()?"已读":"未读");
		btnType.setText("转为普通");
	}

}
