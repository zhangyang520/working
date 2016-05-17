package com.zhjy.iot.mobile.oa.holder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.activity.LoadingActivity;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.fragment.FileTransactionFragment;
import com.zhjy.iot.mobile.oa.manager.ThreadManager;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup.OnFileTreePopupClickListenter;
import com.zhjy.iot.mobile.oa.popup.LogoutPopup;
import com.zhjy.iot.mobile.oa.protocol.FileTransSendFileProtocol;
import com.zhjy.iot.mobile.oa.tree.TreeNode;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.NetUtils;
import com.zhjy.iot.mobile.oa.utils.SDCardUtils;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
import com.zhjy.iot.mobile.oa.view.ListViewForScrollView;
/**
 * 
 * @项目名：MobileOA
 * @类名称：FileTransSendFile
 * @类描述：文件发送
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-14 下午4:13:01
 * @version 1.1
 * 
 */
public class FileTransSendFileHolder extends BaseHolder<String> {
	
	private EditText etTitle;
	private EditText etDescription;
	private Button btn_addFile;
	private Button btnSender;
	private Button transmit;
	private Button cancel;
	
//	private String url = "http://192.168.81.199:8089/default/mobileInterface/fileDownLoad/UpFilesServlet.jsp";
	
	
	private Activity activity;
	private ListViewForScrollView nameListView;
	private ListViewForScrollView sendFileListView;
	private List<String> nameList;
	private List<String> fileNameList;
	private List<File> selectedFiles;
	
	private FileTransactionFragment fileTransactionFragment;
	private NameAdapter nameAdapter;
	private NameAdapter fileNameAdapter;
	
	
	private List<TreeNode> peopleNodes;
	private String title;
	private String description;
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
	public void setFileTransactionFragment(
			FileTransactionFragment fileTransactionFragment) {
		this.fileTransactionFragment = fileTransactionFragment;
	}


	@Override
	public View initView() {
		View contentView=View.inflate(UiUtils.getContext(), R.layout.activity_add_file, null);
		etTitle = (EditText) contentView.findViewById(R.id.summary);
		etDescription = (EditText) contentView.findViewById(R.id.et_description);
		btn_addFile = (Button) contentView.findViewById(R.id.btn_add_file);
		btnSender = (Button) contentView.findViewById(R.id.btn_sender);
		nameListView = (ListViewForScrollView) contentView.findViewById(R.id.name_listview);
		sendFileListView = (ListViewForScrollView) contentView.findViewById(R.id.sendfile_listview);
		transmit = (Button) contentView.findViewById(R.id.transmit);
		cancel = (Button) contentView.findViewById(R.id.cancel);
		setAction();
		return contentView;
	}
	
	@Override
	public void refreshView(String data) {
		
	}
	
	private void setAction() {
		/**添加文件*/
		btn_addFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (SDCardUtils.getInstance().isSDCardEnable()) {
					Intent mIntent = new Intent();
					mIntent.setAction(Intent.ACTION_GET_CONTENT);
					mIntent.setType("file/*");
					fileTransactionFragment.startActivityForResult(mIntent,MobileOAConstant.FILE_TRANSACTION_SELECT_FILE_REQUEST_CODE);
				}else{
					Toast.makeText(UiUtils.getContext(), "SD卡不可用", 0).show();
				}
			}
		});
		
		/**发送人*/
		btnSender.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				processTransmitClick();
			}
		});
		
		/***提交数据*/
		transmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String zipDir = SDCardUtils.getInstance().getZipDir() + MobileOAConstant.ZIP_FILE_NAME;
