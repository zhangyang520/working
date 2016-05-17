package com.zhjy.iot.mobile.oa.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager.LoadResult;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager.LoadStatus;
import com.zhjy.iot.mobile.oa.activity.MainPageActivity;
import com.zhjy.iot.mobile.oa.adapter.MyPagerAdapter;
import com.zhjy.iot.mobile.oa.adapter.MyPagerAdapter.ViewPagerType;
import com.zhjy.iot.mobile.oa.manager.FileTransactionManager;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.FileTransImportantLoadingPager;
import com.zhjy.iot.mobile.oa.view.FileTransSendFileLoadingPage;
import com.zhjy.iot.mobile.oa.view.FileTransYifaLoadingPager;
import com.zhjy.iot.mobile.oa.view.FileTransYishouLoadingPager;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午3:16:01
 * 
 * @描述 文件传阅的fragment
 */
public class FileTransactionFragment extends Fragment implements OnClickListener, OnPageChangeListener{
 
	public static FileTransactionFragment instance;
	private TextView yishou;//已收按钮
	private TextView yifa;//已发按钮
	private TextView zhongyao;//重要按钮
	private TextView tab_transfile;//传文件按钮
	private ViewPager vp;//vp
	private View contentView;//内容的contenView
	private MyPagerAdapter myPagerAdapter;//viewPager的适配器
	
	private LoadingPager loadingPager;
	private int index;
	private View indicate_line;
	private int lineWidth;//indicate_line的宽度
	
	private FragmentActivity activity;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this.getActivity();
		instance=this;
		if(contentView==null){
			contentView=View.inflate(UiUtils.getContext(), R.layout.fragment_file_transaction, null);
			
			yishou=(TextView) contentView.findViewById(R.id.tab_yishou);
			yifa=(TextView) contentView.findViewById(R.id.tab_yifa);
			zhongyao=(TextView) contentView.findViewById(R.id.tab_zhongyao);
			tab_transfile=(TextView) contentView.findViewById(R.id.tab_transfile);
			indicate_line = contentView.findViewById(R.id.indicate_line);
			
			vp=(ViewPager) contentView.findViewById(R.id.vp);
			myPagerAdapter = new MyPagerAdapter(ViewPagerType.FileTransaction);
			vp.setAdapter(myPagerAdapter);
			yishou.setOnClickListener(this);
			yifa.setOnClickListener(this);
			zhongyao.setOnClickListener(this);
			tab_transfile.setOnClickListener(this);
			vp.setOnPageChangeListener(this);
		}
		index=0;
		//先是默认设置到第一页
		vp.setCurrentItem(index);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		System.out.println("fragment FileTransactionFragment hashcode:"+hashCode());
		loadingPager=FileTransactionManager.getLoadingPager(index);
		//是否需要请求网络
		needProcessNetwork(index);
		if(contentView.getParent()!=null){
			((ViewGroup)contentView.getParent()).removeView(contentView);
		}
		//进行设置标题
		((MainPageActivity)getActivity()).setNevigaTitle(R.string.tab2);
		calculateIndicateLineWidth();
		lightAndScaleTabTitle();
		
