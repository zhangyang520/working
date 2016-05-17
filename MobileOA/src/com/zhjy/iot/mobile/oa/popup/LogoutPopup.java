package com.zhjy.iot.mobile.oa.popup;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup.OnFileTreePopupClickListenter;

/**
 * 
 * @项目名：MobileOA
 * @类名称：LogoutPopup
 * @类描述： 退出登录界面
 * @创建人：HuangXianFeng
 * @修改人：
 * @创建时间：2016-4-6 上午9:49:38
 * @version
 * 
 */
public class LogoutPopup {

	private static LogoutPopup instance;
    private OnFileTreePopupClickListenter listener;
	private TextView tv_warning;
	private LogoutPopup() {
	}

	public static LogoutPopup getInstance() {
		if (instance == null) {
			synchronized (LoginPopup.class) {
				if (instance == null) {
					instance = new LogoutPopup();
				}
			}
		}
		return instance;
	}

	private  PopupWindow popupWindow;
	
	private  View contentView;

	public  void showPopupLogout(final Context context, View parent,String title) {
		if (popupWindow == null) {
			popupWindow = new PopupWindow((int) context.getResources()
					.getDimension(R.dimen.logout_wight), (int) context
					.getResources().getDimension(R.dimen.logout_height));
			contentView = View.inflate(context, R.layout.popup_mine_logout, null);
			tv_warning=(TextView) contentView.findViewById(R.id.tv_warning);
		}
		Button btnOK = (Button) contentView.findViewById(R.id.logout_yes);
		Button btnCancel = (Button) contentView.findViewById(R.id.logout_cancel);
		tv_warning.setText(title);
		
		/***确定退出按钮**/
		btnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onOkClickListener();
				}
			}
		});
		
		/**取消按钮*/
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					listener.onCancelClickListener();
				}
			}
		});
		
		popupWindow.setContentView(contentView);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popup_file_round_rect));
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
	}

	public  PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public  void setPopupWindow(PopupWindow popupWindow) {
		popupWindow = popupWindow;
	}

	public OnFileTreePopupClickListenter getListener() {
		return listener;
	}

	public void setListener(OnFileTreePopupClickListenter listener) {
		this.listener = listener;
	}
	
	/**
	 * 进行消失对话框
	 */
	public void dismiss(){
		if(popupWindow!=null && popupWindow.isShowing()){
			popupWindow.dismiss();
		}
	}
}
