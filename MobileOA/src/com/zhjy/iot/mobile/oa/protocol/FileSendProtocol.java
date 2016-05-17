package com.zhjy.iot.mobile.oa.protocol;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;

/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileSendProtocol   
 * @类描述：   文件传阅界面
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-5-3 下午5:43:06  
 * @version    
 *
 */
public class FileSendProtocol extends FileTransProtocol {
	
	@Override
	protected List<FileTransaction> parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		
		return null;
	}
	
	@Override
	protected List<FileTransaction> parseJson(SoapObject soapObject)
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
