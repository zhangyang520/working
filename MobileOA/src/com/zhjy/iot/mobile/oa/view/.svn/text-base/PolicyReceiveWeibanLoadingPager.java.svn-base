package com.zhjy.iot.mobile.oa.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.activity.PolicyReceiveWeiBanActivity;
import com.zhjy.iot.mobile.oa.adapter.PolicyReceiveWeibanAdapter;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.PolicyReceDao;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.entity.ReceiveFileState;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.fragment.PolicyReceiveFragment;
import com.zhjy.iot.mobile.oa.manager.PolicyPagerManager;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.protocol.PolicyReceProtocol;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshBase;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshBase.OnRefreshListener;
import com.zhjy.iot.mobile.oa.refreshview.PullToRefreshListView;
import com.zhjy.iot.mobile.oa.utils.LogUtils;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月18日上午9:13:29
 * 
 * @描述  行政收文未办的loadingPager
 */
public class PolicyReceiveWeibanLoadingPager extends LoadingPager {
	
	private PullToRefreshListView mPullToRefreshListView;
	private PolicyReceiveWeibanAdapter mPolicyReceiveWeibanAdapter;
	private PolicyReceProtocol policyReceProtocol;
	private List<PolicyReceiveFile> datas=new ArrayList<PolicyReceiveFile>();//数据类
	private List<PolicyReceiveFile> arrayList=new ArrayList<PolicyReceiveFile>();//数据类
	int currentPage;
	private String weiBanType = "weiban";
	
	private static final String Tag=PolicyReceiveYibanLoadingPager.class.getName();
	private String keyName="行政未办收文数据";
	private String refershName="刷新首页失败,";
	private String loadmoreName="加载更多失败,";
	private String loadFirstPage="加载首页失败,";
	
	//activity定义
	private PolicyReceiveFragment activity;
	
