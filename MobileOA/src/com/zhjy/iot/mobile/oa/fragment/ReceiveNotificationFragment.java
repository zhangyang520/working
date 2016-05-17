package com.zhjy.iot.mobile.oa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager.LoadStatus;
import com.zhjy.iot.mobile.oa.activity.MainPageActivity;
import com.zhjy.iot.mobile.oa.adapter.MyPagerAdapter;
import com.zhjy.iot.mobile.oa.adapter.MyPagerAdapter.ViewPagerType;
import com.zhjy.iot.mobile.oa.manager.ReceiveNotificationManager;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.ReceiveNotificationDaiYueLoadingPager;
import com.zhjy.iot.mobile.oa.view.ReceiveNotificationYiYueLoadingPager;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午3:16:01
 * 
 * @描述 公文通知的fragment
 */
public class ReceiveNotificationFragment extends Fragment implements OnClickListener, OnPageChangeListener {
	
	
	private Button yiBan,weiBan;
	private View contentView;
	private ViewPager vp;
	private MyPagerAdapter mPagerAdapter;
	
	private LoadingPager loadingPager;
	private int index;
	private TextView tvDaiYue;
	private TextView tvYiYue;
	private View indicate_line;
	private int lineWidth;//indicate_line的宽度

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if (contentView == null) {
			contentView = View.inflate(UiUtils.getContext(), R.layout.fragment_receive_notification, null);
			
			tvDaiYue = (TextView) contentView.findViewById(R.id.tab_daiyue);
			tvYiYue = (TextView) contentView.findViewById(R.id.tab_yiyue);
			vp=(ViewPager)contentView.findViewById(R.id.vp);
			indicate_line = contentView.findViewById(R.id.indicate_line);
			
			tvDaiYue.setOnClickListener(this);
			tvYiYue.setOnClickListener(this);
			mPagerAdapter = new MyPagerAdapter(ViewPagerType.ReceiveNotification);
			vp.setAdapter(mPagerAdapter);
			vp.setOnPageChangeListener(this);
		}
		index = 0;
		//先默认设置到第一页
		vp.setCurrentItem(0);
		
	}
 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("fragment ReceiveNotificationFragment hashcode:"+hashCode());
		loadingPager = ReceiveNotificationManager.getLoadingPager(index);
		//是否进行网络请求
		needProcessNetwork(index);
		if(contentView.getParent()!=null){
			((ViewGroup)contentView.getParent()).removeView(contentView);
		}
		
		//对Fragment进行设置标题
		((MainPageActivity)getActivity()).setNevigaTitle(R.string.tab3);
		calculateIndicateLineWidth();
		lightAndScaleTabTitle();
		return contentView;
	}

	/**
	 * 描述：是否需要请求网络
	 */
	private void needProcessNetwork(int position) {
		MainPageActivity.searchVisible(0);
		//查看数据库是否有数据
		switch (position) {
		case 0://待阅
			loadingPager = ReceiveNotificationManager.getLoadingPager(index);
			((ReceiveNotificationDaiYueLoadingPager)loadingPager).setActivity(this);
			if (((ReceiveNotificationDaiYueLoadingPager)loadingPager).hasYiYueDatas()) {
				//如果有数据,同时需要查看是否超过对应的时间
				if (((ReceiveNotificationDaiYueLoadingPager)loadingPager).isOverNetGapTime()) {
					if (loadingPager.getLoadStatus() == LoadStatus.hasComplete) {
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
		case 1://已阅
			loadingPager = ReceiveNotificationManager.getLoadingPager(index);
			if (((ReceiveNotificationYiYueLoadingPager)loadingPager).hasDaiYueDatas()) {
				//如果有数据,同时需要查看是否超过对应的时间
				if (((ReceiveNotificationYiYueLoadingPager)loadingPager).isOverNetGapTime()) {
					if (loadingPager.getLoadStatus() == LoadStatus.hasComplete) {
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

		default:
			break;
		}
	}

	int currentId;
	
	
	@Override
	public void onClick(View v) {
		MainPageActivity.searchVisible(0);
		switch (v.getId()) {
			case R.id.tab_daiyue:
				if(currentId!=R.id.tab_daiyue){
					currentId=R.id.tab_daiyue;
					vp.setCurrentItem(0);
				}
				break;	
			case R.id.tab_yiyue:
				if(currentId!=R.id.tab_yiyue){
					currentId=R.id.tab_yiyue;
					vp.setCurrentItem(1);
				}
				break;	
			default:
				break;
		}
	}


	/**
	 * 实现OnPageChangeListener
	 * @param position
	 * @param positionOffset
	 * @param positionOffsetPixels
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		int targetPosition = lineWidth*position + positionOffsetPixels/2;
		ViewPropertyAnimator.animate(indicate_line).translationX(targetPosition).setDuration(0);
	}

	@Override
	public void onPageSelected(int position) {
		lightAndScaleTabTitle();
		index=position;
		needProcessNetwork(position);
	}
	

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
	}
	
	/**根据当前page使标题高亮*/
	private void lightAndScaleTabTitle() {
		int currentPage = vp.getCurrentItem();
		tvDaiYue.setTextColor(currentPage==0?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		tvYiYue.setTextColor(currentPage==1?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		
		ViewPropertyAnimator.animate(tvDaiYue).scaleX(currentPage==0?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(tvDaiYue).scaleY(currentPage==0?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(tvYiYue).scaleX(currentPage==1?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(tvYiYue).scaleY(currentPage==1?1.2f:1.0f).setDuration(200);
	}
	
    /**计算indicate_line的宽度*/
	private void calculateIndicateLineWidth() {
		int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		lineWidth = screenWidth/2;
		indicate_line.getLayoutParams().width = lineWidth;
		indicate_line.requestLayout();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==MobileOAConstant.NOTIFICATION_DAIYUE_REQUEST_CODE &&
				 resultCode==MobileOAConstant.NOTIFICATION_DAIYUE_RESULT_CODE){
			//进行获取data
			String workItemId=data.getStringExtra("workItemId");
			String processId=data.getStringExtra("prcessId");
			((ReceiveNotificationDaiYueLoadingPager)loadingPager).processClickIndex();
		}
	}
}
