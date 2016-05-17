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
 * @author zhangyang
 * 
 * @日期 2016-3-20下午2:45:34
 * 
 * @描述  行政收文的协议类
 */
public class PolicyReceProtocol extends BaseProtocol<List<PolicyReceiveFile>> {

	int i=0;
	List<PolicyReceiveFile> datas;
	PolicyReceiveFile mPolicyReceiveFile;
	int j=0;
	@Override
	protected List<PolicyReceiveFile> parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		datas = new ArrayList<PolicyReceiveFile>();
//		datas.add(new PolicyReceiveFile(null, "北京市海淀区防火安全委员会关于安全防范知识的文件", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于印发“夏季专项行动”方案的专题会议", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "6月份全区流动人口和出租房屋的情况信息", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于加强信息报送的通知", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于北京海国鑫泰投资控股中心的研题报告", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于北京实创科技产业园区开发建设的研究", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "北京市海淀区防火安全委员会关于安全防范知识的文件", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于印发“夏季专项行动”方案的专题会议", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "6月份全区流动人口和出租房屋的情况信息", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于加强信息报送的通知", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于北京海国鑫泰投资控股中心的研题报告", "无", getDate(), 0));
//		datas.add(new PolicyReceiveFile(null, "关于北京实创科技产业园区开发建设的研究", "无", getDate(), 0));
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
			List<String> strLists=null;
			//如果有sendName的情况下
			if(soapObject.hasProperty("senderName")){
				//进行获取senderName的值
				data=soapObject.getProperty("senderName");
				strLists=new ArrayList<String>();
				for(int i=0;i<((SoapObject)data).getPropertyCount();i++){
					strLists.add(((SoapObject)data).getProperty(i).toString());
				}
			}
			int j=0;
			for (int i = 0; i < soapObject.getPropertyCount(); i++) {
				data = soapObject.getProperty(i);
				if (data instanceof SoapPrimitive) {
					
				}else if (data instanceof SoapObject) {
					
					if(((SoapObject) data).hasProperty("workItemID") && 
									((SoapObject) data).hasProperty("workItemID") && 
											((SoapObject) data).hasProperty("processInstName") && 
														((SoapObject) data).hasProperty("startTime")) {
						
						mPolicyReceiveFile = new PolicyReceiveFile();
						
						String workItemId = ((SoapObject) data).getProperty("workItemID").toString();
						String title = ((SoapObject) data).getProperty("processInstName").toString();
						String receFileTime = ((SoapObject) data).getProperty("startTime").toString();
						
						if(strLists!=null && strLists.size()>=j){
							mPolicyReceiveFile.setToUnit(strLists.get(j));
						}else{
							mPolicyReceiveFile.setToUnit("无");
						}
						
						mPolicyReceiveFile.setWorkItemId(workItemId);
						mPolicyReceiveFile.setTitle(title);
						mPolicyReceiveFile.setReceFileTime(DateUtil.dateFormat(DateUtil.parse2Date(receFileTime)));
						datas.add(mPolicyReceiveFile);
						j++;
					}else{
						System.out.println("PolicyReceProtocol parseJson else...");
					}
				}
			}
		}
		
		return datas;
	}
}
