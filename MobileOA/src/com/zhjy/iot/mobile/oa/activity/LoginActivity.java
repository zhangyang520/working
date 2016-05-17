package com.zhjy.iot.mobile.oa.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView.BufferType;
import android.widget.EditText;
import android.widget.Toast;

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
import com.zhjy.iot.mobile.oa.popup.LoginPopup;
import com.zhjy.iot.mobile.oa.popup.LoginPopup.OnLoginItemClickListener;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午2:48:38
 * 
 * @描述   登录页
 */
public class LoginActivity extends BaseActivity{

	private Button loginBtn;
	private EditText edLoginName;
	private EditText edLoginPd;
	private CheckBox cb_auto;
//	private LoginPopup pop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		customLayout=R.layout.activity_login;
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void initViews(){
		loginBtn = (Button) findViewById(R.id.login_btn);
		edLoginName = (EditText) findViewById(R.id.login_name);
		edLoginPd = (EditText) findViewById(R.id.login_password);
		cb_auto = (CheckBox) findViewById(R.id.cb_auto);
		edLoginName.setFocusable(true);
//		pop = new LoginPopup();
		cb_auto.setChecked(SystemSettingDao.getInstance().getSystemSettingAutoLogin());
		if(SystemSettingDao.getInstance().getSystemSettingAutoLogin()){
			try {
				LoginUser user=LoginUserDao.getInstance().getLastUser();
				if(user!=null){
					edLoginName.setText(user.getLoginName());
					edLoginPd.setText(user.getMD5Pwd());
					LoginPopup.getInstance().dismissWindow();
				}
			} catch (ContentException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void initInitevnts() {
		/**
		 * 进入到主页的按钮
		 */
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!checkLoginName()){
					LoginPopup.getInstance().dismissWindow();
					Intent intent=new Intent(UiUtils.getContext(),LoadingActivity.class);
					intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.LoginProtocol");
					intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY,"登录中");
					UrlParamsEntity urlParamsEntity=new UrlParamsEntity();
					//后台的方法名(后台定义好的)
					urlParamsEntity.setMethodName(MobileOAConstant.CHECK_LOGIN);
					//(参数要有顺序)
					LinkedHashMap<String,String> paramsHashMap=new LinkedHashMap<String, String>();
					paramsHashMap.put("tranCode", "tran00001");
					paramsHashMap.put("userName", edLoginName.getEditableText().toString().trim());
					paramsHashMap.put("md5Passwd",edLoginPd.getEditableText().toString().trim());
					urlParamsEntity.setParamsHashMap(paramsHashMap);
					intent.putExtra("urlParamsEntitiy", urlParamsEntity);
					startActivityForResult(intent, MobileOAConstant.LOAD_REQUEST_CODE);
				}else{
					Toast.makeText(UiUtils.getContext(),"用户名和密码不能为空!",0).show();
				}
			}
		});
		
	
		edLoginName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				System.out.println(s.toString());
			}
			
			/**
			 * 将LoginUser集合转换成string集合
			 * @param datas
			 */
			private List<String> transLoginUserToString(List<LoginUser> datas) {
				List<String> nameDatas=new ArrayList<String>();
				for(LoginUser data:datas){
					nameDatas.add(data.getLoginName());
				}
				return nameDatas;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				System.out.println("afterTextChanged........");
				try {
					if(!s.toString().trim().equals("") && !(LoginUserDao.getInstance().getUserListByName(s.toString().trim()).size()>0)){
						//进行获取修改后的字符串
						try {
							final List<LoginUser> datas=LoginUserDao.getInstance().getUserListLikeName(s.toString().trim());
						    if(datas.size()>0){
						    	//进行展示popupWindow
						    	LoginPopup.getInstance().showPopupWindow(edLoginName,//
										UiUtils.getDimen(R.dimen.login_btn_width), //
														UiUtils.getDimen(R.dimen.login_btn_height),
																			transLoginUserToString(datas),new OnLoginItemClickListener() {
																				@Override
																				public void onItemCilick(final int position) {
																						edLoginName.setText(datas.get(position).getLoginName());
																						edLoginPd.setText(datas.get(position).getMD5Pwd());
																				}
																			});
						    }else{
						    	LoginPopup.getInstance().dismissWindow();
						    }
						} catch (ContentException e) {
							e.printStackTrace();
						}
					}else{
						LoginPopup.getInstance().dismissWindow();
					}
				} catch (ContentException e) {
					e.printStackTrace();
				}
			}
		});
		
		cb_auto.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SystemSettingDao.getInstance().updateSettingAutoLogin(isChecked);
			}
		});
	}

	/**
	 * 检测输入用户名和密码
	 * @return
	 */
	protected boolean checkLoginName(){
		String loginName = edLoginName.getText().toString().trim();
		String loginPd = edLoginPd.getText().toString().trim();
		return loginName.trim().equals("") || loginPd.trim().equals("");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==MobileOAConstant.LOAD_REQUEST_CODE && 
							resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			//进行处理请求码
			String loadState=data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if(MobileOAConstant.LOAD_SUCCESS.equals(loadState)){
				try {
					//如果加载的状态显示为成功,进行获取内容
					SystemSettingDao.getInstance().updateSettingAutoLogin(cb_auto.isChecked());
					MobileOaApplication.user.setLoginName(edLoginName.getText().toString().trim());
					MobileOaApplication.user.setMD5Pwd(edLoginPd.getText().toString().trim());
					
					LoginUserDao.getInstance().saveUser(MobileOaApplication.user);
					startActivity(new Intent(LoginActivity.this,MainActivity.class));
					finish();
					
					//机型启动个线程同步数据
					synchronizedDatas();
				} catch (ContentException e) {
					e.printStackTrace();
					Toast.makeText(UiUtils.getContext(),e.getMessage(), 0).show();
				}
			}else{
				//加载的状态显示为失败
				Toast.makeText(UiUtils.getContext(),data.getStringExtra(MobileOAConstant.LOAD_CONTENT), 0).show();
			}
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
