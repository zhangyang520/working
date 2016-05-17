package com.zhjy.iot.mobile.oa.protocol;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;

/**
 * 
 * @项目名：MobileOA
 * @类名称：FileTrans2PeopleProtocol
 * @类描述：文件传阅的传阅的协议类:继承FileTransDeleteProtocol删除的协议类：
 *        暂时将parseSoapObject的方法使用父类的方法,如果要有改进,自己定义
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-12 下午4:43:50
 * 
 * @version 1.1
 * 
 */
public class FileTrans2PeopleProtocol extends FileTransDeleteProtocol {
	
	
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

}
