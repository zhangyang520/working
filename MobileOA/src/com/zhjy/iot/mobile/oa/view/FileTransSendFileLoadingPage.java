package com.zhjy.iot.mobile.oa.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.fragment.FileTransactionFragment;
import com.zhjy.iot.mobile.oa.holder.FileTransSendFileHolder;
/***
 * 
 * @项目名：MobileOA
 * @类名称：SendFileLoadingPage
 * @类描述：发送文件
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-14 下午4:04:52
 * @version 1.1
 * 
 */
public class FileTransSendFileLoadingPage extends LoadingPager{
	
	private Activity activity;
	private FileTransactionFragment fileTransactionFragment;
	private FileTransSendFileHolder holder;

	public void setActivity(Activity activity) {
		this.activity = activity;
		if(holder!=null){
			holder.setActivity(activity);
		}
	}

    
	public void setFileTransactionFragment(
			FileTransactionFragment fileTransactionFragment) {
		this.fileTransactionFragment = fileTransactionFragment;
		if(holder!=null){
			holder.setFileTransactionFragment(fileTransactionFragment);
		}
	}


	public FileTransSendFileLoadingPage(Context context) {
		super(context);
	}

	
	public FileTransSendFileLoadingPage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public FileTransSendFileLoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public View createSuccessView() {
//		UiUtils.showToast("createSuccessView ...FileTransSendFileLoadingPage");
		holder = new FileTransSendFileHolder();
		holder.setActivity(activity);
		holder.setFileTransactionFragment(fileTransactionFragment);
		return holder.getContentView();
	}

	@Override
	protected LoadResult load() {
		return null;
	}

	public void addFile(String fileName){
		if(holder!=null){
			holder.addFile(fileName);
		}
	}
}