		return contentView;
	}
	
	
	/**
	 * 是否需要请求网络
	 * @param position 
	 */
	private void needProcessNetwork(int position) {
		MainPageActivity.searchVisible(0);
		//看数据库中有无数据
		switch(position){
			case 0://已办理
				loadingPager=FileTransactionManager.getLoadingPager(index);
				if(((FileTransYishouLoadingPager)loadingPager).hasYiShouDatas()){
					//如果有数据,同时需要查看是否超过对应的时间
					if(((FileTransYishouLoadingPager)loadingPager).isOverNetGapTime()){
						if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
							MainPageActivity.searchVisible(0);
							loadingPager.show();
						}
					}else{
						if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
							//进行刷新界面:
							if(((FileTransYishouLoadingPager)loadingPager).getState()==LoadResult.empty.getValue()){
								((FileTransYishouLoadingPager)loadingPager).setState(LoadResult.success.getValue());
								((FileTransYishouLoadingPager)loadingPager).showPage();
								
							}
							//进行刷新界面:
							((FileTransYishouLoadingPager)loadingPager).setDataSetChange();
						}else{
							//进行不处理
						}
					}
				}else{
					if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
						//如果没有数据
						loadingPager.show();
					}
				}
				break;
				
			case 1://未办理
				loadingPager=FileTransactionManager.getLoadingPager(index);
				if(((FileTransYifaLoadingPager)loadingPager).hasYiFaDatas()){
					//如果有数据,同时需要查看是否超过对应的时间
					if(((FileTransYifaLoadingPager)loadingPager).isOverNetGapTime()){
						if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
							loadingPager.show();
						}
					}
				}else{
					if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
						//如果没有数据
						loadingPager.show();
					}
				}
				break;
				
				
			case 2://重要
				loadingPager=FileTransactionManager.getLoadingPager(index);
				if(((FileTransImportantLoadingPager)loadingPager).hasImportantDatas()){
					//如果有数据,同时需要查看是否超过对应的时间
					if(((FileTransImportantLoadingPager)loadingPager).isOverNetGapTime()){
						if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
							loadingPager.show();
						}
					}else{
						if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
							//进行刷新界面:
							if(((FileTransImportantLoadingPager)loadingPager).getState()==LoadResult.empty.getValue()){
								((FileTransImportantLoadingPager)loadingPager).setState(LoadResult.success.getValue());
								((FileTransImportantLoadingPager)loadingPager).showPage();
							}
							((FileTransImportantLoadingPager)loadingPager).setDataSetChange();
						}else{
							//进行不处理
						}
					}
				}else{
					System.out.println("isOverNetGapTimeisOverNetGapTime no importantData:.......");
					if(loadingPager.getLoadStatus()==LoadStatus.hasComplete){
						//如果没有数据
						loadingPager.show();
					}
				}
				break;
				
			case 3://重要
				loadingPager=FileTransactionManager.getLoadingPager(index);
				((FileTransSendFileLoadingPage)loadingPager).setActivity(getActivity());
				((FileTransSendFileLoadingPage)loadingPager).setFileTransactionFragment(this);
				loadingPager.setState(LoadResult.success.getValue());
				loadingPager.showPage();
				break;
		}
	}
	
	
	
	int currentId;
