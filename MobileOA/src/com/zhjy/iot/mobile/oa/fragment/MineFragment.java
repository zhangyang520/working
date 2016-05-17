package com.zhjy.iot.mobile.oa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.activity.LoginActivity;
import com.zhjy.iot.mobile.oa.activity.MainPageActivity;
import com.zhjy.iot.mobile.oa.activity.ModifyPasswordActivity;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup.OnFileTreePopupClickListenter;
import com.zhjy.iot.mobile.oa.popup.LogoutPopup;
import com.zhjy.iot.mobile.oa.utils.SysControl;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午 3:16:01
 * 
 * @描述 我的 fragment
 */
public class MineFragment extends Fragment implements OnClickListener {
 
	private View contentView;
	private TextView userName;
	private ImageButton passwordBtn;
	private ImageButton signOut;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = UiUtils.inflate(R.layout.fragment_mine);
		
		initView();
		
		//进行设置标题
		((MainPageActivity)getActivity()).setNevigaTitle(R.string.tab4);
		return contentView;
	}

	private void initView() {
		userName = (TextView) contentView.findViewById(R.id.mine_name_tv);
		if(MobileOaApplication.user!=null){
			userName.setText(MobileOaApplication.user.getRealName());
		}
		MainPageActivity.searchVisible(0);
		passwordBtn = (ImageButton) contentView.findViewById(R.id.mine_password_imgbtn);
		signOut = (ImageButton) contentView.findViewById(R.id.mine_sign_out_imgbtn);
		setAction();
	}

	private void setAction() {
		passwordBtn.setOnClickListener(this);
		signOut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mine_password_imgbtn:
			Intent mIntent = new Intent(UiUtils.getContext(),ModifyPasswordActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			UiUtils.getContext().startActivity(mIntent);
			break;
		case R.id.mine_sign_out_imgbtn:
			LogoutPopup.getInstance().setListener(new OnFileTreePopupClickListenter() {
				
				@Override
				public void onOkClickListener() {
					// TODO Auto-generated method stub
					SysControl.exit_Sys();
					Intent intent = new Intent(UiUtils.getContext(),LoginActivity.class);
					/***设置返回键按钮无效*/
					getActivity().startActivity(intent);
					LogoutPopup.getInstance().dismiss();
				}
				
				@Override
				public void onCancelClickListener() {
					LogoutPopup.getInstance().dismiss();
				}
			});
			LogoutPopup.getInstance().showPopupLogout(getActivity(), signOut,"您是否确定退出？");
			break;
		default:
			break;
		}
	}
	
}
