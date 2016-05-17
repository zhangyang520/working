package com.zhjy.iot.mobile.oa.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager.LoadResult;
import com.zhjy.iot.mobile.oa.adapter.OperationProcessAdapter;
import com.zhjy.iot.mobile.oa.entity.OperationProcess;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.protocol.OperationProcessProtocol;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshListView;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：OperationProcessActivity   
 * @类描述：   查看办理流程的管理类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-5 上午11:26:18  
 * @version    
 *
 */
public class OperationProcessActivity extends BaseActivity {

	private ImageButton backBtn;
	private TextView nevigaTitle;
	private LinearLayout rootView;
	private LoadingPager mLoadingPager;
	private PullToRefreshListView mPullToRefreshListView = new PullToRefreshListView(UiUtils.getContext());
	private ListView listView;
	List<OperationProcess> list = new ArrayList<OperationProcess>();
	OperationProcessAdapter mOperationProcessAdapter = null;
	OperationProcessProtocol operationProcessProtocol=new OperationProcessProtocol();
	
	private String keyName="查看操作流程,";
	private String processInstId;//流程实例id
	private String userId;//用户的Id
	private List<OperationProcess> datas;
	
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message message) {
			switch (message.what) {
			case MobileOAConstant.LOAD_DATA_FAIL:
				UiUtils.showToast(keyName+message.obj);
				break;
			case MobileOAConstant.LOAD_DATA_ERROR:
				UiUtils.showToast(keyName+message.obj);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout = R.layout.operation_process;
		super.onCreate(savedInstanceState);

		initLoadingPager();
	}

	@Override
	protected void initViews() {
		backBtn = (ImageButton) findViewById(R.id.back_activity);
		nevigaTitle = (TextView) findViewById(R.id.nevigaTitle);
		rootView = (LinearLayout) findViewById(R.id.look_handling_listview);
		nevigaTitle.setText("办理过程");
		
		//进行获取数据
		processInstId=getIntent().getStringExtra("processInstId");
		userId=getIntent().getStringExtra("userId");
	}

	@Override
	protected void initInitevnts() {
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initLoadingPager() {

		if (mLoadingPager == null) {
			mLoadingPager = new LoadingPager(UiUtils.getContext()) {
				@Override
				protected LoadResult load() {
					Message message = new Message();
					try {
						//进行设置urlParamsEntity参数
						UrlParamsEntity urlParamsEntity=new UrlParamsEntity();
						urlParamsEntity.setMethodName(MobileOAConstant.OPERATION_PROCESS);
						LinkedHashMap<String,String> linkedHashMap=new LinkedHashMap<String, String>();
						linkedHashMap.put("tranCode", "tran00019");
						linkedHashMap.put("processInstId", processInstId);
						linkedHashMap.put("userId", userId);
						urlParamsEntity.setParamsHashMap(linkedHashMap);
						//进行请求网络
						datas = operationProcessProtocol.loadDataFromWebService(urlParamsEntity);
					} catch (JsonParseException e) {
						message.what = MobileOAConstant.LOAD_DATA_ERROR;
						handler.sendMessage(message);
						e.printStackTrace();
					} catch (ContentException e) {
						e.printStackTrace();
						if(e.getErrorCode()==MobileOAConstant.NO_DATAS_ERROR_CODE){
							//进行同步数据
							return LoadResult.syschronize_data;
						}else{
							//发送消息
							message.what = MobileOAConstant.LOAD_DATA_FAIL;
							message.obj = e.getMessage();
							handler.sendMessage(message);
						}
					}
					return checkData(datas);
				}

				@Override
				public View createSuccessView() {
					/***因为在PullToRefreshListView中ListView里面已经封装了ListView的父亲，所以在LoadPager再添加View的时候就会出现两个父亲，故解决方法如下**/
					if (mOperationProcessAdapter == null) {
						mOperationProcessAdapter = new OperationProcessAdapter(
								datas, listView);
						listView = mPullToRefreshListView.getmListView();
						listView.setAdapter(mOperationProcessAdapter);
					} else {
						listView.setAdapter(mOperationProcessAdapter);
					}
					if(listView.getParent()!=null){
						((ViewGroup)listView.getParent()).removeView(listView);
					}
					return listView;
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
						datas=operationProcessProtocol.parseJson(operationProcessProtocol.getSuccessSoapObject());
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
			
			
			mLoadingPager.setEmptyString(keyName+"数据为空!");
			mLoadingPager.setErrorString(keyName+"加载数据失败!");
			mLoadingPager.setLoadingString(keyName+"数据加载中!");
			
			mLoadingPager.show();
			mLoadingPager.setLayoutParams(new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT));
			rootView.addView(mLoadingPager);
		}
	}
}
