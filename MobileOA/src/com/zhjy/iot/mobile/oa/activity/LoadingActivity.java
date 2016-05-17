package com.zhjy.iot.mobile.oa.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.protocol.FileTrans2PeopleProtocol;
import com.zhjy.iot.mobile.oa.protocol.FileTransDeleteProtocol;
import com.zhjy.iot.mobile.oa.protocol.FileTransInteractProtocol;
import com.zhjy.iot.mobile.oa.protocol.FileTransSend2FileProtocol;
import com.zhjy.iot.mobile.oa.protocol.FileTransSendFileProtocol;
import com.zhjy.iot.mobile.oa.protocol.LoginProtocol;
import com.zhjy.iot.mobile.oa.protocol.ModifyPasswordProtocol;
import com.zhjy.iot.mobile.oa.protocol.PolicyReceiveProcessActionProtocol;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.tree.TreeNode;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-28下午4:41:30
 * 
 * @描述 透明的进度条页面
 */
public class LoadingActivity extends BaseActivity {

	private LinearLayout ll;
	private String url;
	private AnimationDrawable drawable;
	private boolean is_prevent_back = false;
	private UrlParamsEntity urlParamsEntity;
	private String protocolClassName;
	private String urlHttp;
	private String zipDir;
	private String titles;
	private String description;
	
	private List<TreeNode> peopleNodes;

	public void setPeopleNodes(List<TreeNode> peopleNodes) {
		this.peopleNodes = peopleNodes;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout = R.layout.loading;
		super.onCreate(savedInstanceState);
	}
	

