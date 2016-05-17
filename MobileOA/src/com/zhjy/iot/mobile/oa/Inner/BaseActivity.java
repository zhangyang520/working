package com.zhjy.iot.mobile.oa.Inner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

import com.zhjy.iot.mobile.oa.utils.SysControl;

public abstract class BaseActivity extends Activity {

	protected int customLayout = 0;
	public String TAG = "BaseActivity.class";
	public ProgressDialog progressDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		if(customLayout != 0){
			setContentView(customLayout);
			SysControl.add(this);
		}
		initViews();
		initInitevnts();
	}
	/**
	 * 组件初始化
	 */
	protected abstract void initViews();
	/**
	 * 事件初始化
	 */
	protected abstract void initInitevnts();
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	public interface BeforeLogout{
		public void before();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SysControl.removeCurrent(this);
	}
}
