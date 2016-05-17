package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.OperationProcess;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;

public class OperationProcessProtocol extends BaseProtocol<List<OperationProcess>> {

	List<OperationProcess> list = null;
	
	OperationProcess mOperationProcess;
	Node mNode;
	private SoapObject successSoapObject;
	@Override
	protected List<OperationProcess> parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		System.out.println("OperationProcessProtocol.JsonStr = " + jsonStr);
		list = new ArrayList<OperationProcess>();
		try {
			JSONObject json = new JSONObject(jsonStr);
			String valuesStr = json.getString("values");
			JSONObject jsonValues = new JSONObject(valuesStr);
			String statusStr = jsonValues.getString("status");
			String msgStr = jsonValues.getString("msg");
			String infoStr = json.getString("info");
			
			JSONArray infoArray = new JSONArray(infoStr);
			for (int i = 0; i < infoArray.length(); i++) {
				mOperationProcess = new OperationProcess();
				mNode = new Node();
				
				JSONObject job = infoArray.getJSONObject(i);
				String node = job.getString("node");
				
				JSONObject nodeObj = new JSONObject(node);
				String nodeName = nodeObj.getString("nodeName");
				mNode.setNodeName(nodeName);
				
				String nodeId = nodeObj.getString("nodeId");
				mNode.setNodeId(nodeId);
				
				String deptId = nodeObj.getString("deptId");
				mNode.setDeptId(deptId);
				
				//String idDeptFlag = nodeObj.getString("idDeptFlag");
//				mOperationProcess.setNode(mNode);
				
				String handleStage = job.getString("handleStage");
				mOperationProcess.setHandleStage(handleStage);
				
				String completion = job.getString("completion");
				
				String receiptTime = job.getString("receiptTime");
				list.add(mOperationProcess);
				
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new JsonParseException();
		}
	}

	
	/**
	 * 
	 */
	@Override
	public List<OperationProcess> parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		successSoapObject=soapObject;
		// TODO Auto-generated method stub
		//进行解析status 和msg
		if(!soapObject.hasProperty("status") || !soapObject.hasProperty("msg")){
			throw new ContentException("数据传输失败!");
		}else{
			//是否成功status为"1"
			if("1".equals(soapObject.getProperty("status").toString())){
				//如果成功的情况下:
				List<OperationProcess> lists=new ArrayList<OperationProcess>();
				for(int i=0;i<soapObject.getPropertyCount();++i){
					if(soapObject.getProperty(i) instanceof SoapObject){
						SoapObject data=(SoapObject)soapObject.getProperty(i);
						if(data.hasProperty("workItemName") && data.hasProperty("createTime") 
								&& data.hasProperty("endTime")  && data.hasProperty("partiName")){
							//进行获取其中的名称
							String workItemName=data.getProperty("workItemName").toString();
							String createTime=data.getProperty("createTime").toString();
							String endTime=data.getProperty("endTime").toString();
//							System.out.println("OperationProcessProtocol createTime:"+createTime+"...endTime:"+endTime);
							String partiName=data.getProperty("partiName").toString();
							OperationProcess process=new OperationProcess();
							process.setCompletion(endTime);
							process.setReceiptTime(createTime);
							process.setHandleStage(workItemName);
							if(partiName.contains("|")){
								//进行分解
								String partiNames[]=partiName.split("\\|");
								List<Node> nodeList=new ArrayList<Node>();
								for(int j=0;partiNames!=null && j<partiNames.length;++j){
									//进行设置办理人
									Node node=NodeDao.getInstance().getNodeByName(partiNames[j]);
									nodeList.add(node);
								}
								process.setNodes(nodeList);
							}else{
								//进行设置办理人
								Node node=NodeDao.getInstance().getNodeByName(partiName);
								List<Node> nodeList=new ArrayList<Node>();
								nodeList.add(node);
								process.setNodes(nodeList);
							}
							lists.add(process);
						}
					}
				}
				return lists;
			}else{
				//传输失败
				throw new ContentException(soapObject.getProperty("msg").toString());
			}
		}
	}


	public SoapObject getSuccessSoapObject() {
		return successSoapObject;
	}


	public void setSuccessSoapObject(SoapObject successSoapObject) {
		this.successSoapObject = successSoapObject;
	}
	
	
}