//				final String zipDir = SDCardUtils.getInstance().getZipDir() + "移动OA问题20160429.doc";
				System.out.println("zipDir = " + zipDir);
				title = etTitle.getText().toString().trim();
				description = etDescription.getText().toString().trim();
				ThreadManager.getInstance().createLongPool().execute(new Runnable() {
					
					public void run() {
						if (NetUtils.checkNetwork(activity)) {
							if (!title.equals("") && !description.equals("")) {
								if(nameList!=null && nameList.size() >0 && fileNameList!=null && fileNameList.size()>0){
									UrlParamsEntity urlParamsEntity = new UrlParamsEntity();
									urlParamsEntity.setMethodName(MobileOAConstant.FILE_SEND_ADDDOC);
									
									LinkedHashMap<String,String> paramsHashMap=new LinkedHashMap<String, String>();
									paramsHashMap.put("tranCode", "tran00019");
									paramsHashMap.put("document", getRecipientParentContentDatas(peopleNodes));
									paramsHashMap.put("fileInfo", "");
									paramsHashMap.put("docuser", "");
									urlParamsEntity.setParamsHashMap(paramsHashMap);
									Intent intent = new Intent(activity,LoadingActivity.class);
									intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.FileTransSendFileProtocol");
									intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY,"发送中");
									intent.putExtra("url",MobileOAConstant.UPLOAD_URL);
									intent.putExtra("zipDir", zipDir);
									intent.putExtra(MobileOAConstant.LOADING_IS_PREVENT_BACK, true);
									intent.putExtra("urlParamsEntity", urlParamsEntity);
									fileTransactionFragment.startActivityForResult(intent, MobileOAConstant.FILR_SEND_REQUEST_CODE);
								}else{
									UiUtils.showToast("附件或发送人为空");
								}
							}else{
								UiUtils.showToast("标题或描述为空");
							}
						}else{
							UiUtils.showToast("请检查网络！");
						}
					}
				});

			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//进行将所有的内容进行清空
				if(nameAdapter!=null){
					nameList.clear();
					nameAdapter.notifyDataSetChanged();
				}
				
				if(fileNameAdapter!=null){
					fileNameList.clear();
					fileNameAdapter.notifyDataSetChanged();
				}
				
				//进行将标题及描述内容清空
				etTitle.setText("");
				etDescription.setText("");
			}
		});
	}
	
	
	
	/**
	 * 进行获取参数集合请求网络
	 * @param peopleNodes
	 * @return
	 * @throws ContentException 
	 */
	protected String getRecipientParentContentDatas(List<TreeNode> peopleNodes){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("title",title);
			jsonObject.put("description", description);
			jsonObject.put("sender", MobileOaApplication.user.getRealName());
			String recipientNameDatas=getRecipientDatas(peopleNodes);
			jsonObject.put("recipient", recipientNameDatas);
			jsonObject.put("sendid", MobileOaApplication.user.getUserId());
			jsonObject.put("list", getRecipientContentDatas(peopleNodes));
			return jsonObject.toString();
		} catch (Exception e) {
			System.out.println("e.getMessage()" + e.getMessage());
			try {
				throw new ContentException("数据格式出错!");
			} catch (ContentException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 进行获取接收人的数据的集合
	 * @param peopleNodes 
	 * @return
	 */
	private String getRecipientDatas(List<TreeNode> peopleNodes) {
		StringBuilder sb=new StringBuilder();
		for(TreeNode data:peopleNodes){
			sb.append(data.getNodeName()+MobileOAConstant.CONSTANT_NODE_NAME_DELEGATE);
		}
		//进行删除最后一个","
		sb.deleteCharAt(sb.lastIndexOf(MobileOAConstant.CONSTANT_NODE_NAME_DELEGATE));
		return sb.toString();
	}
	
	/**
	 * 进行获取接收者详情内容的数据集合
	 * [{"id":1038,"nodeName":"徐燕","nodeType":"emp"}]
	 * @param peopleNodes
	 * @return
	 * @throws ContentException 
	 */
	private JSONArray getRecipientContentDatas(List<TreeNode> peopleNodes) throws ContentException {
		// TODO Auto-generated method stub
		try {
			JSONArray jsonArray=new JSONArray();
			for(TreeNode data:peopleNodes){
				JSONObject jsonObject = new JSONObject();
				BigDecimal mBigDecimal = new BigDecimal(data.getNodeId());
				jsonObject.put("id", mBigDecimal);
				jsonObject.put("nodeName",data.getNodeName());
				jsonObject.put("nodeType", "emp");
				jsonArray.put(jsonObject);
			}
			return jsonArray;
		} catch (Exception e) {
			System.out.println("e.getMessage()" + e.getMessage());
			throw new ContentException("数据格式出错!");
		}
	}
	
	
	


	/***选择发送人*/
	protected void processTransmitClick() {
		
		try {
			List<Node> nodeList = NodeDao.getInstance().getNodeList();
			System.out.println("processTransmitClick datas:"+nodeList.size()+"..toString:"+nodeList.toString());
			FileTreePopup.getInstance().setOkListener(new OnFileTreePopupClickListenter() {
				

				@Override
				public void onOkClickListener() {
					//点击确定的按钮时:进行获取数据，进行判断是否选择了数据
					List<TreeNode> nodeList=FileTreePopup.getInstance().getCheckedTreeNodes();
					if(nodeList!=null && nodeList.size()>0){
						peopleNodes = new ArrayList<TreeNode>();
						for(TreeNode data:nodeList){
							if(!data.isIdDeptFlag()){
								peopleNodes.add(data);
							}
						}
						if (peopleNodes.size() > 0) {
							getRecipientDatas(peopleNodes);
							FileTreePopup.getInstance().dismisss();
						}else{
							UiUtils.showToast("请选择发送人");
						}
					}else{
						UiUtils.showToast("请选择发送人");
					}	
				}
				
				/**
				 * 进行获取接收人的数据的集合
				 * @param peopleNodes 
				 * @return
				 */
				private String getRecipientDatas(List<TreeNode> peopleNodes) {
					StringBuilder sb=new StringBuilder();
					for(TreeNode data:peopleNodes){
						sb.append(data.getNodeName()+MobileOAConstant.CONSTANT_NODE_NAME_DELEGATE);
					}
					//进行删除最后一个","
					sb.deleteCharAt(sb.lastIndexOf(MobileOAConstant.CONSTANT_NODE_NAME_DELEGATE));
					System.out.println("sb.toString() = " + sb.toString());
					getPeopleName(sb.toString());
					return sb.toString();
				}
				
				@Override
				public void onCancelClickListener() {
					
				}
			});
			FileTreePopup.getInstance().showPopupFileList(activity,transmit,nodeList);
		} catch (ContentException e) {
			e.printStackTrace();
			String title = "是否同步用户?";
			LogoutPopup.getInstance().setListener(new OnFileTreePopupClickListenter() {
				
				@Override
				public void onOkClickListener() {
					LogoutPopup.getInstance().dismiss();
					Intent intent = new Intent(activity,LoadingActivity.class);
					intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol");
					intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY, "数据同步中");
					intent.putExtra(MobileOAConstant.LOADING_IS_PREVENT_BACK, true);
					UrlParamsEntity urlParamsEntity = new UrlParamsEntity();
					urlParamsEntity.setMethodName(MobileOAConstant.SYNBASICRESOURCE);
					LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
					map.put("tranCode", "tran00002");
					urlParamsEntity.setParamsHashMap(map);
					intent.putExtra("urlParamsEntitiy", urlParamsEntity);
					fileTransactionFragment.startActivityForResult(intent,MobileOAConstant.SYNCHRONIZE_DATA_REQUEST_CODE);
				}
				
				@Override
				public void onCancelClickListener() {
					LogoutPopup.getInstance().dismiss();
				}
			});
			LogoutPopup.getInstance().showPopupLogout(UiUtils.getContext(), btnSender, title);
		}
	}
	
	/**显示发送人的列表**/
	protected void getPeopleName(String string) { 
		String[] name = string.split(",");
		if(nameList!=null){
			nameList.clear();
		}else{
			nameList = new ArrayList<String>();
		}
		
		for (int i = 0; i < name.length; i++) {
			nameList.add(name[i]);
		}
		
		if(nameAdapter==null){
			nameAdapter = new NameAdapter(nameList);
			nameListView.setAdapter(nameAdapter);
		}else{
			nameAdapter.setDatas(nameList);
			nameAdapter.notifyDataSetChanged();
		}
		
	}


	/***接收人显示的ListView的适配器**/
	class NameAdapter extends BaseAdapter{

		private List<String> datas;
		private DeleteClickListener listener;
		
		
		public DeleteClickListener getListener() {
			return listener;
		}

		public void setListener(DeleteClickListener listener) {
			this.listener = listener;
		}

		public NameAdapter(List<String> datas) {
			super();
			this.datas = datas;
		}

		
		
		public List<String> getDatas() {
			return datas;
		}

		public void setDatas(List<String> datas) {
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas != null ? datas.size() : 0;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(UiUtils.getContext(), R.layout.listview_item_name_list, null);
				holder = new ViewHolder();
				holder.tv_Name = (TextView) convertView.findViewById(R.id.listview_name);
				holder.delete_Name = (ImageView) convertView.findViewById(R.id.btn_name);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_Name.setText(datas.get(position));
			holder.delete_Name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					datas.remove(position);
					if(listener!=null){
						listener.deleteClick(position);
					}
					notifyDataSetChanged();
				}
			});
			return convertView;
		}
		class ViewHolder{
			TextView tv_Name;
			ImageView delete_Name;
		}
	}
	
	
	/**
	 * 进行增加文件
	 */
	public void addFile(String fileName){
		System.out.println("fileName = " + fileName);
		if(fileNameList==null){
			fileNameList=new ArrayList<String>();
			selectedFiles=new ArrayList<File>();
			/**显示文件的名字*/
			String[] split = fileName.split("/");
			String name = split[(split.length)-1];
			fileNameList.add(name);
			selectedFiles.add(new File(fileName));
			//需要进行一次压缩文件
			createZip(MobileOAConstant.ZIP_FILE_NAME,SDCardUtils.getInstance().getZipDir(),selectedFiles);
			fileNameAdapter=new NameAdapter(fileNameList);
			fileNameAdapter.setListener(new DeleteClickListener() {
				
				@Override
				public void deleteClick(int position) {
					selectedFiles.remove(position);
					createZip(MobileOAConstant.ZIP_FILE_NAME,SDCardUtils.getInstance().getZipDir(),selectedFiles);
				}
			});
			sendFileListView.setAdapter(fileNameAdapter);
		}else{
			if(!fileNameList.contains(fileName)){
				/**显示文件的名字*/
				String[] split = fileName.split("/");
				String name = split[(split.length)-1];
				fileNameList.add(name);
				selectedFiles.add(new File(fileName));
				createZip(MobileOAConstant.ZIP_FILE_NAME,SDCardUtils.getInstance().getZipDir(),selectedFiles);
				fileNameAdapter.notifyDataSetChanged();
			}
		}
	}
	
    private void createZip(String filename, String temp_path,
		      List<File> list) {
		    File file = new File(temp_path);
		    System.err.println(temp_path);
		    File zipFile = new File(temp_path+File.separator+filename);
		    InputStream input = null;
		        try {
		    ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile,false));
		    zipOut.setEncoding("gbk");
		    zipOut.setComment(file.getName());
		            if (file.isDirectory()) {
		                for (int i = 0; i < list.size(); ++i) {
		                    input = new FileInputStream(list.get(i));
		                    zipOut.putNextEntry(new ZipEntry(list.get(i).getName()));
		                    int temp = 0;
		                    while ((temp = input.read()) != -1) {
		                        zipOut.write(temp);
		                    }
		                    input.close();
		                }
		            }
		            zipOut.close();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	 }
    
     public interface DeleteClickListener{
    	 void deleteClick(int position);
     }
}
