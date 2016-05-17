package com.zhjy.iot.mobile.oa.Inner;

import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.utils.UiUtils;

/**
 * 正在加载中的页面
 * @author zhangyang
 * 
 * 过程:
 *    1:先进行设置页面
 */
public abstract class LoadingPager extends FrameLayout{
	
	//定义java变量
	private final int STATE_UNKOWN=-1;//未知状态
	private final int STATE_LOADING=0;//正在加载中状态
	private final int STATE_EMPTY=1;//空状态
	private final int STATE_ERROR=2;//加载失败状态
	private final int STATE_SUCCESS=3;//加载成功状态
	private final int STATE_NEED_SYNCHRONIZE_DATA=4;
	//初始化设置状态值
	private int state=STATE_UNKOWN;
	
	private View loadingView;// 加载中的界面S
	private View errorView;// 错误界面
	private View emptyView;// 空界面
	private View successView;//加载成功的界面
	private View synchronizedDataView; //同步用户数据
	
	private String errorString; //提示错误的字符串
	private String loadingString;//正在加载的字符串
	private String emptyString;//加载空页面的提示数据
	
	//加载的状态枚举值
	private LoadStatus loadStatus=LoadStatus.hasComplete;
	//访问网络的时间:
	private long networkTime;
	
	
	public long getNetworkTime() {
		return networkTime;
	}

	public void setNetworkTime(long networkTime) {
		this.networkTime = networkTime;
	}

	public String getLoadingString() {
		return loadingString;
	}

	public void setLoadingString(String loadingString) {
		this.copyLoadingString=loadingString;
		this.loadingString = loadingString;
	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	
	public LoadStatus getLoadStatus() {
		return loadStatus;
	}

	public void setLoadStatus(LoadStatus loadStatus) {
		this.loadStatus = loadStatus;
	}

	
	public String getEmptyString() {
		return emptyString;
	}

	public void setEmptyString(String emptyString) {
		this.emptyString = emptyString;
	}

	public LoadingPager(Context context) {
		super(context);
		//初始化函数
		init();
	}

	public LoadingPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//初始化函数
		init();
	}

