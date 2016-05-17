package com.zhjy.iot.mobile.oa.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.PolicyReceDao;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveDetail;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.entity.ReceiveFileState;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.holder.FormHolder;
import com.zhjy.iot.mobile.oa.popup.FileAppendPopup;
import com.zhjy.iot.mobile.oa.protocol.PolicyReceiveActivityProtocol;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.OpenFileTipDialog;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：PolicyReceiveWeiBanActivity   
 * @类描述：   行政收文未办item数据点击管理类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-30 下午4:26:50  
 * @version    
 *
 */
public class PolicyReceiveWeiBanActivity extends BaseActivity{

	private ImageButton backtn;
	private TextView nevigaTitle;
	private LoadingPager mLoadingPager;
	private PolicyReceiveActivityProtocol mPolicyReceiveActivityProtocol = new PolicyReceiveActivityProtocol();
	
	PolicyReceiveDetail mPolicyReceiveDetail;
	private LinearLayout rootView;
	private String keyTitle="行政未办收文详情";
	private PolicyReceiveFile policyReceiveFile;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message message) {
			switch (message.what) {
			case MobileOAConstant.LOAD_DATA_ERROR:
				UiUtils.showToast("数据解析异常");
				break;
			case MobileOAConstant.LOAD_DATA_FAIL:
				//展示信息
				UiUtils.showToast((String)message.obj);
				break;
			default:
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout = R.layout.file_form_policy_details_one;
		policyReceiveFile = (PolicyReceiveFile)getIntent().getSerializableExtra("policyReceiveFile");
		String weiban = getIntent().getStringExtra("weiban");
		mPolicyReceiveActivityProtocol.setYiBanType(weiban);
		super.onCreate(savedInstanceState);
		initLoadingPager();
	}
	
	
	@Override
	protected void initViews() {
		rootView = (LinearLayout) findViewById(R.id.load_view);
		backtn = (ImageButton) findViewById(R.id.back_activity);
		nevigaTitle = (TextView) findViewById(R.id.nevigaTitle);
		nevigaTitle.setText("未办详情");
	}

	@Override
	protected void initInitevnts() {
		backtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/***
	 * 描述：初始化LoadingPager
	 */
	private void initLoadingPager() {
		if (mLoadingPager == null) {
			mLoadingPager = new LoadingPager(UiUtils.getContext()) {
				
				@Override
				protected LoadResult load() {
					Message message = new Message();
					List<PolicyReceiveDetail> list=null;
					try {
						
						final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
						mUrlParamsEntity.setMethodName(MobileOAConstant.POLICY_RECEIVE_WEIBAN_DATEIL);
						LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
						paramsMap.put("tranCode", "tran00005");
						paramsMap.put("userId",  MobileOaApplication.user.getUserId());
						paramsMap.put("workItemId", policyReceiveFile.getWorkItemId());

						mUrlParamsEntity.setParamsHashMap(paramsMap);
						mPolicyReceiveDetail = mPolicyReceiveActivityProtocol.loadDataFromWebService(mUrlParamsEntity);
						message.what=MobileOAConstant.REQUEST_FIRST_PAGE;
						handler.sendMessage(message);
						
						list=new ArrayList<PolicyReceiveDetail>();
						list.add(mPolicyReceiveDetail);
						return checkData(list);
					} catch (JsonParseException e) {
						message.what = MobileOAConstant.LOAD_DATA_ERROR;
						handler.sendMessage(message);
						e.printStackTrace();
						return checkData(list);
					} catch (ContentException e) {
						e.printStackTrace();
						if(e.getErrorCode()==MobileOAConstant.NO_NODE_ERROR_CODE){
							//Node查找数据库出错:提示用户进行同步所有用户数据
							
							return LoadResult.syschronize_data;
						}else{
							message.what = MobileOAConstant.LOAD_DATA_FAIL;
							message.obj = keyTitle+"加载首页失败,"+e.getMessage();
							handler.sendMessage(message) ;
							return checkData(list);
						}
					}
				}
				
				@Override
				public View createSuccessView(){
					FormHolder mFormHolder = new FormHolder(mPolicyReceiveDetail.getFileTypes(),PolicyReceiveWeiBanActivity.this);
					mFormHolder.setFileState(ReceiveFileState.WEIBAN.getValue());
					//需要进行设置工作项的id
					mPolicyReceiveDetail.setWorkItemId(policyReceiveFile.getWorkItemId());
					mFormHolder.setData(mPolicyReceiveDetail);
					View contentView = mFormHolder.getContentView();
					return contentView;
				}
				
				@Override
				protected LoadResult synchronizeUserData() {
					
					try {
						UrlParamsEntity urlParamsEntitiy=new UrlParamsEntity();
						//后台的方法名(后台定义好的)
						urlParamsEntitiy.setMethodName(MobileOAConstant.SYNBASICRESOURCE);
						//tran00002
						LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
						map.put("tranCode", "tran00002");
						urlParamsEntitiy.setParamsHashMap(map);
						SynchronizeDataProtocol protocol=new SynchronizeDataProtocol();
						protocol.loadDataFromWebService(urlParamsEntitiy);
						
						//如果同步成功了..怎么办
						mPolicyReceiveDetail=mPolicyReceiveActivityProtocol.parseJson(mPolicyReceiveActivityProtocol.getSuccessSoapObject());
						return LoadResult.success;
					} catch (ContentException e) {
						e.printStackTrace();
						System.out.println("synchronizedDatas "+e.getMessage());
						return LoadResult.syschronize_data;
					} catch (JsonParseException e) {
						e.printStackTrace();
						System.out.println("synchronizedDatas "+e.getMessage());
						return LoadResult.syschronize_data;
					}
				}

			};
			mLoadingPager.setEmptyString(keyTitle+"数据为空!");
			mLoadingPager.setErrorString(keyTitle+"数据加载失败!");
			mLoadingPager.setLoadingString(keyTitle+"加载中...");
			mLoadingPager.show();
			mLoadingPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
			rootView.addView(mLoadingPager);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==MobileOAConstant.LOAD_REQUEST_CODE && 
				resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			//进行处理请求码
			String loadState=data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if(MobileOAConstant.LOAD_SUCCESS.equals(loadState)){
				String fileName=data.getStringExtra(MobileOAConstant.LOAD_CONTENT);
				OpenFileTipDialog.openFiles(fileName,UiUtils.getContext()); 
				Toast.makeText(UiUtils.getContext(),"文件下载成功!", 0).show();
			}else{
				//加载的状态显示为失败
				Toast.makeText(UiUtils.getContext(),"文件下载失败请查看网络连接!", 0).show();
			}
		}else if(requestCode==MobileOAConstant.BANLI_REQUEST_CODE &&// 
								resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			//进行处理请求码
			String loadState=data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if(MobileOAConstant.LOAD_SUCCESS.equals(loadState)){
				try {
					Toast.makeText(UiUtils.getContext(),"操作成功!", 0).show();
					//需要将对应的数据库中进行:改变状态--->将未办的条目变为已办
					policyReceiveFile.setState(ReceiveFileState.YIBAN.getValue());
					PolicyReceDao.getInstance().updatePolicyReceiveList(policyReceiveFile);
				} catch (ContentException e){
					e.printStackTrace();
				}
				//设置结束码
				setResult(MobileOAConstant.WEIBAN_ACTIVITY_RESULT_CODE);
				//进行消失页面
				finish();
			}else{
				//加载的状态显示为失败
				Toast.makeText(UiUtils.getContext(),"操作失败!", 0).show();
			}
		}else{
			System.out.println("PolicyReceiveWeiBanActivity onActivityResult else:..........");
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(FileAppendPopup.getInstance().getPopupWindow()!=null  && FileAppendPopup.getInstance().getPopupWindow().isShowing()){
			FileAppendPopup.getInstance().getPopupWindow().dismiss();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
