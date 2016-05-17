package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;
import java.util.List;

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
 * @类名称：ReceiveNotificationProtocol   
 * @类描述：   公文通知数据解析协议
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-22 下午2:50:52  
 * @version    
 *
 */
public class ReceiveNotificationProtocol extends BaseProtocol<List<PolicyReceiveFile>> {
	
	
	int i=0;
	List<PolicyReceiveFile> datas;
	PolicyReceiveFile mPolicyReceiveFile;
	int j=0;
	@Override
	protected List<PolicyReceiveFile> parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		datas = new ArrayList<PolicyReceiveFile>();
		datas.add(new PolicyReceiveFile(null, "北京市海淀区防火安全委员会关于安全防范知识的文件", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于印发“夏季专项行动”方案的专题会议", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "6月份全区流动人口和出租房屋的情况信息", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于加强信息报送的通知", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于北京海国鑫泰投资控股中心的研题报告", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于北京实创科技产业园区开发建设的研究", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "北京市海淀区防火安全委员会关于安全防范知识的文件", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于印发“夏季专项行动”方案的专题会议", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "6月份全区流动人口和出租房屋的情况信息", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于加强信息报送的通知", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于北京海国鑫泰投资控股中心的研题报告", "无", null, 0));
		datas.add(new PolicyReceiveFile(null, "关于北京实创科技产业园区开发建设的研究", "无", null, 0));
		return datas;
	}
	
	
	@Override
	protected List<PolicyReceiveFile> parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		System.out.println("PolicyReceProtocol parseJson soapObject:"+String.valueOf(soapObject.toString()));
		if (soapObject.hasProperty("status") && "0".equals(soapObject.getProperty("status").toString())) {
			if (soapObject.hasProperty("msg")) {
				throw new ContentException(soapObject.getProperty("msg").toString());
			}else{
				throw new ContentException("数据传输有误");
			}
		}else{
			Object data = null;
			datas = new ArrayList<PolicyReceiveFile>();
			String status = soapObject.getProperty("status").toString();
			String msg = soapObject.getProperty("msg").toString();
			for (int i = 0; i < soapObject.getPropertyCount(); i++) {
				data = soapObject.getProperty(i);
				if (data instanceof SoapPrimitive) {
					
				}else if (data instanceof SoapObject) {
					if (((SoapObject) data).hasProperty("msg")) {
						
					}else if (!((SoapObject) data).hasProperty("title") || 
							  !((SoapObject) data).hasProperty("sendtime") || 
							  !((SoapObject) data).hasProperty("processid") || 
							  !((SoapObject) data).hasProperty("notifid")) {
						System.out.println("!((SoapObject) data).hasProperty(title)" + ((SoapObject) data).hasProperty("title"));
						System.out.println("!((SoapObject) data).hasProperty(sendtime)" + ((SoapObject) data).hasProperty("sendtime"));
						System.out.println("!((SoapObject) data).hasProperty(processid)" + ((SoapObject) data).hasProperty("processid"));
					}else{
						mPolicyReceiveFile = new PolicyReceiveFile();
						
						String title = ((SoapObject) data).getProperty("title").toString();
						String receFileTime = ((SoapObject) data).getProperty("sendtime").toString();
						String processId = ((SoapObject) data).getProperty("processid").toString();
						String workItemId=((SoapObject) data).getProperty("notifid").toString();
						System.out.println("PolicyReceProtocol... title = " + title + "；receFileTime" + receFileTime);
						
						mPolicyReceiveFile.setTitle(title);
						mPolicyReceiveFile.setToUnit("无");
						mPolicyReceiveFile.setProcessId(processId);
						mPolicyReceiveFile.setWorkItemId(workItemId);
						mPolicyReceiveFile.setReceFileTime(DateUtil.dateFormat(DateUtil.parse2Date(receFileTime)));
						datas.add(mPolicyReceiveFile);
					}
				}
			}
		}
		
		return datas;
	}
	

}
