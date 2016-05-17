package com.zhjy.iot.mobile.oa.activity;

import java.io.File;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.OpenFileTipDialog;
import com.zhjy.iot.mobile.oa.utils.SDCardUtils;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.utils.Utils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-31下午2:40:08
 * 
 * @描述 下载中进度的显示
 */
public class DownloadingActivity extends BaseActivity{
	
	private LinearLayout ll;
	private String url;
	private AnimationDrawable drawable;
	private String fileName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout=R.layout.down_loading;
		super.onCreate(savedInstanceState);
	
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initViews() {
		String title=getIntent().getStringExtra(MobileOAConstant.LOADING_TITLE_KEY);
		TextView tv=(TextView) findViewById(R.id.tv);
		tv.setText(title);
		ll=(LinearLayout) findViewById(R.id.ll);
		ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.alert_round_rect));
		ImageView im=(ImageView) ll.findViewById(R.id.iv);
		im.setBackgroundResource(R.drawable.loading_anim);
		drawable = (AnimationDrawable)im.getBackground();
		drawable.start();
		url=getIntent().getStringExtra(MobileOAConstant.LOADING_URL_KEY);
		System.out.println("DownloadingActivity url:"+url);
		fileName=getIntent().getStringExtra(MobileOAConstant.DOWNLOAD_FILE_NAME);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void initInitevnts(){
		//进行发送请求
		final File downloadFile=new File(SDCardUtils.getInstance().getDownLoadFileDir(),fileName);
		if(!downloadFile.exists()){
			Utils.getHttpUtils().download(url, downloadFile.getAbsolutePath(),new RequestCallBack<File>(){
				//如果登录失败
				@Override
				public void onFailure(HttpException arg0, String arg1){
					UiUtils.getHandler().postDelayed(new Runnable() {
						@Override
						public void run(){
							Intent intent=new Intent();
							intent.putExtra(MobileOAConstant.LOAD_STATE, MobileOAConstant.LOAD_FAILURE);
							setResult(MobileOAConstant.LOAD_RESULT_CODE, intent);
							finish();
						}
					}, 500);
					
				}
				//如果登录成功,
				@Override
				public void onSuccess(final ResponseInfo<File> arg0){
					UiUtils.getHandler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent=new Intent();
							intent.putExtra(MobileOAConstant.LOAD_STATE, MobileOAConstant.LOAD_SUCCESS);
							intent.putExtra(MobileOAConstant.LOAD_CONTENT, downloadFile.getAbsolutePath());
							setResult(MobileOAConstant.LOAD_RESULT_CODE, intent);
							finish();
						}
					}, 500);
				}  
		});
		}else{
			//直接进行finish
			finish();
			Toast.makeText(UiUtils.getContext(), "文件已存在!",Toast.LENGTH_SHORT).show();
			OpenFileTipDialog.openFiles(downloadFile.getAbsolutePath(),UiUtils.getContext()); 
		}
		
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//动画的结束
		drawable.stop();
	}
}
