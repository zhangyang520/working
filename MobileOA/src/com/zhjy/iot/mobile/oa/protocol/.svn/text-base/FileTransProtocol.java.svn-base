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
/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransProtocol   
 * @类描述：   文件传阅数据解析协议(普通和重要之间数据解析)
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-22 下午2:54:54  
 * @version    
 *
 */
public class FileTransProtocol extends BaseProtocol<List<FileTransaction>> {

	List<FileTransaction> datas;
	FileTransaction mFileTransaction;
	@Override
	protected List<FileTransaction> parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		if (datas == null) {
			datas = new ArrayList<FileTransaction>();
			/*datas.add(new FileTransaction("付饶", "海淀国资国企动态（专刊）第7期", true, null, 1));
			datas.add(new FileTransaction("周建军", "海淀国资国企动态（专刊）第7期", true, null, 1));
			datas.add(new FileTransaction("周建军", "海淀国资国企动态（专刊）第6期", true, null, 1));
			datas.add(new FileTransaction("齐梦婕", "践行“三严三实”深化作风建设以忠诚干净担当精神文明建设", true, null, 1));
			datas.add(new FileTransaction("周建军", "海淀国资国企动态（普刊）第5期", true, null, 1));
			datas.add(new FileTransaction("黄依彦", "关于工业公司下属企业风机二厂刘长鑫等4位同志的", true, null, 1));
			datas.add(new FileTransaction("付饶", "海淀国资国企动态（专刊）第2期", true, null, 1));
			datas.add(new FileTransaction("周建军", "海淀国资国企动态（专刊）第7期", true, null, 1));
			datas.add(new FileTransaction("付饶", "海淀国资国企动态（专刊）第7期", true, null, 1));
			datas.add(new FileTransaction("周建军", "海淀国资国企动态（专刊）第6期", true, null, 1));
			datas.add(new FileTransaction("齐梦婕", "践行“三严三实”深化作风建设以忠诚干净担当精神文明建设", true, null, 1));
			datas.add(new FileTransaction("周建军", "海淀国资国企动态（普刊）第5期", true, null, 1));
			datas.add(new FileTransaction("黄依彦", "关于工业公司下属企业风机二厂刘长鑫等4位同志的", true, null, 1));
			datas.add(new FileTransaction("付饶", "海淀国资国企动态（专刊）第2期", true, null, 1));*/
		}
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
			String total = soapObject.getProperty("total").toString();
			System.out.println("total = " + total);
			
			for (int i = 0; i < soapObject.getPropertyCount(); i++) {
				data = soapObject.getProperty(i);
				if (data instanceof SoapPrimitive) {
					
				}else if(data instanceof SoapObject){
					
					if (((SoapObject) data).hasProperty("msg")) {
						
					}else if(((SoapObject) data).hasProperty("id")){
						if (!((SoapObject) data).hasProperty("id") || !((SoapObject) data).hasProperty("documentid") || !((SoapObject) data).hasProperty("sender") || !((SoapObject) data).hasProperty("title") || !((SoapObject) data).hasProperty("isreading") || !((SoapObject) data).hasProperty("importance")) {
							System.out.println("!((SoapObject) data).hasProperty(id)" + ((SoapObject) data).hasProperty("id"));
							System.out.println("!((SoapObject) data).hasProperty(documentid)" + ((SoapObject) data).hasProperty("documentid"));
							System.out.println("!((SoapObject) data).hasProperty(sender)" + ((SoapObject) data).hasProperty("sender"));
							System.out.println("!((SoapObject) data).hasProperty(title)" + ((SoapObject) data).hasProperty("title"));
							System.out.println("!((SoapObject) data).hasProperty(isreading)" + ((SoapObject) data).hasProperty("isreading"));
							System.out.println("!((SoapObject) data).hasProperty(importance)" + ((SoapObject) data).hasProperty("importance"));
							throw new ContentException("数据传输有误!");
						}else{
							mFileTransaction = new FileTransaction();
							Object id = ((SoapObject) data).getProperty("id");
							String documentUserid = String.valueOf(id);
							
							Object sender = ((SoapObject) data).getProperty("sender");
							Object title = ((SoapObject) data).getProperty("title");
							Object isreading = ((SoapObject) data).getProperty("isreading");
							
							Object documentid = ((SoapObject) data).getProperty("documentid");
							String documentId = String.valueOf(documentid);
							
							Object importance = ((SoapObject) data).getProperty("importance");
							
							
							mFileTransaction.setDocumentUserId(documentUserid.toString());
							mFileTransaction.setSender(sender.toString());
							mFileTransaction.setTitle(title.toString());
							if ("1".equals(isreading.toString())) {
								mFileTransaction.setReading(true);
							}else{
								mFileTransaction.setReading(false);
							}
							mFileTransaction.setDocumentId(documentId);
							if ("0".equals(importance.toString())) {
								mFileTransaction.setImportance(FileTransactionState.ORDINARY.getValue());
							}else{
								mFileTransaction.setImportance(FileTransactionState.INPORTANT.getValue());
							}
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
