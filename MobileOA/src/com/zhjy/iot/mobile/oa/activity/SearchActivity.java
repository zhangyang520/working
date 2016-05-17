package com.zhjy.iot.mobile.oa.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.manager.PolicyPagerManager;
import com.zhjy.iot.mobile.oa.view.PolicyReceiveYibanLoadingPager;
/**
 * 
 * @项目名：MoblieOA
 * @类名称：搜索界面
 * @类描述：
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-5-16 下午6:26:37
 * @version
 * 
 */
public class SearchActivity extends BaseActivity implements OnClickListener {

	private ImageButton btnBack;
	private EditText etSearch;
	private ImageButton btnSearch;
	private FrameLayout flView;
	private String editContent;
	private PolicyReceiveYibanLoadingPager policyReceiveYibanLoadingPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		customLayout=R.layout.activity_search_activity2;
		super.onCreate(savedInstanceState);
	}
	/**
	 * 进行初始化view
	 */
	@Override
	protected void initViews() {
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		etSearch = (EditText) findViewById(R.id.et_search);
		btnSearch = (ImageButton) findViewById(R.id.btn_search);
		flView = (FrameLayout) findViewById(R.id.search_ui_activity2);
		policyReceiveYibanLoadingPager=(PolicyReceiveYibanLoadingPager) PolicyPagerManager.getLoadingPager(1);
	}

	/**
	 * 进行初始化事件
	 */
	@Override
	protected void initInitevnts() {
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		etSearch.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				policyReceiveYibanLoadingPager.setQueryTitle(s.toString());
			}
		});
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_back:
				finish();
				break;
				
			case R.id.btn_search:
				//进行搜索
				searchYIBANContent();
				break;
		}
	} 
	
	/**
	 * 进行搜索已办的内容
	 */
	private void searchYIBANContent() {
		//进行不处理
		if(policyReceiveYibanLoadingPager.getParent()!=null){
			((ViewGroup)policyReceiveYibanLoadingPager.getParent()).removeView(policyReceiveYibanLoadingPager);
		}
		if(flView.getChildCount()==1){
			
		}else{
			flView.addView(policyReceiveYibanLoadingPager);
		}
		policyReceiveYibanLoadingPager.show();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		policyReceiveYibanLoadingPager.setQueryTitle("");
	}
}