//	boolean clickFlag=false;
	@Override
	public void onClick(View v){
		MainPageActivity.searchVisible(0);
		switch (v.getId()) {
			case R.id.tab_yishou:
//				clickFlag=true;
				if(currentId!=R.id.tab_yishou){
					currentId=R.id.tab_yishou;
					if(FileTransactionManager.getLoadingPager(0).getLoadStatus()==LoadStatus.hasComplete){
//						UiUtils.showToast("已收文件..."); 
						//需要进行判断数据库中有无数据
						vp.setCurrentItem(0);
					}
				}
				break;
				
			case R.id.tab_yifa:
//				clickFlag=true;
				if(currentId!=R.id.tab_yifa){
					currentId=R.id.tab_yifa;
					if(FileTransactionManager.getLoadingPager(1).getLoadStatus()==LoadStatus.hasComplete){
//						UiUtils.showToast("已发文件...");
						vp.setCurrentItem(1);
					}
				}
				break;	
			case R.id.tab_zhongyao:
//				clickFlag=true;
				if(currentId!=R.id.tab_zhongyao){
					currentId=R.id.tab_zhongyao;
					if(FileTransactionManager.getLoadingPager(2).getLoadStatus()==LoadStatus.hasComplete){
//						UiUtils.showToast("重要文件...");
						vp.setCurrentItem(2);
					}
				}
			break;
			
			case R.id.tab_transfile:
				if(currentId!=R.id.tab_transfile){
					currentId=R.id.tab_transfile;
					if (activity != null) {
						FileTransactionManager.setActivity(getActivity());
						FileTransactionManager.setFileTransactionFragment(this);
						vp.setCurrentItem(3);
						loadingPager=FileTransactionManager.getLoadingPager(index);
						((FileTransSendFileLoadingPage)loadingPager).setActivity(getActivity());
						((FileTransSendFileLoadingPage)loadingPager).setFileTransactionFragment(this);
					}
				}
				break;
	 default:
		break;
	 }
	}
	
	
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
	
	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		int targetPosition = lineWidth*position + positionOffsetPixels/MobileOAConstant.FILETRANSACTION_PAGER_COUNT;
		ViewPropertyAnimator.animate(indicate_line).translationX(targetPosition).setDuration(0);
		
	}
	@Override
	public void onPageSelected(int position){
		lightAndScaleTabTitle();
		index=position;
		needProcessNetwork(position);
	}
	
	/**根据当前page使标题高亮 */
	private void lightAndScaleTabTitle() {
		int currentPage = vp.getCurrentItem();
		yishou.setTextColor(currentPage==0?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		
		yifa.setTextColor(currentPage==1?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		
		zhongyao.setTextColor(currentPage==2?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		
		tab_transfile.setTextColor(currentPage==3?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		
		ViewPropertyAnimator.animate(yishou).scaleX(currentPage==0?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(yishou).scaleY(currentPage==0?1.2f:1.0f).setDuration(200);
		
		ViewPropertyAnimator.animate(yifa).scaleX(currentPage==1?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(yifa).scaleY(currentPage==1?1.2f:1.0f).setDuration(200);
		
		ViewPropertyAnimator.animate(zhongyao).scaleX(currentPage==2?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(zhongyao).scaleY(currentPage==2?1.2f:1.0f).setDuration(200);
		
		ViewPropertyAnimator.animate(tab_transfile).scaleX(currentPage==3?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(tab_transfile).scaleY(currentPage==3?1.2f:1.0f).setDuration(200);
	}
	
    /**计算indicate_line的宽度*/
	private void calculateIndicateLineWidth() {
		int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		lineWidth = screenWidth/MobileOAConstant.FILETRANSACTION_PAGER_COUNT;
		indicate_line.getLayoutParams().width = lineWidth;
		indicate_line.requestLayout();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
//		System.out.println("FileTransactionFragment  onActivityResult" + "   requestCode = " + requestCode + ";resultCode = " + resultCode);
		if(requestCode==MobileOAConstant.FILE_TRANS_2_IMPORTANT_REQUEST_CODE && 
											resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			//进行请求转为重要的requestCode
			String loadState=data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if(MobileOAConstant.LOAD_SUCCESS.equals(loadState)){
				//如果操作成功
				Toast.makeText(UiUtils.getContext(),"转为重要成功", 0).show();
				((FileTransYishouLoadingPager)FileTransactionManager.getLoadingPager(index)).processClickIndex();
			}else{
				//加载的状态显示为失败
				Toast.makeText(UiUtils.getContext(),data.getStringExtra(MobileOAConstant.LOAD_CONTENT), 0).show();
			}
		}else if(requestCode==MobileOAConstant.FILE_TRANS_2_PUTONG_REQUEST_CODE && 
			resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			//进行请求转为重要的requestCode
			String loadState=data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if(MobileOAConstant.LOAD_SUCCESS.equals(loadState)){
				//如果操作成功
				Toast.makeText(UiUtils.getContext(),"转为普通成功", 0).show();
				((FileTransImportantLoadingPager)FileTransactionManager.getLoadingPager(index)).processClickIndex();
			}else{
				//加载的状态显示为失败
				Toast.makeText(UiUtils.getContext(),data.getStringExtra(MobileOAConstant.LOAD_CONTENT), 0).show();
			}
		}else if(requestCode == MobileOAConstant.FILE_TRANS_2_DELETE_REQUEST_CODE &&
				resultCode== MobileOAConstant.LOAD_RESULT_CODE){
			System.out.println("FileTransactionFragment...onActivityResult...delete data-------------");
			String loadState = data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if (MobileOAConstant.LOAD_SUCCESS.equals(loadState)) {
				Toast.makeText(UiUtils.getContext(), "删除成功", 0).show();
				FileTransYifaLoadingPager mFileTransYifaLoadingPager = (FileTransYifaLoadingPager) FileTransactionManager.getLoadingPager(index);
				mFileTransYifaLoadingPager.processClickIndex();
			}else{
				//加载的状态改为失败
				Toast.makeText(UiUtils.getContext(),data.getStringExtra(MobileOAConstant.LOAD_CONTENT), 0).show();
			}
		}else if(requestCode==MobileOAConstant.FILE_TRANSACTION_SELECT_FILE_REQUEST_CODE){
			if(data!=null && data.getData()!=null){
				Uri uri=data.getData();
				String filePath=uri.getPath();
				loadingPager=FileTransactionManager.getLoadingPager(index);
				((FileTransSendFileLoadingPage)loadingPager).addFile(filePath);
			}
		}else if(requestCode == MobileOAConstant.SYNCHRONIZE_DATA_REQUEST_CODE &&
				resultCode== MobileOAConstant.LOAD_RESULT_CODE){
			String loadState = data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if (MobileOAConstant.LOAD_SUCCESS.equals(loadState)) {
				Toast.makeText(UiUtils.getContext(), "同步数据成功!", 0).show();
			}else{
				//加载的状态
				Toast.makeText(UiUtils.getContext(), "同步数据失败!", 0).show();
			}
		}else if(requestCode == MobileOAConstant.FILR_SEND_REQUEST_CODE &&
				resultCode== MobileOAConstant.LOAD_RESULT_CODE){
			String loadState = data.getStringExtra(MobileOAConstant.LOAD_STATE);
			if (MobileOAConstant.LOAD_SUCCESS.equals(loadState)) {
				Toast.makeText(UiUtils.getContext(), "文件上传成功!", 0).show();
			}else{
				//加载的状态
				Toast.makeText(UiUtils.getContext(), "文件上传失败!", 0).show();
			}
		}
	}
}
