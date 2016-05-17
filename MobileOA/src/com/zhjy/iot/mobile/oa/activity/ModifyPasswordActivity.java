package com.zhjy.iot.mobile.oa.activity;


import java.util.LinkedHashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.LoginUserDao;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;

/**
 * 
 * @项目名：MobileOA 
 * @类名称：ModifyPasswordActivity   
 * @类描述：   我的界面修改密码
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-5 下午4:46:41  
 * @version    
 *
 */
public class ModifyPasswordActivity extends BaseActivity {
	
	
	private EditText etOldPwd;
	private EditText etNewPwd;
	private EditText etAgainNewPwd;
	private Button savePwd;
	private TextView nevigaTitle;
	private ImageButton backBtn;
	private String againPwd;
	private String newPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout = R.layout.activity_modify_password;
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initViews() {
		backBtn = (ImageButton) findViewById(R.id.back_activity);
		nevigaTitle = (TextView) findViewById(R.id.nevigaTitle);
		
		etOldPwd = (EditText) findViewById(R.id.old_password);
		etNewPwd = (EditText) findViewById(R.id.modify_et_new_password);
		etAgainNewPwd = (EditText) findViewById(R.id.modify_et_again_new_password);
		
		savePwd = (Button) findViewById(R.id.btn_save_pwd);
		String md5Pwd = MobileOaApplication.user.getMD5Pwd();
		System.out.println("ModifyPasswordActivity...md5Pwd = " + md5Pwd);
		etOldPwd.setText(md5Pwd);
	}

	@Override
	protected void initInitevnts() {
		nevigaTitle.setText("密码修改");
		
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		/**登录按钮*/
		savePwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (checkPwd()) {
					Intent intent = new Intent(ModifyPasswordActivity.this,LoadingActivity.class);
					intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.ModifyPasswordProtocol");
					//intent.putExtra(MobileOAConstant.LOADING_URL_KEY, MobileOAConstant.CESHIURL);
					intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY, "密码修改中");
					intent.putExtra(MobileOAConstant.LOADING_IS_PREVENT_BACK, true);
					
					UrlParamsEntity urlParamsEntity = new UrlParamsEntity();
					urlParamsEntity.setMethodName(MobileOAConstant.UPDATE_USE_RPASSWORDS);//后台定义好的方法名称
					
					LinkedHashMap<String, String> paramsHashMap = new LinkedHashMap<String, String>();
					paramsHashMap.put("tranCode", "tran0017");
					paramsHashMap.put("operatorId", MobileOaApplication.user.getOperationId());
					paramsHashMap.put("newMD5Pwd", againPwd);
					urlParamsEntity.setParamsHashMap(paramsHashMap);
					intent.putExtra("urlParamsEntitiy", urlParamsEntity);
					
					startActivityForResult(intent, MobileOAConstant.MODIFY_PASSWORD_REQUEST_CODE);
				}
			}
		});
	}

	/**检验新密码是否一致和是否为空*/
	protected boolean checkPwd() {
		newPwd = etNewPwd.getText().toString().trim();
		againPwd = etAgainNewPwd.getText().toString().trim();
		if (!isPasswordNo(newPwd,"新密码")) {
			return false;
		}else if (!isPasswordNo(againPwd,"确认密码")) {
			return false;
		}
		
		if (newPwd.trim().equals(againPwd.trim())) {
			return true;
		}else{
			UiUtils.showToast("新密码和确认密码不一致!");
			return false;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("ModifyPasswordActivity...onActivityResult...requestCode::" + requestCode + ";resultCode= " + resultCode);
		if (requestCode == MobileOAConstant.MODIFY_PASSWORD_REQUEST_CODE && resultCode == MobileOAConstant.LOAD_RESULT_CODE) {
			String loadState = data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if (MobileOAConstant.LOAD_SUCCESS.equals(loadState)) {
				//String loadContent = data.getStringExtra(MobileOAConstant.LOAD_CONTENT);
				try {
					/**如果加载成功，则进行数据的获取*/
					String successPwd = etAgainNewPwd.getText().toString().trim();
					MobileOaApplication.user.setMD5Pwd(successPwd);
					LoginUserDao.getInstance().saveUserPwdByUserId(MobileOaApplication.user.getOperationId(), MobileOaApplication.user);
					UiUtils.showToast("密码修改成功");
				} catch (ContentException e) {
					e.printStackTrace();
				}
				finish();
			}else{
				String errorContent=data.getStringExtra(MobileOAConstant.LOAD_CONTENT);
				UiUtils.showToast(errorContent);
			}
		}
	}
	
	/***检验密码的正则表达式
	 * @param passwd **/
	public static boolean isPasswordNo(String string, String passwd){
//		String checkPassword = "(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{1,}";
		String checkPassword = "\\w+";
		boolean flag=false;
		if (TextUtils.isEmpty(string.trim())) {
			UiUtils.showToast(passwd+"不能为空!");
			flag=false;
		}else{
			flag=string.matches(checkPassword);
			if(!flag){
				UiUtils.showToast(passwd+"至少一位单词字符!");
			}
		}
		return flag;
	}
	
	
}
