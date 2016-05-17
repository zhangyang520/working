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

import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.activity.FileTransYifaActivity;
import com.zhjy.iot.mobile.oa.adapter.FileTransYifaAdapter;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.FileTransDao;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.entity.FileTransactionState;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.holder.FileTransImportantHolder;
import com.zhjy.iot.mobile.oa.holder.FileTransYifaHolder;
import com.zhjy.iot.mobile.oa.holder.FileTransYishouHolder;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.protocol.FileTransProtocol;
import com.zhjy.iot.mobile.oa.protocol.FileTransYiFaProtocol;
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
 * @日期 2016-3-18下午2:07:24
 * 
 * @描述 文件传阅已发文件
 */
public class FileTransYifaLoadingPager extends LoadingPager {
	
	private PullToRefreshListView mPullToRefreshListView;
	private FileTransYiFaProtocol fileTransYiFaProtocol;
	private FileTransYifaAdapter mFileTransYifaAdapter;
	private List<FileTransaction> datas=new ArrayList<FileTransaction>();//数据类
	private List<FileTransaction> arrayList=new ArrayList<FileTransaction>();//数据类
	
	int currentPage = 0;
	private static final String Tag=FileTransYifaLoadingPager.class.getName();
	private String KEY_NAME="已发文件数据";
	private String refershName="刷新首页失败,";
	private String loadmoreName="加载更多失败,";
	private String loadFirstPage="加载首页失败,";
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message message) {
			if (mPullToRefreshListView == null) {
				synchronized (getClass()) {
					if (mPullToRefreshListView == null) {
						mPullToRefreshListView = new PullToRefreshListView(UiUtils.getContext());
						mPullToRefreshListView.setRefreshTitle("已发文件传阅,");
						mFileTransYifaAdapter = new FileTransYifaAdapter(datas, mPullToRefreshListView.getRefreshableView());
						mPullToRefreshListView.getmListView().setAdapter(mFileTransYifaAdapter);
						mPullToRefreshListView.getmListView().setOnItemClickListener(new OnItemClickListener() {
							
							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int arg2, long arg3) {
								Intent mIntent = new Intent(UiUtils.getContext(),FileTransYifaActivity.class);
								mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								//进行传递文档id
								mIntent.putExtra("docmentId", datas.get(arg2).getDocumentId());
								UiUtils.getContext().startActivity(mIntent);
							}
						});
					}
				}
			}
			
			//进行处理消息
			switch (message.what) {
			case MobileOAConstant.REFRESH_PAGE:
				//先进行已办数据的删除
				if ((List<FileTransaction>)message.obj!=null && ((List<FileTransaction>)message.obj).size()>0) {
					handleRefreshData(message);
					 UiUtils.showToast(KEY_NAME+"刷新成功!");
				}else if((List<FileTransaction>)message.obj!=null){
					LogUtils.e(Tag,"收文已办数据刷新失败");
					handleRefreshData(message);
					//进行刷新页面的结束
					mPullToRefreshListView.onPullDownRefreshComplete();
//				    UiUtils.showToast(KEY_NAME+"刷新失败!");
				}
				break;
			case MobileOAConstant.LOAD_MORE:
				try {
					if((List<FileTransaction>)message.obj!=null && ((List<FileTransaction>)message.obj).size()>0){
						
						arrayList = (List<FileTransaction>)message.obj;
						FileTransaction fileTransaction = arrayList.get(0);
						if(!datas.contains(fileTransaction)){
							FileTransDao.getInstance().addFileTransactionList((List<FileTransaction>)message.obj);
							currentPage=currentPage+1;
							//进行刷新首页,首先清除数据
							datas.addAll((List<FileTransaction>)message.obj);
							//进行重新刷新
							mFileTransYifaAdapter.notifyDataSetChanged();
							//进行加载更多结束
							mPullToRefreshListView.onPullUpRefreshComplete();
							UiUtils.showToast(KEY_NAME+"加载成功!");
						}else{
							UiUtils.showToast("没有更多的"+KEY_NAME);	
							mPullToRefreshListView.onPullUpRefreshComplete();
						}
					}else{
						mPullToRefreshListView.onPullUpRefreshComplete();
						LogUtils.e(Tag,"加载页面数据失败...");
					    UiUtils.showToast("没有更多的"+KEY_NAME);
					}
				} catch (ContentException e) {
					e.printStackTrace();
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
				 Toast.makeText(UiUtils.getContext(),KEY_NAME+","+bundle.getString(MobileOAConstant.ERROR_CONTENT),0).show();
				break;

			case MobileOAConstant.REQUEST_FIRST_PAGE://加载第一页的数据
				if(((List<FileTransaction>) message.obj).size() > 0){
					handleRefreshData(message);
				}else{
					try {
						 FileTransDao.getInstance().clearFileTransactionList(FileTransactionState.YIFA);
						 System.out.println("ImportantLoadingPager REFRESH_PAGE: size:"+FileTransDao.getInstance().getFileTransactionList(FileTransactionState.INPORTANT).size());
						 currentPage=1;
						 //进行刷新首页,首先清除数据
						 datas.clear();
						 mFileTransYifaAdapter.setDatas(datas);
						 //进行重新刷新
						 mFileTransYifaAdapter.notifyDataSetChanged();
						 //进行清除数据库
						 mPullToRefreshListView.onPullDownRefreshComplete();
					} catch (ContentException e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}
		@SuppressWarnings("unchecked")
		private void handleRefreshData(Message message) {
			try {
				FileTransDao.getInstance().clearFileTransactionList(FileTransactionState.YIFA);
				FileTransDao.getInstance().addFileTransactionList((List<FileTransaction>)message.obj);
				 currentPage=0;
				//进行刷新首页,首先清除数据
				 datas.clear();
//					if(message.obj!=null){
				 datas.addAll((List<FileTransaction>)message.obj);
//					}
				//进行重新刷新
//			mPolicyReceiveYibanAdapter.setDatas(datas);
				 mFileTransYifaAdapter.notifyDataSetChanged();
				//进行刷新页面的结束
				 mPullToRefreshListView.onPullDownRefreshComplete();
			} catch (ContentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	

	public FileTransYifaLoadingPager(Context context) {
		this(context,null);
	}

	
	public FileTransYifaLoadingPager(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setLoadingString(KEY_NAME+"加载中...");
		setErrorString(KEY_NAME+"加载失败");
		setEmptyString(KEY_NAME+"为空.");
		//fileTransProtocol = new FileTransProtocol();
		fileTransYiFaProtocol = new FileTransYiFaProtocol();
	}


	public FileTransYifaLoadingPager(Context context, AttributeSet attrs) {
		this(context, attrs,-1);
		// TODO Auto-generated constructor stub
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
							mUrlParamsEntity.setMethodName(MobileOAConstant.QUERYPUBLISHEDDOC);
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							paramsMap.put("tranCode", "tran00009");
							paramsMap.put("currentPage", "0");
							paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
							System.out.println("MobileOaApplication.user.getUserId():"+MobileOaApplication.user.getUserId());
							paramsMap.put("userId", MobileOaApplication.user.getUserId());
							mUrlParamsEntity.setParamsHashMap(paramsMap);
							message.obj = fileTransYiFaProtocol.loadDataFromWebService(mUrlParamsEntity);
							
							System.out.println("fileTransProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + fileTransYiFaProtocol.loadDataFromWebService(mUrlParamsEntity).size());
							message.what=MobileOAConstant.REFRESH_PAGE;
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
							mUrlParamsEntity.setMethodName(MobileOAConstant.QUERYPUBLISHEDDOC);
							LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
							paramsMap.put("tranCode", "tran00009");
							paramsMap.put("currentPage", (currentPage+1)+"");
							paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
							paramsMap.put("userId", MobileOaApplication.user.getUserId());
							System.out.println("MobileOaApplication.user.getUserId():"+MobileOaApplication.user.getUserId());
							mUrlParamsEntity.setParamsHashMap(paramsMap);
							message.obj = fileTransYiFaProtocol.loadDataFromWebService(mUrlParamsEntity);
							
							System.out.println("fileTransProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + fileTransYiFaProtocol.loadDataFromWebService(mUrlParamsEntity).size());
							message.what=MobileOAConstant.LOAD_MORE;
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
			mUrlParamsEntity.setMethodName(MobileOAConstant.QUERYPUBLISHEDDOC);
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("tranCode", "tran00009");
			paramsMap.put("currentPage", "0");
			paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
			paramsMap.put("userId", MobileOaApplication.user.getUserId());
			System.out.println("MobileOaApplication.user.getUserId():"+MobileOaApplication.user.getUserId());
			mUrlParamsEntity.setParamsHashMap(paramsMap);
			message.obj = fileTransYiFaProtocol.loadDataFromWebService(mUrlParamsEntity);
			
			System.out.println("fileTransProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" +// 
														fileTransYiFaProtocol.loadDataFromWebService(mUrlParamsEntity).size());
			message.what=MobileOAConstant.REQUEST_FIRST_PAGE;
			handler.sendMessage(message);
			return  checkData((List<FileTransaction>)message.obj);
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
		System.out.println("isOverNetGapTimeisOverNetGapTime: System.currentTimeMillis():"+System.currentTimeMillis()+"..getNetworkTime:"+getNetworkTime()+"...gap:"+(System.currentTimeMillis()-getNetworkTime()));
		return System.currentTimeMillis()-getNetworkTime()>MobileOAConstant.NET_GAP_TIME;
	}
	
	/**
	 * 是否有已办的数据
	 * @return
	 */
	public boolean hasYiFaDatas(){
		try {
			List<FileTransaction> list=FileTransDao.getInstance().getFileTransactionList(FileTransactionState.YIFA);
			if(list!=null){
				return list.size()>0;
			}
			return false;
		} catch (ContentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public int getClickItemIndex(){
		return ((FileTransYifaHolder)mFileTransYifaAdapter.getHolder()).clickIndex;
	}
	
	
	public void processClickIndex(){
		//进行数据库的删除
		int index=((FileTransYifaHolder)mFileTransYifaAdapter.getHolder()).clickIndex;
		try {
			System.out.println("before FileTransYifa datas size:"+datas.size()+"....toString:"+datas.toString());
			FileTransDao.getInstance().deleteFileTransactionById(datas.get(index).getDocumentId());
			datas.remove(index);
			mFileTransYifaAdapter.notifyDataSetChanged();
			
			if(datas.size()==0){
				//重新展示页面
				show();
			}
		} catch (ContentException e) {
			System.out.println("processClickIndex failure");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 进行设置数据的更新
	 */
	public void setDataSetChange(){
		try {
			datas=FileTransDao.getInstance().getFileTransactionList(FileTransactionState.YIFA);
			mFileTransYifaAdapter.setDatas(datas);
			mFileTransYifaAdapter.notifyDataSetChanged();
		} catch (ContentException e) {
			e.printStackTrace();
		}
	}
	
	
}
