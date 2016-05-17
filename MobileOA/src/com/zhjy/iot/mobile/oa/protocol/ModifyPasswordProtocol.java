package com.zhjy.iot.mobile.oa.protocol;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ModifyPasswordProtocol   
 * @类描述：修改密码的协议类   
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-15 下午2:05:12  
 * @version    
 *
 */
public class ModifyPasswordProtocol extends BaseProtocol<String> {

	@Override
	protected String parseJson(String jsonStr) throws JsonParseException,
			ContentException {
		return null;
	}
	
	
	@Override
	protected String parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		System.out.println("right result:"+String.valueOf(soapObject.toString()));
		if (soapObject.hasProperty("status") && soapObject.hasProperty("msg")) {
			if(soapObject.getProperty("status").toString().equals("1")){
				
			}else{
				throw new ContentException(soapObject.getProperty("msg").toString());
			}
		}else{
			throw new ContentException("传输数据出错!");
		}
		return null;
	}
}
