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
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager.LoadStatus;
import com.zhjy.iot.mobile.oa.activity.MainPageActivity;
import com.zhjy.iot.mobile.oa.adapter.MyPagerAdapter;
import com.zhjy.iot.mobile.oa.adapter.MyPagerAdapter.ViewPagerType;
import com.zhjy.iot.mobile.oa.manager.PolicyPagerManager;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.PolicyReceiveWeibanLoadingPager;
import com.zhjy.iot.mobile.oa.view.PolicyReceiveYibanLoadingPager;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日下午3:16:01
 * 
 * @描述 行政收文的fragment
 */
public class PolicyReceiveFragment extends Fragment implements OnClickListener, OnPageChangeListener {
	private TextView yiban;
	private TextView weiban;
	private View contentView;
	private ViewPager vp;
	private MyPagerAdapter myPagerAdapter;
	
	private LoadingPager loadingPager;
	private int index;
	private View indicate_line;
	private int lineWidth;//indicate_line的宽度
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(contentView==null){
			contentView = View.inflate(UiUtils.getContext(), R.layout.fragment_policy, null);
			yiban=(TextView)contentView.findViewById(R.id.tab_yiban);
			weiban=(TextView)contentView.findViewById(R.id.tab_weiban);
			indicate_line = contentView.findViewById(R.id.indicate_line);
			vp=(ViewPager)contentView.findViewById(R.id.vp);
			myPagerAdapter=new MyPagerAdapter(ViewPagerType.PolicyReceive);
			vp.setAdapter(myPagerAdapter);
			
			yiban.setOnClickListener(this);
			weiban.setOnClickListener(this);
			vp.setOnPageChangeListener(this);
		}
		index=0;
		//先是默认设置到第一页
		vp.setCurrentItem(index);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		   Bundle savedInstanceState){
		System.out.println("fragment PolicyReceiveFragment hashcode:"+hashCode());
		loadingPager=PolicyPagerManager.getLoadingPager(index);
		//是否需要请求网络
		needProcessNetwork(index);
		if(contentView.getParent()!=null){
			((ViewGroup)contentView.getParent()).removeView(contentView);
		}
		//进行设置标题
		((MainPageActivity)getActivity()).setNevigaTitle(R.string.tab1);
		calculateIndicateLineWidth();
		lightAndScaleTabTitle();
		return contentView;
	}
	
	/**
	 * 是否需要请求网络
	 * @param position 
	 */
	private void needProcessNetwork(int position) {
		//看数据库中有无数据
		switch(position){
			case 1://已办
				MainPageActivity.searchVisible(1);
				loadingPager=PolicyPagerManager.getLoadingPager(index);
				((PolicyReceiveYibanLoadingPager)loadingPager).setActivity(this);
				if(((PolicyReceiveYibanLoadingPager)loadingPager).hasYibanDatas()){
					//如果有数据,同时需要查看是否超过对应的时间
					if(((PolicyReceiveYibanLoadingPager)loadingPager).isOverNetGapTime()){
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
				
			case 0://未办
				MainPageActivity.searchVisible(0);
				loadingPager=PolicyPagerManager.getLoadingPager(index);
				((PolicyReceiveWeibanLoadingPager)loadingPager).setActivity(this);
				if(((PolicyReceiveWeibanLoadingPager)loadingPager).hasWeibanDatas()){
					//如果有数据,同时需要查看是否超过对应的时间
					if(((PolicyReceiveWeibanLoadingPager)loadingPager).isOverNetGapTime()){
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
		}
	}
	
	int currentId;
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tab_yiban:
				if(currentId!=R.id.tab_yiban){
					currentId=R.id.tab_yiban;
					vp.setCurrentItem(0);
					MainPageActivity.searchVisible(0);
				}
				break;
				
			case R.id.tab_weiban:
				if(currentId!=R.id.tab_weiban){
					currentId=R.id.tab_weiban;
					vp.setCurrentItem(1);
					MainPageActivity.searchVisible(1);
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
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
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
		
	}
	
	/**根据当前page使标题高亮*/
	private void lightAndScaleTabTitle() {
		int currentPage = vp.getCurrentItem();
		yiban.setTextColor(currentPage==0?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		weiban.setTextColor(currentPage==1?getResources().getColor(R.color.gry_black)
				:getResources().getColor(R.color.gry_black_press));
		
		ViewPropertyAnimator.animate(yiban).scaleX(currentPage==0?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(yiban).scaleY(currentPage==0?1.2f:1.0f).setDuration(200);
		
		ViewPropertyAnimator.animate(weiban).scaleX(currentPage==1?1.2f:1.0f).setDuration(200);
		ViewPropertyAnimator.animate(weiban).scaleY(currentPage==1?1.2f:1.0f).setDuration(200);
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
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode==MobileOAConstant.WEIBAN_ACTIVITY_REQUEST_CODE && 
    					resultCode==MobileOAConstant.WEIBAN_ACTIVITY_RESULT_CODE){
    		//需要进行更新未办中loadingPager中adapter的数据
    		((PolicyReceiveWeibanLoadingPager)loadingPager).processClickIndex();
    	}
    }
}
