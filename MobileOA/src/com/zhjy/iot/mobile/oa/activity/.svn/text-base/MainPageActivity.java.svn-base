package com.zhjy.iot.mobile.oa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.fragment.FileTransactionFragment;
import com.zhjy.iot.mobile.oa.fragment.MineFragment;
import com.zhjy.iot.mobile.oa.fragment.PolicyReceiveFragment;
import com.zhjy.iot.mobile.oa.fragment.ReceiveNotificationFragment;
import com.zhjy.iot.mobile.oa.manager.PolicyPagerManager;
import com.zhjy.iot.mobile.oa.utils.SysControl;
/**
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午2:51:34
 * 
 * @描述   主界面包含四个模块:
 *      行政收文,文件传阅,收文通知,我的模块
 */
public class MainPageActivity extends FragmentActivity {

	private int showIndex = 0;//菜单选择
	private FragmentTabHost tabHost;//菜单切换
	private TextView nevigaTitle;//导航栏
	public static  ImageButton btnSearch;
	
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		setContentView(R.layout.activity_main_page);
		SysControl.add(this);
		//进行初始化views
		initView();
		//进行获取Intent传递过来的参数：
	    showIndex = getIntent().getIntExtra("showIndex", 0);//点击的序号，默认展示
	   
	    tabHost.setCurrentTab(showIndex);
	}
	
    
	/**
	 * 进行初始化views
	 */
	private void initView() {
		tabHost=(FragmentTabHost)findViewById(R.id.tabhost);
		nevigaTitle=(TextView)findViewById(R.id.nevigaTitle);
		btnSearch = (ImageButton) findViewById(R.id.btn_img_search);
		
		tabHost.setup(this, getSupportFragmentManager(),R.id.realtabcontent);
		
		View indicator;
		TabHost.TabSpec spec1 = tabHost.newTabSpec("0");
		indicator = getLayoutInflater().inflate(R.layout.menutab1, null);
		spec1.setIndicator(indicator);
		tabHost.addTab(spec1,PolicyReceiveFragment.class,null);
		
		TabHost.TabSpec spec2 = tabHost.newTabSpec("1");
		indicator = getLayoutInflater().inflate(R.layout.menutab2, null);
		spec2.setIndicator(indicator);
		tabHost.addTab(spec2,FileTransactionFragment.class,null);
		
		TabHost.TabSpec spec3 = tabHost.newTabSpec("2");
		indicator = getLayoutInflater().inflate(R.layout.menutab3, null);
		spec3.setIndicator(indicator);
		tabHost.addTab(spec3,ReceiveNotificationFragment.class,null);
		
		TabHost.TabSpec spec4 = tabHost.newTabSpec("3");
		indicator = getLayoutInflater().inflate(R.layout.menutab4, null);
		spec4.setIndicator(indicator);
		tabHost.addTab(spec4,MineFragment.class,null);
		
		tabHost.setCurrentTab(showIndex);//设置展示页面的序号
		
		
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(MainPageActivity.this,SearchActivity.class);
				startActivity(mIntent);
			}
		});
	}
	
	public static void searchVisible(int index){
		if (index == 0) {
			btnSearch.setVisibility(View.GONE);
		}else{
			btnSearch.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * 
	 * @param view
	 */
   public void  backMain(View view){
	   finish();
   }
   
   /**
    * 设置导航栏的标题
    * @param title
    */
   public void setNevigaTitle(int titleId){
	   nevigaTitle.setText(titleId);
   }
   
    @Override
	protected void onDestroy() {
    	//进行对"行政收文,文件传阅,收文通知"的network时间初始化0
    	PolicyPagerManager.initPolicyPagerNetowrkTime();
		super.onDestroy();
		SysControl.removeCurrent(this);
	}
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
    	super.onActivityResult(arg0, arg1, arg2);
    }
}
