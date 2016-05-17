package com.zhjy.iot.mobile.oa.popup;

import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup.OnFileTreePopupClickListenter;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：IsFileTransPopup   
 * @类描述：  是否传阅提示框
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-9 下午2:49:39  
 * @version    
 *
 */
public class IsFileTransPopup {
	
	private static IsFileTransPopup instance;
    private OnFileTreePopupClickListenter listener;
	public IsFileTransPopup() {
		super();
	}
	
	public static IsFileTransPopup getInstance(){
		if (instance == null) {
			synchronized (IsFileTransPopup.class) {
				if (instance == null) {
					instance = new IsFileTransPopup();
				}
			}
		}
		return instance;
	}
	
	private  PopupWindow popupWindow;
	private  View contentView;
	
	public void showPopupIsFileTran(final Activity activity,final View parent,final List<Node> list){
		
		if(popupWindow==null){
			popupWindow = new PopupWindow(
					(int)activity.getResources().getDimension(R.dimen.file_popup_width),
					(int)activity.getResources().getDimension(R.dimen.file_popup_height_new));
			contentView = View.inflate(activity, R.layout.popup_is_file_trans_content, null);
		}
		
		
		Button btn_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				if(listener!=null){
					listener.onCancelClickListener();
				}
			}
		});
		
		Button btn_ok = (Button) contentView.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v){
				FileTreePopup.getInstance().setOkListener(listener);
				FileTreePopup.getInstance().showPopupFileList(activity, parent,list);
				popupWindow.dismiss();
			}
		});
		
		
		
		popupWindow.setContentView(contentView);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.popup_file_round_rect));
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		
	}

	/**
	 * @return the listener
	 */
	public OnFileTreePopupClickListenter getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(OnFileTreePopupClickListenter listener) {
		this.listener = listener;
	}
}
