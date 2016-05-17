package com.zhjy.iot.mobile.oa.popup;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.activity.DownloadingActivity;
import com.zhjy.iot.mobile.oa.adapter.FileAdapter;
import com.zhjy.iot.mobile.oa.adapter.FileAdapter.FilePopupDismissListener;
import com.zhjy.iot.mobile.oa.entity.AppendDoc;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-31上午11:40:59
 * 
 * @描述 展现文件列表的框工具类
 */
public class FileAppendPopup{
	
	static FileAppendPopup instance;
	private PopupWindow popupWindow;
	private View contentView;
	//私有化构造方法
	private FileAppendPopup(){
		
	}
	
	public static FileAppendPopup getInstance(){
		if(instance==null){
			synchronized (FileTreePopup.class) {
				if(instance==null){
					instance=new FileAppendPopup();
				}
			}
		}
		return instance;
	}
	public  void showPopupFileList(final Activity activity,View parent,final List<AppendDoc> datas){
		
		if(popupWindow==null){
			popupWindow = new PopupWindow(//
					(int)activity.getResources().getDimension(R.dimen.file_popup_width),//
											(int)activity.getResources().getDimension(R.dimen.file_popup_height));
			contentView = View.inflate(activity, R.layout.popup_file_append_content, null);
		}
		
		//取得关闭按钮
		Button btn_close=(Button) contentView.findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		ListView listView=(ListView) contentView.findViewById(R.id.lv);
		
		FileAdapter adapter=new FileAdapter(datas,activity,popupWindow);
		adapter.setListener(new FilePopupDismissListener() {
			
			@Override
			public void getItemClickFile(int position) {
				popupWindow.dismiss();
				//进行跳转文件下载提示框:
				/*
				 * 	url=getIntent().getStringExtra(MobileOAConstant.LOADING_URL_KEY);
				    fileName=getIntent().getStringExtra(MobileOAConstant.DOWNLOAD_FILE_NAME);
				 */
				Intent intent=new Intent(activity,DownloadingActivity.class);
				
				intent.putExtra(MobileOAConstant.LOADING_URL_KEY,MobileOAConstant.DOWNLOAD_URL+"?docId="+datas.get(position).getDocId());
				System.out.println("download url:"+MobileOAConstant.DOWNLOAD_URL+"?docId="+datas.get(position).getDocId());
//				intent.putExtra(MobileOAConstant.LOADING_URL_KEY,MobileOAConstant.DOWNLOAD_URL+"?docId=30521");
				intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY,"文件下载中.....");
				intent.putExtra(MobileOAConstant.DOWNLOAD_FILE_NAME,datas.get(position).getDocName());
//				intent.putExtra(MobileOAConstant.DOWNLOAD_FILE_NAME,"123456.zip");
				activity.startActivityForResult(intent, MobileOAConstant.LOAD_REQUEST_CODE);
			}
		});
		listView.setAdapter(adapter);
		listView.setCacheColorHint(R.color.white);
		listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_INSET);
		
		popupWindow.setContentView(contentView);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.popup_file_round_rect));
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}

	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public void setPopupWindow(PopupWindow popupWindow) {
		this.popupWindow = popupWindow;
	}
	
}

