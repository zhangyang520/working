package com.zhjy.iot.mobile.oa.protocol;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
/***
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransDeleteProtocol   
 * @类描述：   文件传阅中删除数据的操作
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-12 下午2:36:35  
 * @version    
 *
 */
public class FileTransDeleteProtocol extends BaseProtocol<FileTransaction> {

	private String actionName="";
	
	@Override
	protected FileTransaction parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		
		return null;
	}
	
	
	@Override
	protected FileTransaction parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		
		System.out.println("FileTransDeleteProtocol soapObject toString:"+soapObject.toString());
		if(soapObject.hasProperty("status") && soapObject.hasProperty("msg")){
			if(soapObject.getProperty("status").toString().equals("1")){
				
			}else{
				throw new ContentException(soapObject.getProperty("msg").toString());
			}
		}else{
			throw new ContentException("传输数据出错!");
		}
		return null;
	}
	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}
