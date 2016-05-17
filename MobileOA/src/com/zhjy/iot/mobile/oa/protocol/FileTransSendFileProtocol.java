package com.zhjy.iot.mobile.oa.protocol;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransSendFileProtocol   
 * @类描述：   对文件提交的协议类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-5-5 上午10:35:40  
 * @version    
 *
 */
public class FileTransSendFileProtocol extends BaseProtocol<String> {

	@Override
	protected String parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		
		return jsonStr;
	}

	
	@Override
	protected String parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
