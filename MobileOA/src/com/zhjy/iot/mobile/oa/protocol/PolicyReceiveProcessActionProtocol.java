package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
/**
 * 
 * @项目名：MobileOA
 * @类名称：PolicyReceiveProcessActionProtocol
 * @类描述：行政收文处理操作的协议类
 * @创建人：zhangyang
 * @修改人：zhangyang
 * @创建时间：2016-4-22 下午5:10:05
 * @version 1.1
 * 
 */
public class PolicyReceiveProcessActionProtocol extends BaseProtocol<String> {

	@Override
	protected String parseJson(String jsonStr) throws JsonParseException,
			ContentException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	protected String parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		//需要进行解析对应结果信息
		if (soapObject.hasProperty("status") && "0".equals(soapObject.getProperty("status").toString())) {
			if (soapObject.hasProperty("msg")) {
				throw new ContentException(soapObject.getProperty("msg").toString());
			}else{
				throw new ContentException("数据传输有误");
			}
		}
		return "";
	}
}
