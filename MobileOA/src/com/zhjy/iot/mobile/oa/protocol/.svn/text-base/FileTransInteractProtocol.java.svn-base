package com.zhjy.iot.mobile.oa.protocol;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
/**
 * @项目名：MobileOA
 * @类名称：FileTransInteractProtocol
 * @类描述：文件传阅中"普通"和重要之间进行互转
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-11下午5:38:09
 * @version
 */
public class FileTransInteractProtocol extends BaseProtocol<FileTransaction> {

	private String actionName="";
	
	@Override
	protected FileTransaction parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 进行解析内容
	 */
	@Override
	protected FileTransaction parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		
		System.out.println("FileTransInteractProtocol soapObject toString:"+soapObject.toString());
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
