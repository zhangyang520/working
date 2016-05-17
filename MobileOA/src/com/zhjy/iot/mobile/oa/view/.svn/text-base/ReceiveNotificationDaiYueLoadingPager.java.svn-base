package com.zhjy.iot.mobile.oa.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.activity.ReceiveNotificationDaiYueActivity;
import com.zhjy.iot.mobile.oa.adapter.ReceiveNotificationYiYueAdapter;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.PolicyReceDao;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.entity.ReceiveFileState;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.fragment.ReceiveNotificationFragment;
import com.zhjy.iot.mobile.oa.manager.ReceiveNotificationManager;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.protocol.ReceiveNotificationProtocol;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshBase;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshBase.OnRefreshListener;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshListView;
import com.zhjy.iot.mobile.oa.utils.LogUtils;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ReceiveNotificationYiBanLoadingPager   
 * @类描述：   公文通知-已办管理页
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-20 下午3:38:56  
 * @version    
 *
 */
public class ReceiveNotificationDaiYueLoadingPager extends LoadingPager {
	
	private PullToRefreshListView mPullToRefreshListView;
	private ReceiveNotificationProtocol receiveNotificationProtocol;
	private ReceiveNotificationYiYueAdapter mReceiveNotificationYiBanAdapter;
	
	private List<PolicyReceiveFile> datas=new ArrayList<PolicyReceiveFile>();//数据类
	private List<PolicyReceiveFile> arrayList =new ArrayList<PolicyReceiveFile>();//数据类
	
	private static final String Tag=ReceiveNotificationDaiYueLoadingPager.class.getName();
	
	private String keyName="待阅公文通知数据";
	private String loadFirstPage="加载首页失败,";
	private String refershName="刷新首页失败,";
	private String loadmoreName="加载更多失败,";
	int currentPage = 0;//当前页
	
