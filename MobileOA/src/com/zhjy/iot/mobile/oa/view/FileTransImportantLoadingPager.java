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
import com.zhjy.iot.mobile.oa.activity.FileTransImportantActivity;
import com.zhjy.iot.mobile.oa.adapter.FileTransImportantAdapter;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.FileTransDao;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.entity.FileTransactionState;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.holder.FileTransImportantHolder;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.protocol.FileTransProtocol;
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
 * @描述 文件传阅重要文件
 */
public class FileTransImportantLoadingPager extends LoadingPager {

	private PullToRefreshListView mPullToRefreshListView;
	private FileTransProtocol fileTransProtocol;
	private FileTransImportantAdapter mFileTransImportantAdapter;
	private List<FileTransaction> datas = new ArrayList<FileTransaction>();// 数据类
	
	private List<FileTransaction> arrayList=new ArrayList<FileTransaction>();//数据类
	
	private String keyName = "已收重要文件数据";
	int currentPage = 0;
	private String refershName="刷新首页失败,";
	private String loadmoreName="加载更多失败,";
	private String loadFirstPage="加载首页失败,";
	
	private static final String Tag = FileTransImportantLoadingPager.class
			.getName();

	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message message) {
			if (mPullToRefreshListView == null) {
				synchronized (getClass()) {
					if (mPullToRefreshListView == null) {
						mPullToRefreshListView = new PullToRefreshListView(
								UiUtils.getContext());
						mPullToRefreshListView.setRefreshTitle(keyName+",");
						mFileTransImportantAdapter = new FileTransImportantAdapter(
								datas,
								mPullToRefreshListView.getRefreshableView());
						mPullToRefreshListView.getmListView().setAdapter(
								mFileTransImportantAdapter);
						mPullToRefreshListView.getmListView()//
									.setOnItemClickListener(
											new OnItemClickListener() {
	
												@Override
												public void onItemClick(
														AdapterView<?> arg0,
														View arg1, int arg2,
														long arg3) {
													Intent mIntent = new Intent(UiUtils.getContext(),//
																				FileTransImportantActivity.class);
													//进行传递文档id
													mIntent.putExtra("docmentId", datas.get(arg2).getDocumentId());
													mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
													UiUtils.getContext().startActivity(mIntent);
												}
											});
					}
				}
			}
			// 进行处理消息
			switch (message.what) {
				case MobileOAConstant.REFRESH_PAGE:
					// 先进行已办数据的删除
					if ((List<FileTransaction>) message.obj != null) {
						
						if(((List<FileTransaction>) message.obj).size() > 0){
							handleRefreshData(message);
							UiUtils.showToast(keyName+"刷新成功!");
						}else{
							 try {
									 FileTransDao.getInstance().clearFileTransactionList(FileTransactionState.INPORTANT);
									 System.out.println("ImportantLoadingPager REFRESH_PAGE: size:"+FileTransDao.getInstance().getFileTransactionList(FileTransactionState.INPORTANT).size());
									 currentPage=1;
									 //进行刷新首页,首先清除数据
									 datas.clear();
									 mFileTransImportantAdapter.setDatas(datas);
									 //进行重新刷新
									 mFileTransImportantAdapter.notifyDataSetChanged();
									 //进行清除数据库
									 mPullToRefreshListView.onPullDownRefreshComplete();
								} catch (ContentException e) {
									e.printStackTrace();
								}
						}
					}else if((List<FileTransaction>) message.obj != null){
						//LogUtils.e(Tag, keyName+"收文已办数据刷新失败");
						// 进行刷新页面的结束
						handleRefreshData(message);
						mPullToRefreshListView.onPullDownRefreshComplete();
						//UiUtils.showToast("收文已办数据刷新失败!");
					}
					break;
					
			case MobileOAConstant.LOAD_MORE:
					try {
						if ((List<FileTransaction>) message.obj != null
								&& ((List<FileTransaction>) message.obj).size() > 0) {
							
							arrayList = (List<FileTransaction>)message.obj;
							FileTransaction fileTransaction = arrayList.get(0);
							if(!datas.contains(fileTransaction)){
								FileTransDao.getInstance().addFileTransactionList((List<FileTransaction>)message.obj);
								currentPage=currentPage+1;
								//进行刷新首页,首先清除数据
								datas.addAll((List<FileTransaction>)message.obj);
								//进行重新刷新
								mFileTransImportantAdapter.notifyDataSetChanged();
								//进行加载更多结束
								mPullToRefreshListView.onPullUpRefreshComplete();
								UiUtils.showToast(keyName+"加载成功!");
							}else{
								UiUtils.showToast("没有更多的"+keyName);	
								mPullToRefreshListView.onPullUpRefreshComplete();
							}
						} else {
							mPullToRefreshListView.onPullUpRefreshComplete();
							LogUtils.e(Tag, "加载页面数据失败...");
							UiUtils.showToast("没有更多"+keyName);
						}
					} catch (ContentException e) {
						e.printStackTrace();
					}
					break;
			case MobileOAConstant.ERROR_CODE:// 如果是错误码
					Bundle bundle = message.getData();
					int action_code = bundle.getInt(MobileOAConstant.ACTION_CODE);
					if (action_code == MobileOAConstant.REFRESH_PAGE) {
						// 进行刷新页面的结束
						mPullToRefreshListView.onPullDownRefreshComplete();
					} else if (action_code == MobileOAConstant.LOAD_MORE) {
						// 进行加载更多结束
						mPullToRefreshListView.onPullUpRefreshComplete();
					} else {
						LogUtils.i(Tag, "handleMessage ERROR_CODE ELSE");
					}
					Toast.makeText(UiUtils.getContext(),//
							keyName+","+bundle.getString(MobileOAConstant.ERROR_CONTENT), 0).show();
				break;

			case MobileOAConstant.REQUEST_FIRST_PAGE:// 加载第一页的数据
					if ((List<FileTransaction>) message.obj != null) {
						// 处理数据
						if(((List<FileTransaction>) message.obj).size() > 0){
							handleRefreshData(message);
						}else{
							try {
								 FileTransDao.getInstance().clearFileTransactionList(FileTransactionState.INPORTANT);
								 System.out.println("ImportantLoadingPager REFRESH_PAGE: size:"+FileTransDao.getInstance().getFileTransactionList(FileTransactionState.INPORTANT).size());
								 currentPage=0;
								 //进行刷新首页,首先清除数据
								 datas.clear();
								 mFileTransImportantAdapter.setDatas(datas);
								 //进行重新刷新
								 mFileTransImportantAdapter.notifyDataSetChanged();
								 //进行清除数据库
								 mPullToRefreshListView.onPullDownRefreshComplete();
							} catch (ContentException e) {
								e.printStackTrace();
							}
						}
					}
				break;
			}
		}

		@SuppressWarnings("unchecked")
		private void handleRefreshData(Message message) {
			try {
				FileTransDao.getInstance().clearFileTransactionList(
						FileTransactionState.INPORTANT);
				FileTransDao.getInstance().addFileTransactionList(
						(List<FileTransaction>) message.obj);
				currentPage = 0;
				// 进行刷新首页,首先清除数据
				datas.clear();
				// if(message.obj!=null){
				datas.addAll((List<FileTransaction>) message.obj);
				// }
				// 进行重新刷新
				// mPolicyReceiveYibanAdapter.setDatas(datas);
				mFileTransImportantAdapter.notifyDataSetChanged();
				// 进行刷新页面的结束
				mPullToRefreshListView.onPullDownRefreshComplete();
			} catch (ContentException e) {
				e.printStackTrace();
			}
		}
	};

	public FileTransImportantLoadingPager(Context context) {
		this(context, null);
	}

	public FileTransImportantLoadingPager(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		setLoadingString(keyName+"加载中...");
		setErrorString(keyName+"加载失败.");
		setEmptyString(keyName+"为空.");
		fileTransProtocol = new FileTransProtocol();
	}

	public FileTransImportantLoadingPager(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	@Override
	public View createSuccessView() {
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						ThreadManager.getInstance().createLongPool()
								.execute(new Runnable() {

									@Override
									public void run() {
										Message message = new Message();
										Bundle bundle = new Bundle();
										try {
											
											final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
											mUrlParamsEntity.setMethodName(MobileOAConstant.QUERYIMPORTENTDOC);
											LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
											paramsMap.put("tranCode", "tran00010");
											paramsMap.put("currentPage", "0");
											paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
											paramsMap.put("userId", MobileOaApplication.user.getUserId());
											System.out.println("MobileOaApplication.user.getUserId():"+MobileOaApplication.user.getUserId());
											mUrlParamsEntity.setParamsHashMap(paramsMap);
											message.obj = fileTransProtocol.loadDataFromWebService(mUrlParamsEntity);
											
											System.out.println("fileTransProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + fileTransProtocol.loadDataFromWebService(mUrlParamsEntity).size());
											message.what=MobileOAConstant.REFRESH_PAGE;
											handler.sendMessage(message);
										} catch (JsonParseException e) {
											e.printStackTrace();
										} catch (ContentException e) {
											e.printStackTrace();
											message.what = MobileOAConstant.ERROR_CODE;
											bundle.putInt(
													MobileOAConstant.ACTION_CODE,
													MobileOAConstant.REFRESH_PAGE);
											bundle.putString(
													MobileOAConstant.ERROR_CONTENT,
													refershName+e.getMessage());
											message.setData(bundle);
											handler.sendMessage(message);
										}
									}
								});

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						ThreadManager.getInstance().createLongPool()
								.execute(new Runnable() {
									@Override
									public void run() {
										Message message = new Message();
										Bundle bundle = new Bundle();
										try {
											
											final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
											mUrlParamsEntity.setMethodName(MobileOAConstant.QUERYIMPORTENTDOC);
											LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
											paramsMap.put("tranCode", "tran00010");
											paramsMap.put("currentPage", (currentPage+1)+"");
											paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
											paramsMap.put("userId", MobileOaApplication.user.getUserId());
											System.out.println("MobileOaApplication.user.getUserId():"+MobileOaApplication.user.getUserId());
											mUrlParamsEntity.setParamsHashMap(paramsMap);
											message.obj = fileTransProtocol.loadDataFromWebService(mUrlParamsEntity);
											
											System.out.println("fileTransProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + fileTransProtocol.loadDataFromWebService(mUrlParamsEntity).size());
											message.what=MobileOAConstant.LOAD_MORE;
											handler.sendMessage(message);
										} catch (JsonParseException e) {
											e.printStackTrace();
										} catch (ContentException e) {
											e.printStackTrace();
											message.what = MobileOAConstant.ERROR_CODE;
											bundle.putInt(
													MobileOAConstant.ACTION_CODE,
													MobileOAConstant.LOAD_MORE);
											bundle.putString(
													MobileOAConstant.ERROR_CONTENT,
													loadmoreName+e.getMessage());
											message.setData(bundle);
											handler.sendMessage(message);
										}
									}
								});
					}
				});
		return mPullToRefreshListView;
	}

	int i = 0;
	LoadResult loadResult;

	@SuppressWarnings("unchecked")
	@Override
	protected LoadResult load() {
		// 进行通过协议类访问网络数据
		Message message = new Message();
		Bundle bundle = new Bundle();
		try {
			
			final UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
			mUrlParamsEntity.setMethodName(MobileOAConstant.QUERYIMPORTENTDOC);
			LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
			paramsMap.put("tranCode", "tran00010");
			paramsMap.put("currentPage", "0");
			paramsMap.put("pageSize", MobileOAConstant.PAGE_SIZE+"");
			System.out.println("MobileOaApplication.user.getUserId():"+MobileOaApplication.user.getUserId());
			paramsMap.put("userId", MobileOaApplication.user.getUserId());
			mUrlParamsEntity.setParamsHashMap(paramsMap);
			message.obj = fileTransProtocol.loadDataFromWebService(mUrlParamsEntity);
			
			System.out.println("fileTransProtocol.loadDataFromWebService(mUrlParamsEntity) datas size ::" + fileTransProtocol.loadDataFromWebService(mUrlParamsEntity).size());
			message.what=MobileOAConstant.REQUEST_FIRST_PAGE;
			handler.sendMessage(message);
			return checkData((List<FileTransaction>) message.obj);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return checkData(null);
		} catch (ContentException e) {
			e.printStackTrace();
			message.what = MobileOAConstant.ERROR_CODE;
			bundle.putString(MobileOAConstant.ERROR_CONTENT, loadFirstPage+e.getMessage());
			message.setData(bundle);
			handler.sendMessage(message);
			return checkData(null);
		}
	}

	/**
	 * 是否超过网络的间隔时间
	 * 
	 * @return
	 */
	public boolean isOverNetGapTime() {
		System.out.println("isOverNetGapTimeisOverNetGapTime: System.currentTimeMillis():"+System.currentTimeMillis()+"..getNetworkTime:"+getNetworkTime()+"...gap:"+(System.currentTimeMillis()-getNetworkTime()));
		return System.currentTimeMillis() - getNetworkTime() > MobileOAConstant.NET_GAP_TIME;
	}

	/**
	 * 是否有已办的数据
	 * 
	 * @return
	 */
	public boolean hasImportantDatas() {
		try {
			List<FileTransaction> list = FileTransDao.getInstance().getFileTransactionList(FileTransactionState.INPORTANT);
			if (list != null) {
				System.out.println("list.size() > 0 = " + (list.size()));
				return list.size() > 0;
			}
			return false;
		} catch (ContentException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public int getClickItemIndex(){
		return ((FileTransImportantHolder)mFileTransImportantAdapter.getHolder()).clickIndex;
	}
	
	public void processClickIndex(){
		//进行数据库中的更新
		int index=((FileTransImportantHolder)mFileTransImportantAdapter.getHolder()).clickIndex;
		try {
			datas.get(index).setImportance(FileTransactionState.ORDINARY.getValue());
			FileTransDao.getInstance().updateFileTransactionState(datas.get(index));
			datas.remove(index);
			mFileTransImportantAdapter.notifyDataSetChanged();
			if(datas.size()==0){
				//重新展示页面
				show();
			}
		} catch (ContentException e) {
			System.out.println("processClickIndex failure");
			// TODO Auto-generated catch block
			e.printStackTrace();
			datas.get(index).setImportance(FileTransactionState.INPORTANT.getValue());
		}
	}
	
	
	/**
	 * 进行设置数据的更新
	 */
	public void setDataSetChange(){
		System.out.println("FileTransImportantLoadingPager setDataSetChange......");
		try {
			datas=FileTransDao.getInstance().getFileTransactionList(FileTransactionState.INPORTANT);
			System.out.println("FileTransImportantLoadingPager setDataSetChange...... size:"+datas.size());
			mFileTransImportantAdapter.setDatas(datas);
			mFileTransImportantAdapter.notifyDataSetChanged();
		} catch (ContentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
