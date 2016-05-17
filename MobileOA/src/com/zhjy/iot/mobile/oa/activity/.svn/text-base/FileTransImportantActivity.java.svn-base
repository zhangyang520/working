package com.zhjy.iot.mobile.oa.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.Inner.LoadingPager;
import com.zhjy.iot.mobile.oa.entity.FileTransactionDetail;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.holder.FileTransDetailHolder;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup;
import com.zhjy.iot.mobile.oa.protocol.FileTransActivityProtocol;
import com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.OpenFileTipDialog;
import com.zhjy.iot.mobile.oa.utils.UiUtils;

public class FileTransImportantActivity extends BaseActivity {

	private ImageButton backBtn;
	private TextView nevigaTitle;
	private LinearLayout rootView;
	private LoadingPager mLoadingPager;
	
	private FileTransActivityProtocol mFileTransActivityProtocol = new FileTransActivityProtocol();
	FileTransDetailHolder mFileTransHolder = new FileTransDetailHolder(FileTransImportantActivity.this);
	
	private FileTransactionDetail mFileTransactionDetail;
	private String keyTag="文件传阅重要";
	private String docmentId;//文档id
	
	private Handler handler = new Handler(){
		public void handleMessage(Message message) {
			switch (message.what) {
			case MobileOAConstant.LOAD_DATA_ERROR:
				UiUtils.showToast("数据解析异常");
				break;
			case MobileOAConstant.LOAD_DATA_FAIL:
				UiUtils.showToast((String)message.obj);
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		customLayout = R.layout.file_form_policy_details_one;
		super.onCreate(savedInstanceState);
		initLoadingPager();
	}


	@Override
	protected void initViews() {
		docmentId=getIntent().getStringExtra("docmentId");
		backBtn = (ImageButton) findViewById(R.id.back_activity);
		rootView = (LinearLayout) findViewById(R.id.load_view);
		nevigaTitle = (TextView) findViewById(R.id.nevigaTitle);
		nevigaTitle.setText("已收重要文件详情");
	}

	@Override
	protected void initInitevnts() {
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	private void initLoadingPager() {
		if (mLoadingPager == null) {
			mLoadingPager = new LoadingPager(UiUtils.getContext()) {
				
				@Override
				protected LoadResult load() {
					List<FileTransactionDetail> list = null;
					Message message = new Message();
					try {
						UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
						mUrlParamsEntity.setMethodName(MobileOAConstant.FILE_TRANS_DETAIL);
						LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
						paramsMap.put("tranCode", "tran00013");
						paramsMap.put("docmentId",docmentId);
						mUrlParamsEntity.setParamsHashMap(paramsMap);
						mFileTransactionDetail = mFileTransActivityProtocol.loadDataFromWebService(mUrlParamsEntity);
						list = new ArrayList<FileTransactionDetail>();
						list.add(mFileTransactionDetail);
					} catch (JsonParseException e) {
						message.what = MobileOAConstant.LOAD_DATA_ERROR;
						handler.sendMessage(message);
						e.printStackTrace();
					} catch (ContentException e) {
						if(e.getErrorCode()==MobileOAConstant.NO_NODE_ERROR_CODE){
							//Node查找数据库出错:提示用户进行同步所有用户数据
							
							return LoadResult.syschronize_data;
						}else{
							message.what = MobileOAConstant.LOAD_DATA_FAIL;
							message.obj = keyTag+"加载首页失败,"+e.getMessage();
							handler.sendMessage(message);
							e.printStackTrace();
							return checkData(list);
						}
					}
					return checkData(list);
				}
				
				@Override
				protected LoadResult synchronizeUserData(){
					try {
						UrlParamsEntity urlParamsEntitiy=new UrlParamsEntity();
						//后台的方法名(后台定义好的)
						urlParamsEntitiy.setMethodName(MobileOAConstant.SYNBASICRESOURCE);
						//tran00002
						LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
						map.put("tranCode", "tran00002");
						urlParamsEntitiy.setParamsHashMap(map);
						SynchronizeDataProtocol protocol=new SynchronizeDataProtocol();
						protocol.loadDataFromWebService(urlParamsEntitiy);
						
						//如果同步成功了..怎么办
						mFileTransactionDetail=mFileTransActivityProtocol.parseJson(mFileTransActivityProtocol.getSuccessSoapObject());
						return LoadResult.success;
					} catch (ContentException e) {
						e.printStackTrace();
						System.out.println("synchronizedDatas "+e.getMessage());
						return LoadResult.syschronize_data;
					} catch (JsonParseException e) {
						e.printStackTrace();
						System.out.println("synchronizedDatas "+e.getMessage());
						return LoadResult.syschronize_data;
					}
				}
				
				@Override
				public View createSuccessView() {
					mFileTransHolder.setYifa(false);
					mFileTransHolder.setDocmentId(docmentId);
					mFileTransHolder.setData(mFileTransactionDetail);
					View contentView = mFileTransHolder.getContentView();
					return contentView;
				}
			};
			
			mLoadingPager.setEmptyString("文件传阅重要详情为空");
			mLoadingPager.setLoadingString("文件传阅重要详情加载中...");
			mLoadingPager.setErrorString("文件传阅重要详情加载失败");
			mLoadingPager.show();
			mLoadingPager.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
			rootView.addView(mLoadingPager);
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==MobileOAConstant.FILE_TRANS_TRANSFILE_REQUEST_CODE && resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			if(data !=null && data.getStringExtra(MobileOAConstant.LOAD_STATE).equals(MobileOAConstant.LOAD_SUCCESS)){
				//如果能够成功返回，并且是成功访问网络
				//进行获取网络内容,进行解析
				String content=data.getStringExtra(MobileOAConstant.LOAD_CONTENT);
				Toast.makeText(UiUtils.getContext(),"文件传阅成功!", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(UiUtils.getContext(),"文件传阅失败!", Toast.LENGTH_SHORT).show();
			}
		}else if(requestCode==MobileOAConstant.SYNCHRONIZE_DATA_REQUEST_CODE && resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			if(data !=null && data.getStringExtra(MobileOAConstant.LOAD_STATE).equals(MobileOAConstant.LOAD_SUCCESS)){
				Toast.makeText(UiUtils.getContext(), "同步用户数据成功!", 0).show();
				//进行回调函数
				mFileTransHolder.recallTransmitClick();
			}else{
				Toast.makeText(UiUtils.getContext(), "同步用户数据失败!", 0).show();
			}
		}else if(requestCode==MobileOAConstant.LOAD_REQUEST_CODE && resultCode==MobileOAConstant.LOAD_RESULT_CODE){
			if(data !=null && data.getStringExtra(MobileOAConstant.LOAD_STATE).equals(MobileOAConstant.LOAD_SUCCESS)){
				//如果能够成功返回，并且是成功访问网络
				//进行获取网络内容,进行解析
				Toast.makeText(UiUtils.getContext(),"下载成功!", Toast.LENGTH_SHORT).show();
				String fileName=data.getStringExtra(MobileOAConstant.LOAD_CONTENT);
				OpenFileTipDialog.openFiles(fileName,UiUtils.getContext()); 
			}else{
				Toast.makeText(UiUtils.getContext(),"下载失败!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(FileTreePopup.getInstance().getPopupWindow()!=null && FileTreePopup.getInstance().getPopupWindow().isShowing()){
			FileTreePopup.getInstance().getPopupWindow().dismiss();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