	private int clickIndex;//点击的条目
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message message) {
			if (mPullToRefreshListView == null) {
				synchronized (getClass()) {
					if (mPullToRefreshListView == null) {
						mPullToRefreshListView = new PullToRefreshListView(UiUtils.getContext());
						mPullToRefreshListView.setRefreshTitle(keyName);
						mPolicyReceiveWeibanAdapter = new PolicyReceiveWeibanAdapter(datas, mPullToRefreshListView.getRefreshableView());
						mPullToRefreshListView.getmListView().setAdapter(mPolicyReceiveWeibanAdapter);
					}
				}
			}
			
			mPullToRefreshListView.getmListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					clickIndex=arg2;
					PolicyReceiveFile policyReceiveFile = datas.get(arg2);
					String s = policyReceiveFile.getWorkItemId();
					Intent mIntent = new Intent(UiUtils.getContext(),PolicyReceiveWeiBanActivity.class);
					mIntent.putExtra("policyReceiveFile", policyReceiveFile);
					mIntent.putExtra("weiban", weiBanType);
					activity.startActivityForResult(mIntent, MobileOAConstant.WEIBAN_ACTIVITY_REQUEST_CODE);
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
						LogUtils.e(Tag,"收文已办数据刷新失败");
						//进行刷新页面的结束
						mPullToRefreshListView.onPullDownRefreshComplete();
					    //UiUtils.showToast("收文已办数据刷新失败!");
					}
					break;
				case MobileOAConstant.LOAD_MORE:
					if((List<PolicyReceiveFile>)message.obj!=null && ((List<PolicyReceiveFile>)message.obj).size()>0){
						arrayList = (List<PolicyReceiveFile>)message.obj;
						PolicyReceiveFile policyReceiveFile = arrayList.get(0);
						if (!datas.contains(policyReceiveFile)) {
							PolicyReceDao.getInstance().addPolicyReceiveList((List<PolicyReceiveFile>)message.obj);
							currentPage=currentPage+1;
							//进行刷新首页,首先清除数据
							//				if(message.obj!=null){
							datas.addAll((List<PolicyReceiveFile>)message.obj);
							//				}
							//进行重新刷新
							//				mPolicyReceiveWeibanAdapter.setDatas(datas);
							mPolicyReceiveWeibanAdapter.notifyDataSetChanged();
							//进行加载更多结束
							mPullToRefreshListView.onPullUpRefreshComplete();
							UiUtils.showToast(keyName+"加载成功!");
						}else{
							mPullToRefreshListView.onPullUpRefreshComplete();
							LogUtils.e(Tag,"加载页面数据失败...");
						    UiUtils.showToast("没有更多的"+keyName);
						}
					}else{
						mPullToRefreshListView.onPullUpRefreshComplete();
						LogUtils.e(Tag,"加载页面数据失败...");
					    UiUtils.showToast("没有更多的"+keyName);
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
			PolicyReceDao.getInstance().clearPolicyReceiveList(ReceiveFileState.WEIBAN);
			PolicyReceDao.getInstance().addPolicyReceiveList((List<PolicyReceiveFile>)message.obj);
			 currentPage=0;
			//进行刷新首页,首先清除数据
			 datas.clear();
//					if(message.obj!=null){
			 datas.addAll((List<PolicyReceiveFile>)message.obj);
//					}
			//进行重新刷新
//			mPolicyReceiveYibanAdapter.setDatas(datas);
			 mPolicyReceiveWeibanAdapter.notifyDataSetChanged();
			//进行刷新页面的结束
			 mPullToRefreshListView.onPullDownRefreshComplete();
		}
	};
    
	int i=0;
	public PolicyReceiveWeibanLoadingPager(Context context) {
		this(context,null);
	}

	public PolicyReceiveWeibanLoadingPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setLoadingString(keyName+"加载中...");
		setErrorString(keyName+"加载失败.");
		setEmptyString(keyName+"为空.");
		policyReceProtocol = new PolicyReceProtocol();
	}

	public PolicyReceiveWeibanLoadingPager(Context context, AttributeSet attrs) {
		this(context, attrs,-1);
	}

	@Override
	public View createSuccessView() {
		/**进行初始化数据*/
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			/**进行下拉刷新**/
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				ThreadManager.getInstance().createLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						/**进行访问网络**/
						Message message = new Message();
						Bundle bundle = new Bundle();
						try {
							
							final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
							mUrlParamsEntity.setMethodName(MobileOAConstant.POLICY_RECEIVE_WEIBAN);
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							paramsMap.put("tranCode", "tran00004");
							paramsMap.put("userId", MobileOaApplication.user.getUserId());
							paramsMap.put("currentPage", "0");
							paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
							mUrlParamsEntity.setParamsHashMap(paramsMap);
							message.obj = policyReceProtocol.loadDataFromWebService(mUrlParamsEntity);
							
							System.out.println("mPullToRefreshListView policyReceProtocol.loadDataFromWebService(mUrlParamsEntity) = " + policyReceProtocol.loadDataFromWebService(mUrlParamsEntity).size());
							message.what = MobileOAConstant.REFRESH_PAGE;
							handler.sendMessage(message);
							
							
							/*//进行xUtils网络访问
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							final UrlParamsEntity entity = new UrlParamsEntity();
							entity.setParamsHashMap(paramsMap);
							entity.setUrl(MobileOAConstant.CESHIURL);
							message.obj = policyReceProtocol.load(entity.toString());
							message.what = MobileOAConstant.REFRESH_PAGE;
							handler.sendMessage(message);*/
						} catch (JsonParseException e) {
							e.printStackTrace();
						} catch (ContentException e) {
							e.printStackTrace();
							message.what=MobileOAConstant.ERROR_CODE;
							bundle.putInt(MobileOAConstant.ACTION_CODE, MobileOAConstant.REFRESH_PAGE);
							bundle.putString(MobileOAConstant.ERROR_CONTENT, refershName+e.getMessage());
							message.setData(bundle);
							handler.sendMessage(message);
						}
					}
				});
				
			}

			//进行上拉加载更多
			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				ThreadManager.getInstance().createLongPool().execute(new Runnable() {
					
					@Override
					public void run() {
						SystemClock.sleep(1000);
						//进行访问网络
						Message message=new Message();
						Bundle bundle=new Bundle();
						try {
							final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
							mUrlParamsEntity.setMethodName(MobileOAConstant.POLICY_RECEIVE_WEIBAN);
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							paramsMap.put("tranCode", "tran00004");
							paramsMap.put("userId", MobileOaApplication.user.getUserId());
							paramsMap.put("currentPage", (currentPage+1)+"");
							paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
							mUrlParamsEntity.setParamsHashMap(paramsMap);
							message.obj = policyReceProtocol.loadDataFromWebService(mUrlParamsEntity);
							
							System.out.println("onPullUpToRefresh policyReceProtocol.loadDataFromWebService(mUrlParamsEntity) = " + policyReceProtocol.loadDataFromWebService(mUrlParamsEntity).size());
							message.what = MobileOAConstant.LOAD_MORE;
							handler.sendMessage(message);
							
							
							/*LinkedHashMap<String, String> paramsMap=new LinkedHashMap<String, String>();
							final UrlParamsEntity entity=new UrlParamsEntity();
							entity.setParamsHashMap(paramsMap);
							entity.setUrl(MobileOAConstant.CESHIURL);
							message.obj=policyReceProtocol.load(entity.toString());
							message.what=MobileOAConstant.LOAD_MORE;
							handler.sendMessage(message);*/
						} catch (JsonParseException e) {
							e.printStackTrace();
						} catch (ContentException e) {
							e.printStackTrace();
							message.what=MobileOAConstant.ERROR_CODE;
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
	//加载的结果
	LoadResult loadResult;
	@SuppressWarnings("unchecked")
	@Override
	protected LoadResult load(){
		//进行通过协议类访问网络数据
		Message message=new Message();
		Bundle bundle=new Bundle();
		try {
			
			final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
			mUrlParamsEntity.setMethodName(MobileOAConstant.POLICY_RECEIVE_WEIBAN);
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("tranCode", "tran00004");
			paramsMap.put("userId",  MobileOaApplication.user.getUserId());
			paramsMap.put("currentPage", "0");
			paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
			mUrlParamsEntity.setParamsHashMap(paramsMap);
			message.obj = policyReceProtocol.loadDataFromWebService(mUrlParamsEntity);
			
			System.out.println("policyReceProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + policyReceProtocol.loadDataFromWebService(mUrlParamsEntity).size());
			message.what=MobileOAConstant.REQUEST_FIRST_PAGE;
			handler.sendMessage(message);
			
			/*message.obj=policyReceProtocol.load(MobileOAConstant.CESHIURL);
			message.what=MobileOAConstant.REQUEST_FIRST_PAGE;
			handler.sendMessage(message);*/
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
		System.out.println("isOverNetGapTimeisOverNetGapTime: System.currentTimeMillis():"+System.currentTimeMillis()+"..getNetworkTime:"+getNetworkTime());
		return System.currentTimeMillis()-getNetworkTime()>MobileOAConstant.NET_GAP_TIME;
	}
	
	/**
	 * 是否有已办的数据
	 * @return
	 */
	public boolean hasWeibanDatas(){
		List<PolicyReceiveFile> list=PolicyReceDao.getInstance().getPolicyReceiveList(ReceiveFileState.WEIBAN);
		if(list!=null){
			return list.size()>0;
		}
		return false;
	}

	public PolicyReceiveFragment getActivity() {
		return activity;
	}

	public void setActivity(PolicyReceiveFragment activity) {
		this.activity = activity;
	}

	/**
	 * 进行处理点击条目的index
	 */
	public void processClickIndex(){
		//进行获取对应的条目数据
		PolicyReceiveFile item=datas.get(clickIndex);
		try {
			item.setState(ReceiveFileState.YIBAN.getValue());
			//进行更新状态值
			PolicyReceDao.getInstance().updatePolicyReceiveList(item);
			datas.remove(clickIndex);
			mPolicyReceiveWeibanAdapter.setDatas(datas);
			mPolicyReceiveWeibanAdapter.notifyDataSetChanged();
			//进行增加到已办的条目中
			PolicyReceiveYibanLoadingPager loadingPager=//进行强行转换
					(PolicyReceiveYibanLoadingPager) PolicyPagerManager.getLoadingPager(1);
			loadingPager.processClickIndex(item);
			
			if(datas.size()==0){
				//需要进行重新请求首页的数据
				show();
			}
		} catch (ContentException e) {
			e.printStackTrace();
			item.setState(ReceiveFileState.WEIBAN.getValue());
		}
	}
}
