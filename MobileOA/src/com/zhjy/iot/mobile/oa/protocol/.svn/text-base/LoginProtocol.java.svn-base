package com.zhjy.iot.mobile.oa.protocol;

import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.entity.LoginUser;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;

public class LoginProtocol extends BaseProtocol<LoginUser> {

	@Override
	protected LoginUser parseJson(String jsonStr) throws JsonParseException,
			ContentException {
		// TODO Auto-generated method stub
		return null;
	}

	/* 
	 */
	@Override
	protected LoginUser parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		System.out.println("right result:"+String.valueOf(soapObject.toString()));
		//进行解析逻辑的发生
		LoginUser loginUser=null;
		//进行判断
		if(soapObject.hasProperty("status")){
			Object status=soapObject.getProperty("status");
			if(status.toString().equals("1")){
				loginUser=new LoginUser();
				if(!soapObject.hasProperty("realName") || !soapObject.hasProperty("userId") || !soapObject.hasProperty("loginFlag")|| !soapObject.hasProperty("operatorId")){
					throw new ContentException("传输数据失败!");
				}else{
					Object realName= soapObject.getProperty("realName");
					Object userId= soapObject.getProperty("userId");
					Object loginFlag=soapObject.getProperty("loginFlag");
					Object operationId=soapObject.getProperty("operatorId");
					if(Boolean.parseBoolean(loginFlag.toString().trim())){
						//此时进行封装数据
						System.out.println("realName.toString()=" + realName.toString());
						System.out.println("userId.toString()=" + userId.toString());
						System.out.println("operationId.toString()=" + operationId.toString());
						loginUser.setRealName(realName.toString());
						loginUser.setUserId(userId.toString());
						loginUser.setOperationId(operationId.toString());
						return loginUser;
					}else{
						if(soapObject.hasProperty("loginMsg")){
							Object loginMsg=soapObject.getProperty("loginMsg");
							throw new ContentException(loginMsg.toString().trim());
						}else{
							throw new ContentException("传输数据失败!");
						}
					}
				}
			}else{
				//如果不是就是失败
				Object msg=soapObject.getProperty("msg");
				if(msg==null){
					throw new ContentException("传输数据失败!");
				}else{
					throw new ContentException(msg.toString());
				}
			}
		}else{
			throw new ContentException("传输数据失败!");
		}
	}
}
