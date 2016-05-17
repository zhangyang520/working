package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.entity.FileTransactionState;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
/***
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransYiFaProtocol   
 * @类描述：   文件传阅已发文件解析类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-11 下午3:03:58  
 * @version    
 *
 */
public class FileTransYiFaProtocol extends BaseProtocol<List<FileTransaction>> {

	
	List<FileTransaction> datas;
	FileTransaction mFileTransaction;
	
	
	@Override
	protected List<FileTransaction> parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		
		return null;
	}
	
	
	
	@Override
	protected List<FileTransaction> parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		System.out.println("FileTransProtocol parseJson soapObject:"+String.valueOf(soapObject.toString()));
		if (soapObject.hasProperty("status") && "0".equals(soapObject.getProperty("status").toString())) {
			if (soapObject.hasProperty("msg")) {
				throw new ContentException(soapObject.getProperty("msg").toString());
			}else{
				throw new ContentException("数据传输有误");
			}
		}else{
			Object data = null;
			datas = new ArrayList<FileTransaction>();
			String status = soapObject.getProperty("status").toString();
			String msg = soapObject.getProperty("msg").toString();
			
			for (int i = 0; i < soapObject.getPropertyCount(); i++) {
				data = soapObject.getProperty(i);
				if (data instanceof SoapPrimitive) {
					
				}else if(data instanceof SoapObject){
					
					if (((SoapObject) data).hasProperty("msg")) {
						
					}else if(((SoapObject) data).hasProperty("id")){
						if (!((SoapObject) data).hasProperty("id") || !((SoapObject) data).hasProperty("title") || !((SoapObject) data).hasProperty("description")|| !((SoapObject) data).hasProperty("sender") || !((SoapObject) data).hasProperty("attachment")  || !((SoapObject) data).hasProperty("recipient")|| !((SoapObject) data).hasProperty("senddtime")|| !((SoapObject) data).hasProperty("sendid")) {
							/*System.out.println("!((SoapObject) data).hasProperty(id)" + ((SoapObject) data).hasProperty("id"));
							System.out.println("!((SoapObject) data).hasProperty(title)" + ((SoapObject) data).hasProperty("title"));
							System.out.println("!((SoapObject) data).hasProperty(Sender)" + ((SoapObject) data).hasProperty("sender"));
							System.out.println("!((SoapObject) data).hasProperty(attachment)" + ((SoapObject) data).hasProperty("attachment"));
							System.out.println("!((SoapObject) data).hasProperty(recipient)" + ((SoapObject) data).hasProperty("recipient"));
							System.out.println("!((SoapObject) data).hasProperty(senddtime)" + ((SoapObject) data).hasProperty("senddtime"));
							System.out.println("!((SoapObject) data).hasProperty(sendid)" + ((SoapObject) data).hasProperty("sendid"));*/
							throw new ContentException("数据传输有误!");
						}else{
							mFileTransaction = new FileTransaction();
							Object id = ((SoapObject) data).getProperty("id");
							String documentid = String.valueOf(id);
							Object title = ((SoapObject) data).getProperty("title");
							Object sender = ((SoapObject) data).getProperty("sender");
							Object sendid = ((SoapObject) data).getProperty("sendid");
							
							mFileTransaction.setDocumentId(documentid.toString());
							mFileTransaction.setTitle(title.toString());
							mFileTransaction.setSender(sender.toString());
							mFileTransaction.setSenderId(sendid.toString());
							mFileTransaction.setImportance(FileTransactionState.YIFA.getValue());
							datas.add(mFileTransaction);
						}
					}
				}
			}
		}
		System.out.println("return FileTransProtocol （集合长度）datas size ::" + datas.size());
		return datas;
	}

}
