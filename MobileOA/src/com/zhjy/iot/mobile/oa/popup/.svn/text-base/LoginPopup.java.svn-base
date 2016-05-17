package com.zhjy.iot.mobile.oa.popup;

import java.util.List;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.utils.UiUtils;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-27下午1:47:09
 * 
 * @描述 登录的popup的使用
 */
public class LoginPopup {
	
	private static LoginPopup instance;
	private PopupWindow popupWindow;
	private ListView listView;
	private ArrayAdapter<String> arrayAdapter;
	//私有化构造函数
	private LoginPopup(){
		
	}
	
	
	/**
	 * 进行获取实例
	 * @return
	 */
	public static LoginPopup getInstance(){
		if(instance==null){
			synchronized (LoginPopup.class) {
				if(instance==null){
					instance=new LoginPopup();
				}
			}
		}
		return instance;
	}
	/**
	 * 进行展示popupWindow
	 * @param parent
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("ResourceAsColor")
	public void  showPopupWindow(View parent,int width,int height,List<String> nameData,final OnLoginItemClickListener listener){
		
		if(popupWindow==null){
			popupWindow =new PopupWindow(width,height);
			listView = new ListView(UiUtils.getContext());
			listView.setLayoutParams(new ViewGroup.LayoutParams(//
									ViewGroup.LayoutParams.MATCH_PARENT,//
											ViewGroup.LayoutParams.WRAP_CONTENT));
			arrayAdapter = new ArrayAdapter<String>(UiUtils.getContext(),R.layout.item_array_adapter,R.id.txt,nameData);
			listView.setAdapter(arrayAdapter);
			listView.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.popup_round_rect));
			listView.setCacheColorHint(R.color.white);
			listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_INSET);
		}else{
			arrayAdapter.clear();
			arrayAdapter.addAll(nameData);
			arrayAdapter.notifyDataSetChanged();
		}
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(popupWindow!=null&&popupWindow.isShowing()){
					popupWindow.dismiss();
				}
				listener.onItemCilick(arg2);
			}
		});
		popupWindow.setContentView(listView);
		popupWindow.setOutsideTouchable(true);
		popupWindow.showAsDropDown(parent,0, -height*2);
	}
	
	
	/**
	 * 进行消失对话框
	 */
	public void dismissWindow(){
		if(popupWindow!=null && popupWindow.isShowing()){
			popupWindow.dismiss();
		}
	}
	public interface OnLoginItemClickListener{
		void onItemCilick(int position);
	}
}