	private ReceiveNotificationFragment activity;
	private int clickIndex;
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message message) {
			if (mPullToRefreshListView == null) {
				synchronized (getClass()) {
					if (mPullToRefreshListView == null) {
						mPullToRefreshListView = new PullToRefreshListView(UiUtils.getContext());
						mPullToRefreshListView.setRefreshTitle(keyName+",");
						mReceiveNotificationYiBanAdapter = new ReceiveNotificationYiYueAdapter(datas, mPullToRefreshListView.getRefreshableView());
						mPullToRefreshListView.getmListView().setAdapter(mReceiveNotificationYiBanAdapter);
					}
				}
			}
			
			mPullToRefreshListView.getmListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					clickIndex=arg2;
					PolicyReceiveFile policyReceiveFile = datas.get(arg2);
					String workItemId = policyReceiveFile.getWorkItemId();
					Intent mIntent = new Intent(UiUtils.getContext(),ReceiveNotificationDaiYueActivity.class);
					mIntent.putExtra("workItemId", workItemId);
					mIntent.putExtra("processId", policyReceiveFile.getProcessId());
					activity.startActivityForResult(mIntent,MobileOAConstant.NOTIFICATION_DAIYUE_REQUEST_CODE);
				}
			});
			//进行处理消息
			switch (message.what) {
				case MobileOAConstant.REFRESH_PAGE:
					//先进行已办数据的删除
					if ((List<PolicyReceiveFile>)message.obj!=null && ((List<PolicyReceiveFile>)message.obj).size()>0) {
						handleRefreshData(message);
						 UiUtils.showToast(keyName+"刷新成功!");
					}else{
//						LogUtils.e(Tag,"收文已办数据刷新失败");
						//进行刷新页面的结束
						mPullToRefreshListView.onPullDownRefreshComplete();
//					    UiUtils.showToast(keyName+"刷新失败!");
					}
					break;
				case MobileOAConstant.LOAD_MORE:
					if((List<PolicyReceiveFile>)message.obj!=null && ((List<PolicyReceiveFile>)message.obj).size()>0){
						arrayList = (List<PolicyReceiveFile>)message.obj;
						PolicyReceiveFile policyReceiveFile = arrayList.get(0);
						if (!datas.contains(policyReceiveFile)) {
							//TODO Auto-generated constructor stub
							PolicyReceDao.getInstance().addPolicyReceiveList((List<PolicyReceiveFile>)message.obj);
							currentPage=currentPage+1;
							//进行刷新首页,首先清除数据if(message.obj!=null){
							datas.addAll((List<PolicyReceiveFile>)message.obj);
							//进行重新刷新mPolicyReceiveWeibanAdapter.setDatas(datas);
							mReceiveNotificationYiBanAdapter.notifyDataSetChanged();
							//进行加载更多结束
							mPullToRefreshListView.onPullUpRefreshComplete();
							UiUtils.showToast(keyName+"加载成功!");
						}else{
							mPullToRefreshListView.onPullUpRefreshComplete();
							LogUtils.e(Tag,"加载页面数据失败...");
						    UiUtils.showToast("没有更多的"+keyName+"!");
						}
					}else{
						mPullToRefreshListView.onPullUpRefreshComplete();
						LogUtils.e(Tag,"加载页面数据失败...");
					    UiUtils.showToast("没有更多的"+keyName+"!");
					}
					break;
					
				case MobileOAConstant.ERROR_CODE://如果是错误码
					Bundle bundle=message.getData();
					 int action_code=bundle.getInt(MobileOAConstant.ACTION_CODE);
					 if(action_code==MobileOAConstant.REFRESH_PAGE){
						 //进行刷新页面的结束
						 mPullToRefreshListView.onPullDownRefreshComplete();
					 }else if(action_code==MobileOAConstant.LOAD_MORE){
						 //进行加载更多结束
						 mPullToRefreshListView.onPullUpRefreshComplete();
					 }else{
						 LogUtils.i(Tag, "handleMessage ERROR_CODE ELSE");
					 }
					 Toast.makeText(UiUtils.getContext(),keyName+","+bundle.getString(MobileOAConstant.ERROR_CONTENT),0).show();
					break;
	
				case MobileOAConstant.REQUEST_FIRST_PAGE://加载第一页的数据
					if((List<PolicyReceiveFile>)message.obj!=null && 
						((List<PolicyReceiveFile>)message.obj).size()>0){
						//处理数据
						handleRefreshData(message);
					}
					break;
			}
		}
		@SuppressWarnings("unchecked")
		private void handleRefreshData(Message message) {
			PolicyReceDao.getInstance().clearPolicyReceiveList(ReceiveFileState.DAIYUE);
			//TODO Auto-generated constructor stub
			PolicyReceDao.getInstance().addPolicyReceiveList((List<PolicyReceiveFile>)message.obj);
			 currentPage=0;
			//进行刷新首页,首先清除数据
			 datas.clear();
//					if(message.obj!=null){
			 datas.addAll((List<PolicyReceiveFile>)message.obj);
//					}
			//进行重新刷新
			 mReceiveNotificationYiBanAdapter.notifyDataSetChanged();
			//进行刷新页面的结束
			 mPullToRefreshListView.onPullDownRefreshComplete();
		}
	};
	
	int i=0;
	
	public ReceiveNotificationDaiYueLoadingPager(Context context) {
		this(context , null);
	}

	public ReceiveNotificationDaiYueLoadingPager(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		setLoadingString(keyName+"加载中...");
		setErrorString(keyName+"加载失败");
		setEmptyString(keyName+"为空.");
		receiveNotificationProtocol = new ReceiveNotificationProtocol();
	}
	
	public ReceiveNotificationDaiYueLoadingPager(Context context, AttributeSet attrs) {
		this(context, attrs,-1);
	}

	@Override
	public View createSuccessView() {
		
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				ThreadManager.getInstance().createLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						Message message = new Message();
						Bundle bundle = new Bundle();
						try {
							
							final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
							mUrlParamsEntity.setMethodName(MobileOAConstant.RECEIVE_NOTIFICATION_DAIYUE);
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							paramsMap.put("tranCode", "tran00015");
							paramsMap.put("userId", MobileOaApplication.user.getUserId());
							paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
							paramsMap.put("currentPage", "0");
							mUrlParamsEntity.setParamsHashMap(paramsMap);
							message.obj = receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity);
							
							System.out.println("mPullToRefreshListView receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity) = " + receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity).size());
							message.what = MobileOAConstant.REFRESH_PAGE;
							handler.sendMessage(message);
							

						} catch (JsonParseException e) {
							e.printStackTrace();
						} catch (ContentException e) {
							e.printStackTrace();
							message.what = MobileOAConstant.ERROR_CODE;
							bundle.putInt(MobileOAConstant.ACTION_CODE, MobileOAConstant.REFRESH_PAGE);
							bundle.putString(MobileOAConstant.ERROR_CONTENT, refershName+e.getMessage());
							message.setData(bundle);
							handler.sendMessage(message);
						}
					}
				});
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				ThreadManager.getInstance().createLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						Message message = new Message();
						Bundle bundle = new Bundle();
						try {
							final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
							mUrlParamsEntity.setMethodName(MobileOAConstant.RECEIVE_NOTIFICATION_DAIYUE);
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							paramsMap.put("tranCode", "tran00015");
							paramsMap.put("userId", MobileOaApplication.user.getUserId());
							paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
							paramsMap.put("currentPage", (currentPage+1)+"");
							mUrlParamsEntity.setParamsHashMap(paramsMap);
							message.obj = receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity);
							
							System.out.println("onPullUpToRefresh receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity) = " + receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity).size());
							message.what = MobileOAConstant.LOAD_MORE;
							handler.sendMessage(message);
							
						} catch (JsonParseException e) {
							e.printStackTrace();
						} catch (ContentException e) {
							e.printStackTrace();
							message.what = MobileOAConstant.ERROR_CODE;
							bundle.putInt(MobileOAConstant.ACTION_CODE, MobileOAConstant.LOAD_MORE);
							bundle.putString(MobileOAConstant.ERROR_CONTENT, loadmoreName+e.getMessage());
							message.setData(bundle);
							handler.sendMessage(message);
						}
					}
				});
				
			}
		});
		return mPullToRefreshListView;
	}


	LoadResult loadResult;

	@SuppressWarnings("unchecked")
	@Override
	protected LoadResult load() {
		//进行通过协议类访问网络数据
		Message message=new Message();
		Bundle bundle=new Bundle();
		try {
			
			
			final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
			mUrlParamsEntity.setMethodName(MobileOAConstant.RECEIVE_NOTIFICATION_DAIYUE);
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("tranCode", "tran00015");
			paramsMap.put("userId",  MobileOaApplication.user.getUserId());
			paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
			paramsMap.put("currentPage", "0");
			mUrlParamsEntity.setParamsHashMap(paramsMap);
			message.obj = receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity);
			
//			System.out.println("receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + receiveNotificationProtocol.loadDataFromWebService(mUrlParamsEntity).size());
			message.what=MobileOAConstant.REQUEST_FIRST_PAGE;
			handler.sendMessage(message);
			return  checkData((List<PolicyReceiveFile>)message.obj);
		} catch(JsonParseException e) {
			e.printStackTrace();
			return  checkData(null);
		} catch(ContentException e){
			e.printStackTrace();
			message.what=MobileOAConstant.ERROR_CODE;
			bundle.putString(MobileOAConstant.ERROR_CONTENT, loadFirstPage+e.getMessage());
			message.setData(bundle);
			handler.sendMessage(message);
			return  checkData(null);
		}
	}
	/**
	 * 是否超过网络的间隔时间
	 * @return
	 */
	public boolean isOverNetGapTime(){
		return System.currentTimeMillis()-getNetworkTime()>MobileOAConstant.NET_GAP_TIME;
	}
	
	
	/**
	 * 是否有已办的数据
	 * @return
	 */
	public boolean hasYiYueDatas(){
		List<PolicyReceiveFile> list=PolicyReceDao.getInstance().getPolicyReceiveList(ReceiveFileState.DAIYUE);
		if(list!=null){
			return list.size()>0;
		}
		return false;
	}

	public ReceiveNotificationFragment getActivity() {
		return activity;
	}

	public void setActivity(ReceiveNotificationFragment activity) {
		this.activity = activity;
	}
	
	/**
	 * 进行处理点击成功“公文通知”中的待阅的条目的id
	 */
	public void processClickIndex(){
		//进行更新数据库中对应的条目
		PolicyReceiveFile item=datas.get(clickIndex);
		try {
			item.setState(ReceiveFileState.YIYUE.getValue());
			PolicyReceDao.getInstance().updatePolicyReceiveList(item);
			datas.remove(clickIndex);
			mReceiveNotificationYiBanAdapter.setDatas(datas);
			mReceiveNotificationYiBanAdapter.notifyDataSetChanged();
			//进行将条目增加到已阅条目的集合中！
			ReceiveNotificationYiYueLoadingPager loadingPager = //
					(ReceiveNotificationYiYueLoadingPager)ReceiveNotificationManager.getLoadingPager(1);
			loadingPager.processClickIndex(item);
		} catch (ContentException e) {
			item.setState(ReceiveFileState.DAIYUE.getValue());
			e.printStackTrace();
		}
	}
}
