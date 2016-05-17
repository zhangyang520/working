package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.AppendDoc;
import com.zhjy.iot.mobile.oa.entity.FileTransactionDetail;
import com.zhjy.iot.mobile.oa.entity.ReceivePeople;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
/**
 * 
 * @项目名：MoblieOA
 * @类名称：FileTransActivityProtocol
 * @类描述：文件传阅的详情实体的解析
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-26 下午3:13:49
 * @version 1.1
 * 
 */
public class FileTransActivityProtocol extends BaseProtocol<FileTransactionDetail> {

	FileTransactionDetail mFileTransactionDetail;
	private List<ReceivePeople> list = new ArrayList<ReceivePeople>();
	ReceivePeople mReceivePeople ;
	private SoapObject successSoapObject;
	
	@Override
	protected FileTransactionDetail parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		System.out.println("FileTransactionDetail.JsonStr = " + jsonStr);
		mFileTransactionDetail = new FileTransactionDetail();
		try {
			JSONObject json = new JSONObject(jsonStr);
			String valuesStr = json.getString("values");
			JSONObject jsonValues = new JSONObject(valuesStr);
			String statusStr = jsonValues.getString("status");
			String msgStr = jsonValues.getString("msg");
			String infoStr = json.getString("info");
			
			JSONObject jsonObject = new JSONObject(infoStr);
			
			String title = jsonObject.getString("title");
			mFileTransactionDetail.setTitle(title);

			String content = jsonObject.getString("content");
			mFileTransactionDetail.setContent(content);
			System.out.println("content = " + content);

			String sendPerson = jsonObject.getString("sendPerson");
			mFileTransactionDetail.setSendPerson(sendPerson);
			System.out.println("sendPerson = " + sendPerson);

			String receivePeople = jsonObject.getString("receivePeople");
			System.out.println("FileTransActivityProtocol......receivePeople = " + receivePeople);
			
			JSONArray jsonArray = new JSONArray(receivePeople);
			for (int i = 0; i < jsonArray.length(); i++) {
				mReceivePeople = new ReceivePeople();
				JSONObject job = jsonArray.getJSONObject(i);
				String name = job.getString("name");
				mReceivePeople.setName(name);
				list.add(mReceivePeople);
			}
			mFileTransactionDetail.setReceivePeople(list);

			return mFileTransactionDetail;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new JsonParseException();
		}
	}


	
	/**
	 * 进行解析soapObject
	 */
	@Override
	public FileTransactionDetail parseJson(SoapObject soapObject)
			throws JsonParseException,ContentException{
		successSoapObject=soapObject;
		if (soapObject.hasProperty("status") && "0".equals(soapObject.getProperty("status").toString())) {
			if (soapObject.hasProperty("msg")) {
				throw new ContentException(soapObject.getProperty("msg").toString());
			}else{
				throw new ContentException("数据传输有误");
			}
		}else if(soapObject.hasProperty("status") && "1".equals(soapObject.getProperty("status").toString()) && 
				soapObject.hasProperty("documentInfo")){
			SoapObject soapObject2=(SoapObject) soapObject.getProperty("documentInfo");
			if(!soapObject2.hasProperty("id")|| !soapObject2.hasProperty("title")||
			   !soapObject2.hasProperty("description") || !soapObject2.hasProperty("sender")||
			   !soapObject2.hasProperty("recipient") || !soapObject2.hasProperty("senddtime")||
			   !soapObject2.hasProperty("oaDocumentUsers") ||!soapObject2.hasProperty("sendid")){
				
				// !soapObject.hasProperty("attachmentInfos") ||
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("id"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("title"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("description"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("sender"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("recipient"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("senddtime"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("oaDocumentUsers") );
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("attachmentInfos"));
				System.out.println("!soapObject.hasProperty(id) :"+!soapObject2.hasProperty("sendid"));
				//System.out.println("!soapObject.hasProperty(docmentid) :"+!soapObject2.hasProperty("docmentid"));
				throw new ContentException("传输数据格式出错!");
			}else{
				//所在的字符键值存在
				//进行详情的解析
				mFileTransactionDetail = new FileTransactionDetail();
				//主题:
				String title=soapObject2.getProperty("title").toString();
				System.out.println("soapObject title:"+title);
				mFileTransactionDetail.setTitle(title);
				//描述内容
				String description=soapObject2.getProperty("description").toString();
				if(description.equals(MobileOAConstant.CONSTANT_NULL_SOAP_STRING)){
					mFileTransactionDetail.setContent(null);
				}
				//进行解析发送人的id:如果本地数据库没有这个人:需要进行提示进行同步数据sendid
				String sendid=soapObject2.getProperty("sendid").toString();
				NodeDao.getInstance().getNodeById(Double.valueOf(sendid).toString());
				
				//发送人sender
				String sender=soapObject2.getProperty("sender").toString();
				mFileTransactionDetail.setSendPerson(sender);
				//发送时间 senddtime
				String senddtime=soapObject2.getProperty("senddtime").toString();
				mFileTransactionDetail.setSendTime(DateUtil.dateFormat(DateUtil.parse2Date(senddtime)));
				//解析附加文档 需要进行遍历oaDocumentUsers attachmentInfos
				List<AppendDoc> appendDocs=new ArrayList<AppendDoc>();
				List<ReceivePeople> receivePeoples=new ArrayList<ReceivePeople>();
				if(soapObject.hasProperty("attachmentInfos") ){
					for(int i=0;i<soapObject.getPropertyCount();++i){
						Object obj=soapObject.getProperty(i);
						if(obj instanceof SoapObject){
							if(((SoapObject)obj).hasProperty("id") && ((SoapObject)obj).hasProperty("originalName")){
								String docId=((SoapObject)obj).getProperty("id").toString();
								String originalName=((SoapObject)obj).getProperty("originalName").toString();
								AppendDoc doc=new AppendDoc(originalName, docId);
								appendDocs.add(doc);
							}
						}
					}
				}
				
				//进行解析接受者集合
//				for(int i=0;i<soapObject2.getPropertyCount();++i){
//					Object obj=soapObject2.getProperty(i);
//					if(obj instanceof SoapObject && ((SoapObject)obj).hasProperty("recipientid")){
//						//如果包含接收人中接收人的id
//						String recipientid=((SoapObject)obj).getProperty("recipientid").toString();
//						Node node=NodeDao.getInstance().getNodeById(Double.valueOf(recipientid).toString());
//						ReceivePeople receivePeople=new ReceivePeople();
//						receivePeople.setName(node.getNodeName());
//						receivePeople.setTime(DateUtil.dateFormat(DateUtil.parse2Date(senddtime)));
//						receivePeoples.add(receivePeople);
//					}
//				}
				
				//进行解析recipient字段集合
				String recipients=soapObject2.getProperty("recipient").toString();
				if(recipients!=null && !"".equals(recipients.trim())){
					String[] receivePeopleStrs=recipients.split("\\,");
					if(receivePeopleStrs!=null && receivePeopleStrs.length>0){
						for(int j=0;j<receivePeopleStrs.length;++j){
							ReceivePeople receivePeople=new ReceivePeople();
							receivePeople.setName(receivePeopleStrs[j]);
							receivePeople.setTime(DateUtil.dateFormat(DateUtil.parse2Date(senddtime)));
							receivePeoples.add(receivePeople);
						}
					}
				}
				mFileTransactionDetail.setAppendDocSet(appendDocs);
				mFileTransactionDetail.setReceivePeople(receivePeoples);
				return mFileTransactionDetail;
			}
		}else{
			throw new ContentException("数据传输有误");
		}
		
	}

	public SoapObject getSuccessSoapObject() {
		return successSoapObject;
	}

	public void setSuccessSoapObject(SoapObject successSoapObject) {
		this.successSoapObject = successSoapObject;
	}
}