	// initViews
	@SuppressWarnings("deprecation")
	@Override
	protected void initViews() {
		// 需要提示的内容（传入）
		String title = getIntent().getStringExtra(
				MobileOAConstant.LOADING_TITLE_KEY);
		System.out.println("LoadingActivity title:" + title);
		// true 能阻止 false反之
		is_prevent_back = getIntent().getBooleanExtra(
				MobileOAConstant.LOADING_IS_PREVENT_BACK, false);
		TextView tv = (TextView) findViewById(R.id.tv);
		tv.setText(title);
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.alert_round_rect));
		ImageView im = (ImageView) ll.findViewById(R.id.iv);
		im.setBackgroundResource(R.drawable.loading_anim);
		drawable = (AnimationDrawable) im.getBackground();
		drawable.start();
		// 请求服务器的url
		url = getIntent().getStringExtra(MobileOAConstant.LOADING_URL_KEY);
		urlParamsEntity = (UrlParamsEntity) getIntent().getSerializableExtra(
				"urlParamsEntitiy");
		protocolClassName = getIntent().getStringExtra("protocolClassName");
		System.out.println("LoadingActivity protocolClassName:"
				+ protocolClassName);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (is_prevent_back && keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// initInitevnts
	@Override
	protected void initInitevnts() {
		if (protocolClassName != null && !protocolClassName.equals("")) {
			if ("com.zhjy.iot.mobile.oa.protocol.LoginProtocol"
					.equals(protocolClassName.trim())) {
				ThreadManager.getInstance().createLongPool()
						.execute(new Runnable() {

							@Override
							public void run() {
								try {
									LoginProtocol loginProtocol = (LoginProtocol) Class
											.forName(
													"com.zhjy.iot.mobile.oa.protocol.LoginProtocol")
											.newInstance();
									MobileOaApplication.user = loginProtocol
											.loadDataFromWebService(urlParamsEntity);
									processSuccess();
								} catch (ContentException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (final JsonParseException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
			}else if ("com.zhjy.iot.mobile.oa.protocol.FileTransInteractProtocol"
					.equals(protocolClassName.trim())) {
				ThreadManager.getInstance().createLongPool()
						.execute(new Runnable() {

							@Override
							public void run() {
								try {
									SystemClock.sleep(200);
									FileTransInteractProtocol transInteractProtocol = (FileTransInteractProtocol) Class
											.forName(
													"com.zhjy.iot.mobile.oa.protocol.FileTransInteractProtocol")
											.newInstance();
									transInteractProtocol
											.loadDataFromWebService(urlParamsEntity);
									processSuccess();
								} catch (ContentException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (final JsonParseException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						});
			}else if("com.zhjy.iot.mobile.oa.protocol.FileTransDeleteProtocol"
					.equals(protocolClassName.trim())) {//文件传阅的删除操作
				ThreadManager.getInstance().createLongPool()
						.execute(new Runnable() {

							@Override
							public void run() {
								try {
									SystemClock.sleep(200);
									System.out
											.println("------------------------------------------");
									System.out
											.println("LoadingActivity...FileTransDeleteProtocol");
									FileTransDeleteProtocol fileTransDeleteProtocol = (FileTransDeleteProtocol) Class
											.forName(
													"com.zhjy.iot.mobile.oa.protocol.FileTransDeleteProtocol")
											.newInstance();
									fileTransDeleteProtocol
											.loadDataFromWebService(urlParamsEntity);
									processSuccess();
								} catch (ContentException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (final JsonParseException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (Exception e) {
									System.out.println("LoadingActivity...."
											+ e.getMessage());
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
			}else if("com.zhjy.iot.mobile.oa.protocol.ModifyPasswordProtocol"
					.equals(protocolClassName.trim())) {//修改密码
				ThreadManager.getInstance().createLongPool()
						.execute(new Runnable() {

							@Override
							public void run() {
								try {
									SystemClock.sleep(200);
									System.out.println("------------------------------------------");
									System.out.println("LoadingActivity...ModifyPasswordProtocol");
									ModifyPasswordProtocol modifyPasswordProtocol = (ModifyPasswordProtocol) Class.forName("com.zhjy.iot.mobile.oa.protocol.ModifyPasswordProtocol").newInstance();
									modifyPasswordProtocol.loadDataFromWebService(urlParamsEntity);
									processSuccess();
								} catch (ContentException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (final JsonParseException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (Exception e) {
									System.out.println("LoadingActivity...."
											+ e.getMessage());
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
			}else if("com.zhjy.iot.mobile.oa.protocol.FileTrans2PeopleProtocol"
					.equals(protocolClassName.trim())){
					ThreadManager.getInstance().createLongPool()
								.execute(new Runnable() {
			
									@Override
									public void run(){
										try {
											SystemClock.sleep(200);
											System.out.println("------------------------------------------");
											System.out.println("LoadingActivity...FileTransDeleteProtocol");
											FileTrans2PeopleProtocol fileTrans2PeopleProtocol = (FileTrans2PeopleProtocol) Class.forName("com.zhjy.iot.mobile.oa.protocol.FileTrans2PeopleProtocol").newInstance();
											fileTrans2PeopleProtocol.loadDataFromWebService(urlParamsEntity);
											processSuccess();
										} catch (ContentException e) {
											e.printStackTrace();
											processFailure(e);
										} catch (final JsonParseException e) {
											e.printStackTrace();
											processFailure(e);
										} catch (Exception e) {
											System.out.println("LoadingActivity...."
													+ e.getMessage());
											e.printStackTrace();
										}
									}
					});
		
	}else if("com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol".equals(protocolClassName.trim())){
		System.out.println("LoadingActivity...initEvent........." + "= com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol");
		ThreadManager.getInstance().createLongPool()
					.execute(new Runnable() {
						
						@Override
						public void run(){
							try {
								SystemClock.sleep(200);
								System.out.println("------------------------------------------");
								System.out.println("LoadingActivity...FileTransDeleteProtocol");
								SynchronizeDataProtocol fileTrans2PeopleProtocol = (SynchronizeDataProtocol) Class.forName("com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol").newInstance();
								fileTrans2PeopleProtocol.loadDataFromWebService(urlParamsEntity);
								processSuccess();
							} catch (ContentException e) {
								e.printStackTrace();
								processFailure(e);
							} catch (final JsonParseException e) {
								e.printStackTrace();
								processFailure(e);
							} catch (Exception e) {
								processFailure(e);
								System.out.println("LoadingActivity...."
										+ e.getMessage());
								e.printStackTrace();
							}
						}
					});
			}else if("com.zhjy.iot.mobile.oa.protocol.FileTransSendFileProtocol".equals(protocolClassName.trim())){
				System.out.println("LoadingActivity...initEvent........." + "= com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol");
				ThreadManager.getInstance().createLongPool()
							.execute(new Runnable() {
								
								@Override
								public void run(){
									try {
										SystemClock.sleep(200);
										System.out.println("--------");
										System.out.println("LoadingActivity...FileTransDeleteProtocol");
										urlHttp = getIntent().getStringExtra("url");
										zipDir = getIntent().getStringExtra("zipDir");
										FileTransSendFileProtocol fileTransSendFileProtocol = (FileTransSendFileProtocol) Class.forName("com.zhjy.iot.mobile.oa.protocol.FileTransSendFileProtocol").newInstance();
										String getData = fileTransSendFileProtocol.upLoad1(urlHttp, zipDir);
										System.out.println("LoadingActivity getData = " + getData);
										
										
										HashMap<String, String> paramsHashMap = urlParamsEntity.getParamsHashMap();
										paramsHashMap.put("fileInfo", getData);
										
										FileTransSend2FileProtocol mFileTransSend2FileProtocol = (FileTransSend2FileProtocol) Class.forName("com.zhjy.iot.mobile.oa.protocol.FileTransSend2FileProtocol").newInstance();
										mFileTransSend2FileProtocol.loadDataFromWebService(urlParamsEntity);
										processSuccess();
									} catch (Exception e) {
										processFailure(e);
										e.printStackTrace();
										System.out.println("LoadingActivity....77777"
												+ e.getMessage());
									}
								}
							});
					}else if("com.zhjy.iot.mobile.oa.protocol.PolicyReceiveProcessActionProtocol".equals(protocolClassName.trim())){
			System.out.println("LoadingActivity...initEvent........." + "= com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol");
				ThreadManager.getInstance().createLongPool()
						.execute(new Runnable() {
							
							@Override
							public void run(){
								try {
									SystemClock.sleep(200);
									System.out.println("------------------------------------------");
									System.out.println("LoadingActivity...FileTransDeleteProtocol");
									PolicyReceiveProcessActionProtocol policyReceiveProcessActionProtocol = (PolicyReceiveProcessActionProtocol) Class.forName(protocolClassName.trim()).newInstance();
									policyReceiveProcessActionProtocol.loadDataFromWebService(urlParamsEntity);
									processSuccess();
								} catch (ContentException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (final JsonParseException e) {
									e.printStackTrace();
									processFailure(e);
								} catch (Exception e) {
									processFailure(e);
									System.out.println("LoadingActivity...."
											+ e.getMessage());
									e.printStackTrace();
								}
							}
						});
				}
			}else{
			UiUtils.getHandler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					Intent intent = new Intent();
					intent.putExtra(MobileOAConstant.LOAD_STATE,
							MobileOAConstant.LOAD_FAILURE);
					setResult(MobileOAConstant.LOAD_RESULT_CODE, intent);
					finish();
				}
			}, 1000);
			
		}
	}
	
	
	
	
	
	
	

	/**
	 * 处理成功
	 */
	private void processSuccess() {
		Intent intent = new Intent();
		intent.putExtra(MobileOAConstant.LOAD_STATE,
				MobileOAConstant.LOAD_SUCCESS);
		setResult(MobileOAConstant.LOAD_RESULT_CODE, intent);
		finish();
	}

	/**
	 * 处理失败
	 * @param 
	 */
	private void processFailure(final Exception e) {
		UiUtils.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				System.out.println("LoadingActivity....processFailure");
				Intent intent = new Intent();
				intent.putExtra(MobileOAConstant.LOAD_STATE,
						MobileOAConstant.LOAD_FAILURE);
				intent.putExtra(MobileOAConstant.LOAD_CONTENT, e.getMessage());
				setResult(MobileOAConstant.LOAD_RESULT_CODE, intent);
				finish();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 动画的结束
		drawable.stop();
	}
}
