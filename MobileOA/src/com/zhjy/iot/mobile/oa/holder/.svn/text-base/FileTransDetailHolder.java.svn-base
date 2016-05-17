package com.zhjy.iot.mobile.oa.holder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.activity.LoadingActivity;
import com.zhjy.iot.mobile.oa.adapter.FileTransHolderListViewAdapter;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.FileTransactionDetail;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.popup.FileAppendPopup;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup.OnFileTreePopupClickListenter;
import com.zhjy.iot.mobile.oa.popup.LogoutPopup;
import com.zhjy.iot.mobile.oa.tree.TreeNode;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.NetUtils;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * @项目名：MobileOA 
 * @类名称：FileTransHolder   
 * @类描述：   文件传阅 详情页的Holder
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-1 上午11:42:29  
 * @version    
 */
public class FileTransDetailHolder extends BaseHolder<FileTransactionDetail> implements OnClickListener{

	private View contentView;
	private TextView title;
	private TextView summary;
	private TextView sender;
	private TextView sendTime;
	private Button transmit;
	private Button appendDoc;
	private ListView listView;

	private Activity activity;
	private boolean isYifa;
	
	
	private String docmentId;//文档的id 需要进行传递进来的
	
	public String getDocmentId() {
		return docmentId;
	}

	public void setDocmentId(String docmentId) {
		this.docmentId = docmentId;
	}

