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
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveDetail;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.holder.FormHolder;
import com.zhjy.iot.mobile.oa.popup.FileAppendPopup;
import com.zhjy.iot.mobile.oa.protocol.ReceiveNotificationActivityProtocol;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.OpenFileTipDialog;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ReceiveNotificationDaiYueActivity   
 * @类描述：   公文通知待阅数据item数据管理类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-30 下午5:11:53  
 * @version    
 *
 */
public class ReceiveNotificationDaiYueActivity extends BaseActivity {

	private ImageButton backBtn;
	private TextView nevigaTitle;
	private LinearLayout rootView;
	private LoadingPager mLoadingPager;

	private ReceiveNotificationActivityProtocol mReceiveNotificationActivityProtocol = new ReceiveNotificationActivityProtocol();
	PolicyReceiveDetail mPolicyReceiveDetail;
	private String workItemId;//工作项的id
	private String processId;//流程id
	private String keyTitle="公文通知待阅详情";
	private boolean successViewFlag=false;
	
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
		workItemId = getIntent().getStringExtra("workItemId");
		processId = getIntent().getStringExtra("processId");
		super.onCreate(savedInstanceState);
		initLoadingPager();
	}

	@Override
	protected void initViews() {
		backBtn = (ImageButton) findViewById(R.id.back_activity);
		rootView = (LinearLayout) findViewById(R.id.load_view);
		nevigaTitle = (TextView) findViewById(R.id.nevigaTitle);
		nevigaTitle.setText("待阅详情");
	}

	@Override
	protected void initInitevnts() {
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//进行判断是否成功地打开过
				if(successViewFlag){
					//进行设置结束码,同时将参数返回去workItemId
					Intent data=new Intent();
					data.putExtra("workItemId",workItemId);
					data.putExtra("prcessId",processId);
					setResult(MobileOAConstant.NOTIFICATION_DAIYUE_RESULT_CODE, data);
				}
				finish();
			}
		});
	}

	private void initLoadingPager() {
		if (mLoadingPager == null) {
			mLoadingPager = new LoadingPager(UiUtils.getContext()) {

				@Override
				protected LoadResult load() {
					List<PolicyReceiveDetail> list = null;
					Message message = new Message();
					try {
						
						final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
						mUrlParamsEntity.setMethodName(MobileOAConstant.RECEIVE_NOTIFICATION_DATEIL);
						LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
						paramsMap.put("tranCode", "tran00016");
						paramsMap.put("userId",  MobileOaApplication.user.getUserId());
						paramsMap.put("processInstId", processId);
						paramsMap.put("workItemId", workItemId);
						mUrlParamsEntity.setParamsHashMap(paramsMap);
						mPolicyReceiveDetail= mReceiveNotificationActivityProtocol.loadDataFromWebService(mUrlParamsEntity);
//						String fileName=getFileName(new Random().nextInt(4));
//						mPolicyReceiveDetail = mReceiveNotificationActivityProtocol
//								.load(MobileOAConstant.CESHIURL_POLICY_RECE+File.separator+"policyReceDetail"+"?fileName="+fileName);
						list = new ArrayList<PolicyReceiveDetail>();
						list.add(mPolicyReceiveDetail);
					} catch (JsonParseException e) {
						message.what = MobileOAConstant.LOAD_DATA_ERROR;
						handler.sendMessage(message);
						e.printStackTrace();
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
					return checkData(list);
				}

				@Override
				public View createSuccessView() {
					FormHolder mFormHolder = new FormHolder(mPolicyReceiveDetail.getFileTypes(),ReceiveNotificationDaiYueActivity.this);
					mFormHolder.setData(mPolicyReceiveDetail);
					View contentView = mFormHolder.getContentView();
					successViewFlag=true;
					return contentView;
				}
				
				@Override
				protected LoadResult synchronizeUserData(){
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
						mPolicyReceiveDetail=mReceiveNotificationActivityProtocol.parseJson(mReceiveNotificationActivityProtocol.getSuccessSoapObject());
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

				public String getFileName(int i){
					switch (i) {
						case 0:
							return "policy_receive1.txt";
						case 1:
							return "policy_receive2.txt";
						case 2:
							return "policy_receive3.txt";
						case 3:
							return "policy_receive4.txt";
						default:
							break;
					}
					return "";
				}
			};
			
			mLoadingPager.setEmptyString(keyTitle+"为空!");
			mLoadingPager.setErrorString(keyTitle+"加载失败!");
			mLoadingPager.setLoadingString(keyTitle+"加载中...");
			mLoadingPager.show();
			mLoadingPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT ));
			rootView.addView(mLoadingPager);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==MobileOAConstant.LOAD_REQUEST_CODE && 
				resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			//进行处理请求码
			String loadState=data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if(MobileOAConstant.LOAD_SUCCESS.equals(loadState)){
				Toast.makeText(UiUtils.getContext(),"文件下载成功!", 0).show();
				String fileName=data.getStringExtra(MobileOAConstant.LOAD_CONTENT);
				OpenFileTipDialog.openFiles(fileName,UiUtils.getContext()); 
			}else{
				//加载的状态显示为失败
				Toast.makeText(UiUtils.getContext(),"文件下载失败请查看网络连接!", 0).show();
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(FileAppendPopup.getInstance().getPopupWindow()!=null &&
						FileAppendPopup.getInstance().getPopupWindow().isShowing()){
			FileAppendPopup.getInstance().getPopupWindow().dismiss();
			return true;
		}
		
		//进行判断是否成功地打开过
		if(successViewFlag){
			//进行设置结束码,同时将参数返回去workItemId
			Intent data=new Intent();
			data.putExtra("workItemId",workItemId);
			data.putExtra("prcessId",processId);
			setResult(MobileOAConstant.NOTIFICATION_DAIYUE_RESULT_CODE, data);
		}
		return super.onKeyDown(keyCode, event);
	}

}