	public LoadingPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		//初始化函数
		init();
	}
	
	/**
	 * 初始化函数
	 */
	private void init() {
		loadingView = createLoadingView(); // 创建了加载中的界面
		if (loadingView != null) {
			this.addView(loadingView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
		errorView = createErrorView(); // 加载错误界面
		if (errorView != null) {
			this.addView(errorView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
		
		emptyView = createEmptyView(); //加载空的界面
		if (emptyView != null) {
			this.addView(emptyView, new FrameLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
		
		synchronizedDataView=createSynchronizedDataView();//创建同步数据界面
		if(synchronizedDataView!=null){
			this.addView(synchronizedDataView, new FrameLayout.LayoutParams(
								LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		
		
		showPage();//根据不同的状态显示不同的界面
	}

	
	
	/**
	 * 进行显示页面
	 */
	public void showPage() {
		//加载中的页面
		if (loadingView != null){
			if(loadingString!=null && !loadingString.trim().equals("")){
				TextView loading_txt=(TextView) loadingView.findViewById(R.id.loading_txt);
				loading_txt.setText(loadingString);
			}
			loadingView.setVisibility(state == STATE_UNKOWN
					|| state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		
		//错误页面
		if(errorView != null){
			if(errorString!=null && !errorString.trim().equals("")){
				Button page_bt=(Button) errorView.findViewById(R.id.page_bt);
				page_bt.setText(errorString);
			}
			
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
					: View.INVISIBLE);
		}
		
		//空页面
		if (emptyView != null){
			if(state == STATE_EMPTY){
				if(emptyString!=null && !emptyString.trim().equals("")){
					TextView tv_empty=(TextView) emptyView.findViewById(R.id.tv_empty);
					tv_empty.setText(emptyString);
				}
				System.out.println("showPage: System.currentTimeMillis() gap::"+//
								(System.currentTimeMillis()-networkTime)+"..getNetworkTime:"+networkTime);
				networkTime=System.currentTimeMillis();
				emptyView.setVisibility(View.VISIBLE);
			}else{
				emptyView.setVisibility(View.INVISIBLE);
			}
		}
		
		//需要同步数据的页面
		if(synchronizedDataView!=null){
			synchronizedDataView.setVisibility(state == STATE_NEED_SYNCHRONIZE_DATA ? View.VISIBLE
					: View.INVISIBLE);
		}
		
		//成功的页面
		if (state == STATE_SUCCESS){
			System.out.println("showPage: System.currentTimeMillis() gap::"+//
							(System.currentTimeMillis()-networkTime)+"..getNetworkTime:"+networkTime);
			networkTime=System.currentTimeMillis();
			if (successView == null) {
				successView = createSuccessView();
				this.addView(successView, new FrameLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
			successView.setVisibility(View.VISIBLE);
		} else {
			if (successView != null){
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	//创建成功的页面
	public abstract View createSuccessView();

	/**
	 * 创建空页面
	 * @return
	 */
	private View createEmptyView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_empty,
				null);
		return view;
	}
	
	/**
	 * 创建错误的页面
	 * @return
	 */
	private View createErrorView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_error,
				null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}
	
	
	/**
	 * 创建加载中的页面
	 * @return
	 */
	private View createLoadingView() {
		View view = View.inflate(UiUtils.getContext(),
				R.layout.loadpage_loading, null);
		return view;
	}
	
	private View createSynchronizedDataView() {
		// TODO Auto-generated method stub
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_need_synchronize_data,
				null);
		Button page_bt = (Button) view.findViewById(R.id.page_bt);
		page_bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	public enum LoadResult{
		error(2), empty(1), success(3),syschronize_data(4);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	//加载访问网络的状态
	public enum LoadStatus{
		hasComplete(0), noComplete(1);
		int value;

		LoadStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	private String copyLoadingString;
	public void show(){
		loadStatus=LoadStatus.noComplete;
		// TODO Auto-generated method stub
		if (state == STATE_ERROR || state == STATE_EMPTY || state==STATE_SUCCESS || state==STATE_UNKOWN){
			state = STATE_LOADING;
			this.loadingString=this.copyLoadingString;
			// 请求服务器 获取服务器上数据 进行判断
			// 请求服务器 返回一个结果
			ThreadManager.getInstance().createLongPool().execute(new Runnable() {
				@Override
				public void run(){
					SystemClock.sleep(300);
					final LoadResult result = load();
					UiUtils.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (result != null) {
								state = result.getValue();
								System.out.println("loadingPager..."+"show loadResult:"+state);
								showPage(); // 状态改变了,重新判断当前应该显示哪个界面
							}
							loadStatus=LoadStatus.hasComplete;
						}
					});
				}
			});
		}else if(state == STATE_NEED_SYNCHRONIZE_DATA){
			//如果是同步数据失败的情况
			state = STATE_LOADING;
			this.loadingString="所有用户数据同步中...";
			ThreadManager.getInstance().createLongPool().execute(new Runnable() {
				@Override
				public void run(){
					SystemClock.sleep(300);
					final LoadResult result = synchronizeUserData();
					UiUtils.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (result != null) {
								state = result.getValue();
								showPage(); // 状态改变了,重新判断当前应该显示哪个界面
							}
							Log.e("loadingPager UiUtils.runOnUiThread refresh showPage", "loadingPager UiUtils.runOnUiThread refresh showPage");
							loadStatus=LoadStatus.hasComplete;
						}
					});
				}
			});
		}
		
		
		Log.e("loadingPager show", "loadingPager show");
		showPage();
		
	}

	/**
	 * 进行后台实现同步用户数据
	 * 注意子类根据实际情况进行复写该方法
	 * @return
	 */
	protected LoadResult synchronizeUserData(){
		return LoadResult.success;
	}

	
	protected abstract LoadResult load();
		
	/**校验数据 */
	public LoadResult checkData(List datas) {
		if(datas==null){
			return LoadResult.error;//  请求服务器失败
		}else{
			if(datas.size()==0){
				return LoadResult.empty;
			}else{
				return LoadResult.success;
			}
		}
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
}