	public FileTransDetailHolder(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public View initView() {
		contentView = UiUtils.inflate(R.layout.file_form_file_trans);
		title = (TextView) contentView.findViewById(R.id.title);
		summary = (TextView) contentView.findViewById(R.id.summary);
		sender = (TextView) contentView.findViewById(R.id.sender);
		sendTime = (TextView) contentView.findViewById(R.id.send_time);
		transmit = (Button) contentView.findViewById(R.id.transmit);
		appendDoc = (Button) contentView.findViewById(R.id.AppendDoc);
		listView = (ListView) contentView.findViewById(R.id.listView);
		
		transmit.setOnClickListener(this);
		appendDoc.setOnClickListener(this);
		return contentView;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void refreshView(FileTransactionDetail data) {
		
		System.out.println("FileTransDetailHolder refreshView isYifa:"+isYifa);
		//进行根据 isYifa类型进行判断是否是已发类型
		if(isYifa){
			//如果是已发类型,进行隐藏按钮
			transmit.setClickable(false);
			transmit.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_gray_bg));
		}else{
			//不是已发类型,进行显示按钮btn_four_form_selector_bg
			transmit.setClickable(true);
			transmit.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_four_form_selector_bg));
		}
		title.setText(data.getTitle());
		summary.setText(data.getContent());
		sender.setText(data.getSendPerson());
		sendTime.setText(data.getSendTime());
		FileTransHolderListViewAdapter adapter = new FileTransHolderListViewAdapter(data.getReceivePeople(), listView);
		listView.setAdapter(adapter);
	}

	public boolean isYifa() {
		return isYifa;
	}

	public void setYifa(boolean isYifa) {
		this.isYifa = isYifa;
	}
	
	/**
	 * 进行重新回调转发按钮的启动
	 */
	public void recallTransmitClick(){
		processTransmitClick();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.transmit:
			processTransmitClick();
			break;
			
		case R.id.AppendDoc:
			//进行弹出文件框
			//如果有,进行跳转附件提示框
			if(getData().getAppendDocSet()!=null && getData().getAppendDocSet().size()>=1){
				FileAppendPopup.getInstance().showPopupFileList(activity, appendDoc,getData().getAppendDocSet());
			}else{
				Toast.makeText(UiUtils.getContext(),"附件为空!", 0).show();
			}
			break;
		}
	}
	
	/**
	 * 进行处理传阅的点击逻辑
	 */
	private void processTransmitClick(){
		try {
			
			//进行获取集合数据
			List<Node> datas=NodeDao.getInstance().getNodeList();
			
			
			
			System.out.println("processTransmitClick datas:"+datas.size()+"..toString:"+datas.toString());
			FileTreePopup.getInstance().setOkListener(new OnFileTreePopupClickListenter() {
				
				@Override
				public void onOkClickListener() {
					if (NetUtils.checkNetwork(activity)) {
						//点击确定的按钮时:进行获取数据，进行判断是否选择了数据
						List<TreeNode> datas=FileTreePopup.getInstance().getCheckedTreeNodes();
						
						if(datas!=null && datas.size()>0){
							List<TreeNode> peopleNodes=new ArrayList<TreeNode>();
							for(TreeNode data:datas){
								if(!data.isIdDeptFlag()){
									peopleNodes.add(data);
								}
							}
							
							//进行判断人员的集合的长度
							if(peopleNodes.size()>0){
								try {
									FileTreePopup.getInstance().dismisss();
									//进行提示访问网络
									Intent intent=new Intent(UiUtils.getContext(),LoadingActivity.class);
									intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.FileTrans2PeopleProtocol");
									intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY,"文件传阅中...");
									UrlParamsEntity urlParamsEntity=new UrlParamsEntity();
//							    	后台的方法名(后台定义好的)
									urlParamsEntity.setMethodName(MobileOAConstant.FILE_TRANS_TRANCODES_POND);
									//(参数要有顺序)
									LinkedHashMap<String,String> paramsHashMap=new LinkedHashMap<String, String>();
									paramsHashMap.put("tranCode", "tran00014");
									paramsHashMap.put("document", getRecipientParentContentDatas(peopleNodes));
									paramsHashMap.put("docuser", null);
									
									//FIXME 增加其他的参数信息
									urlParamsEntity.setParamsHashMap(paramsHashMap);
									System.out.println("docmentId = " + docmentId + ";title = " + getData().getTitle() + ";description = " + getData().getContent() + ";sender = " + MobileOaApplication.user.getRealName());
									intent.putExtra("urlParamsEntitiy", urlParamsEntity);
									
									activity.startActivityForResult(intent, MobileOAConstant.FILE_TRANS_TRANSFILE_REQUEST_CODE);
								} catch (ContentException e) {
									e.printStackTrace();
									UiUtils.showToast(e.getMessage());
								}
							}else{
								//提示用户:选择传阅人员
								Toast.makeText(UiUtils.getContext(), "请选择传阅人员!", 0).show();
							}
						}else{
							//提示用户:选择传阅人员
							Toast.makeText(UiUtils.getContext(), "请选择传阅人员!", 0).show();
						}
					
					}else{
						UiUtils.showToast("请检查网络！");
					}
				}
				
				
				/**
				 * 进行获取参数集合方法
				 * @param peopleNodes
				 * @return
				 * @throws ContentException 
				 */
				private String getRecipientParentContentDatas(List<TreeNode> peopleNodes) throws ContentException {
					// TODO Auto-generated method stub
					try {
						JSONObject jsonObject = new JSONObject();
						BigDecimal mBigDecimal = new BigDecimal(docmentId);
						jsonObject.put("id", mBigDecimal);
						jsonObject.put("title", getData().getTitle());
						jsonObject.put("description", getData().getContent());
						jsonObject.put("sender", MobileOaApplication.user.getRealName());
						String recipientNameDatas=getRecipientDatas(peopleNodes);
						jsonObject.put("recipient", recipientNameDatas);
						jsonObject.put("sendid", MobileOaApplication.user.getUserId());
						jsonObject.put("list", getRecipientContentDatas(peopleNodes));
						return jsonObject.toString();
					} catch (Exception e) {
						System.out.println("e.getMessage()" + e.getMessage());
						throw new ContentException("数据格式出错!");
					}
				}

				/**
				 * 进行获取接收者详情内容的数据集合
				 * [{"id":1038,"nodeName":"徐燕","nodeType":"emp"}]
				 * @param peopleNodes
				 * @return
				 * @throws ContentException 
				 */
				private JSONArray getRecipientContentDatas(
						List<TreeNode> peopleNodes) throws ContentException {
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

				@Override
				public void onCancelClickListener() {
					//取消按钮时,什么也不需要操作!
				}
			});
			FileTreePopup.getInstance().showPopupFileList(activity,transmit,NodeDao.getInstance().getNodeList());
		} catch (ContentException e){
			//进行获取数据失败:怎么办,进行提示用户需要更新数据
			LogoutPopup.getInstance().setListener(new OnFileTreePopupClickListenter() {
				
				@Override
				public void onOkClickListener() {
					LogoutPopup.getInstance().dismiss();
					//确定怎么做:访问网络，进行同步用户数据
					Intent intent=new Intent(UiUtils.getContext(),LoadingActivity.class);
					intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.SynchronizeDataProtocol");
					intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY,"同步用户数据中...");
					intent.putExtra(MobileOAConstant.LOADING_IS_PREVENT_BACK,true);
					UrlParamsEntity urlParamsEntity=new UrlParamsEntity();
					//后台的方法名(后台定义好的)
					urlParamsEntity.setMethodName(MobileOAConstant.SYNBASICRESOURCE);
					//(参数要有顺序)
					LinkedHashMap<String,String> paramsHashMap=new LinkedHashMap<String, String>();
					paramsHashMap.put("tranCode", "tran00002");
					
					urlParamsEntity.setParamsHashMap(paramsHashMap);
					intent.putExtra("urlParamsEntitiy", urlParamsEntity);
					activity.startActivityForResult(intent, MobileOAConstant.SYNCHRONIZE_DATA_REQUEST_CODE);
					
				}
				
				@Override
				public void onCancelClickListener() {
					//取消怎么做:
					LogoutPopup.getInstance().dismiss();
				}
			});
			LogoutPopup.getInstance().showPopupLogout(activity, transmit, "需要同步用户数据?");
			e.printStackTrace();
		}
	}
}
