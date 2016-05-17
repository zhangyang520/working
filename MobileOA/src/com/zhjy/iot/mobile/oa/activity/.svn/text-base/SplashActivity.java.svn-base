package com.zhjy.iot.mobile.oa.activity;

import java.util.LinkedHashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.LoginUserDao;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.dao.SystemSettingDao;
import com.zhjy.iot.mobile.oa.entity.LoginUser;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午2:48:57
 * 
 * @描述  引导页
 */
public class SplashActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout rl_top;
	private boolean clickFlag;
	private LoginUser user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout=R.layout.activity_splash;
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initViews() {
		rl_top=(RelativeLayout) findViewById(R.id.rl_top);
		rl_top.setOnClickListener(this);
		
		if(!isTaskRoot()){
			finish();
		}else{
			try {
				user = LoginUserDao.getInstance().getLastUser();
				if(SystemSettingDao.getInstance().getSystemSettingAutoLogin() && user!=null){
					//登录成功之后进行修改数据
					try {
							//如果加载的状态显示为成功,进行获取内容
							MobileOaApplication.user=user;
							LoginUserDao.getInstance().saveUser(MobileOaApplication.user);
							startActivity(new Intent(SplashActivity.this,MainActivity.class));
							finish();
							synchronizedDatas();
					} catch (ContentException e){
							e.printStackTrace();
							startActivity(new Intent(SplashActivity.this,MainActivity.class));
							finish();
					}
				}else{
					UiUtils.getHandler().postDelayed(new Runnable() {
						@Override
						public void run() {
							synchronized (SplashActivity.class) {
								if(!clickFlag){
									SplashActivity.this.startActivity(new Intent(SplashActivity.this,LoginActivity.class));
									SplashActivity.this.finish();
								}
							}
						}
					}, 1000);
				}
			} catch (ContentException e) { 
				e.printStackTrace();
				UiUtils.getHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if(!clickFlag){
							SplashActivity.this.startActivity(new Intent(SplashActivity.this,LoginActivity.class));
							SplashActivity.this.finish();
						}
					}
				}, 1000);
			}
		}
	}
	
	@Override
	protected void initInitevnts() {
		
	}

	@Override
	public void onClick(View view){
		//进行对id进行判断
		switch(view.getId()){
			case R.id.rl_top:
					if(!SystemSettingDao.getInstance().getSystemSettingAutoLogin()){
						synchronized (SplashActivity.class) {
							clickFlag=true;
							SplashActivity.this.startActivity(new Intent(SplashActivity.this,LoginActivity.class));
							SplashActivity.this.finish();
						}
					}
				break;
		}
		
	}
	
	
	/**
	 * 启动线程同步数据
	 */
	private void synchronizedDatas(){
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					UrlParamsEntity urlParamsEntitiy=new UrlParamsEntity();
					//后台的方法名(后台定义好的)
					urlParamsEntitiy.setMethodName(MobileOAConstant.SYNBASICRESOURCE);
					//tran00002
					LinkedHashMap<String,String> map=new LinkedHashMap<String, String>();
					map.put("tranCode", "tran00002");
					urlParamsEntitiy.setParamsHashMap(map);
					SynchronizeDataProtocol protocol=new SynchronizeDataProtocol();
					protocol.loadDataFromWebService(urlParamsEntitiy);
					System.out.println("synchronizedDatas getdatas:"+NodeDao.getInstance().getNodeList().toString());
				} catch (ContentException e) {
					e.printStackTrace();
					System.out.println("synchronizedDatas "+e.getMessage());
				} catch (JsonParseException e) {
					e.printStackTrace();
					System.out.println("synchronizedDatas "+e.getMessage());
				}
			}
		});
				
			
	}
}
