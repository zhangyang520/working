package com.zhjy.iot.mobile.oa.protocol;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import com.zhjy.iot.mobile.oa.entity.AppendDoc;
import com.zhjy.iot.mobile.oa.entity.FormType;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.Operation;
import com.zhjy.iot.mobile.oa.entity.Opinion;
import com.zhjy.iot.mobile.oa.entity.OpinionSeirer;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveDetail;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;

public class ReceiveNotificationActivityProtocol extends PolicyReceiveActivityProtocol{

	PolicyReceiveDetail mPolicyReceiveDetail = null;
	
	@Override
	public PolicyReceiveDetail parseJson(String jsonStr)
			throws JsonParseException, ContentException {
		
		System.out.println("ReceiveNotificationActivityProtocol.JsonStr = " + jsonStr);
		mPolicyReceiveDetail = new PolicyReceiveDetail();
		try {
			JSONObject json = new JSONObject(jsonStr);
			String valuesStr = json.getString("values");
			JSONObject jsonValues = new JSONObject(valuesStr);
			String statusStr = jsonValues.getString("status");
			String msgStr = jsonValues.getString("msg");
			String infoStr = json.getString("info");
			
			JSONObject jsonObject = new JSONObject(infoStr);
			
			String fileTypes = jsonObject.getString("fileTypes");
			int fileTypesInt = Integer.parseInt(fileTypes);
			mPolicyReceiveDetail.setFileTypes(fileTypesInt);
			
			
			
			String title = jsonObject.getString("title");
			mPolicyReceiveDetail.setTitle(title);
			System.out.println("title = " + title);

			String tounit = jsonObject.getString("tounit");
			mPolicyReceiveDetail.setTounit(tounit);
			System.out.println("tounit = " + tounit);

			String receiptNum = jsonObject.getString("receiptNum");
			mPolicyReceiveDetail.setReceiptNum(receiptNum);
			System.out.println("receiptNum = " + receiptNum);

			String dense = jsonObject.getString("dense");
			mPolicyReceiveDetail.setDense(dense);
			System.out.println("dense = " + dense);

			String fileNums = jsonObject.getString("fileNums");
			mPolicyReceiveDetail.setFileNums(fileNums);
			System.out.println("fileNums = " + fileNums);

			String receivetime = jsonObject.getString("receivetime");
			mPolicyReceiveDetail.setReceivetime(receivetime);
			System.out.println("receivetime = " + receivetime);

			//进行设置操作信息
			List<Operation> operations=new ArrayList<Operation>();
			operations.add(new Operation("科室拟办", "12e321321",new ArrayList<Node>()));
			operations.add(new Operation("处室办理", "12e321321",new ArrayList<Node>()));
			operations.add(new Operation("结束", "12e321321",new ArrayList<Node>()));
			mPolicyReceiveDetail.setOperationSet(operations);
			return mPolicyReceiveDetail;
		} catch (JSONException e) {
			e.printStackTrace();
			throw new JsonParseException();
		}
	}
	
	
	
	
	@Override
	public PolicyReceiveDetail parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		setSuccessSoapObject(soapObject);
		System.out.println("PolicyReceProtocol parseJson soapObject:"+String.valueOf(soapObject.toString()));
		if (soapObject.hasProperty("status") && "0".equals(soapObject.getProperty("status").toString())) {
			if (soapObject.hasProperty("msg")) {
				throw new ContentException(soapObject.getProperty("msg").toString());
			}else{
				throw new ContentException("数据传输有误");
			}
		}else{
			Object data = null;
			System.out.println("---------------------------");
			mPolicyReceiveDetail = new PolicyReceiveDetail();
			String status = soapObject.getProperty("status").toString();
			String msg = soapObject.getProperty("msg").toString();
			data= soapObject.getProperty("entity");
			
			if (data instanceof SoapPrimitive) {
				
			}else if(data instanceof SoapObject){
				if (((SoapObject)data).hasProperty("msg")) {
					
				}else if(!((SoapObject)data).hasProperty("id") || !((SoapObject)data).hasProperty("title") || 
						!((SoapObject)data).hasProperty("filetypes") || !((SoapObject)data).hasProperty("receiptnum") || 
						!((SoapObject)data).hasProperty("tonum") || !((SoapObject)data).hasProperty("tounit") || 
						!((SoapObject)data).hasProperty("sender") || !((SoapObject)data).hasProperty("receivetime") || 
						!((SoapObject)data).hasProperty("handlestatus") || !((SoapObject)data).hasProperty("dense")|| 
						!((SoapObject)data).hasProperty("attachment") || !((SoapObject)data).hasProperty("totypes")|| 
						!((SoapObject)data).hasProperty("nbopinion") || !((SoapObject)data).hasProperty("filenums") ||
						!((SoapObject)data).hasProperty("processInstId") || !((SoapObject)data).hasProperty("processName") ||
						!((SoapObject)data).hasProperty("incomedate") || !((SoapObject)data).hasProperty("foradvice") ||
						!((SoapObject)data).hasProperty("qldpsopinion") || !((SoapObject)data).hasProperty("ldpsopinion")){
					
					throw new ContentException("数据格式传输错误!");
				}else{
					//id
					String id = ((SoapObject)data).getProperty("id").toString();
					//主题
					String title = ((SoapObject)data).getProperty("title").toString();
					//文件类型
					String filetypes = ((SoapObject)data).getProperty("filetypes").toString();
					//来文号
					String tonum = ((SoapObject)data).getProperty("tonum").toString();
					//来文单位
					String tounit = ((SoapObject)data).getProperty("tounit").toString();
					//密级
					String dense = ((SoapObject)data).getProperty("dense").toString();
					//附件
					
					String incomedate=((SoapObject) data).getProperty("incomedate").toString();//收发文时间
					String filenums = ((SoapObject) data).getProperty("filenums").toString();//文件数
					System.out.println("incomedate:"+DateUtil.dateFormat(DateUtil.parse2Date(incomedate)));
					
					if(soapObject.hasProperty("attachmentInfos")){
						//进行解析附件
						SoapObject attachmentInfos =(SoapObject)soapObject.getProperty("attachmentInfos");
						//如果有attachmentInfos这个属性值
						if(!attachmentInfos.hasProperty("id") || !attachmentInfos.hasProperty("originalName")){
							throw new ContentException("数据格式传输错误!");
						}else{
							//如果有多个attachmentsInfos属性怎么办
							List<AppendDoc> docList=new ArrayList<AppendDoc>();
							for(int i=0;i<soapObject.getPropertyCount();++i){
								Object obj=soapObject.getProperty(i);
								if(obj instanceof SoapObject){
									if(((SoapObject)obj).hasProperty("id") && ((SoapObject)obj).hasProperty("originalName")){
										String docId=((SoapObject)obj).getProperty("id").toString();
										String originalName=((SoapObject)obj).getProperty("originalName").toString();
										AppendDoc doc=new AppendDoc(originalName, docId);
										docList.add(doc);
									}
								}
							}
							mPolicyReceiveDetail.setAppendDocSet(docList);
						}
					}
					
					//暂不需要
					String sender = ((SoapObject) data).getProperty("sender").toString();
					//暂不需要
					String receiptnum = ((SoapObject) data).getProperty("receiptnum").toString();
					//暂不需要
					String receivetime = ((SoapObject) data).getProperty("receivetime").toString();
					//暂不需要
					String handlestatus = ((SoapObject) data).getProperty("handlestatus").toString();
					//暂不需要
					String totypes = ((SoapObject) data).getProperty("totypes").toString();
					
					mPolicyReceiveDetail.setCanNotify(false);
					//将id进行设置为""
					mPolicyReceiveDetail.setProcedureId("");
					String processInstId=((SoapObject)data).getProperty("processInstId").toString();
					BigDecimal integer=new BigDecimal(processInstId);
					mPolicyReceiveDetail.setProcessInstId(integer.intValue()+"");
					mPolicyReceiveDetail.setProcessName("");
					mPolicyReceiveDetail.setActivityId("");
					mPolicyReceiveDetail.setWorkItemId("");
					
					if (filetypes.equals("1")) {
						mPolicyReceiveDetail.setFileTypes(FormType.CommonForm.getValue());
					}else if (filetypes.equals("2")) {
						mPolicyReceiveDetail.setFileTypes(FormType.WJPBDAN.getValue());
					}else if (filetypes.equals("3")) {
						mPolicyReceiveDetail.setFileTypes(FormType.QSPBDAN.getValue());
					}else if (filetypes.equals("4")) {
						mPolicyReceiveDetail.setFileTypes(FormType.QLDQSPBDAN.getValue());
					}else {
						throw new ContentException("文件类型不存在!");
					}
					
					Map<OpinionSeirer,Opinion> opinionMap=new HashMap<OpinionSeirer, Opinion>();
					//六个意见
					String nbopinion = ((SoapObject) data).getProperty("nbopinion").toString();//拟办意见
					processOpinionMap(mPolicyReceiveDetail.getFileTypes(),nbopinion,opinionMap,OpinionSeirer.intendsComment);
					String foradvice = ((SoapObject) data).getProperty("foradvice").toString();//办理意见
					processOpinionMap(mPolicyReceiveDetail.getFileTypes(),foradvice,opinionMap,OpinionSeirer.handleComment);
					String qldpsopinion = ((SoapObject) data).getProperty("qldpsopinion").toString();//区领导批示意见
					processOpinionMap(mPolicyReceiveDetail.getFileTypes(),qldpsopinion,opinionMap,OpinionSeirer.quldpsOpinion);
					String ldpsopinion = ((SoapObject) data).getProperty("ldpsopinion").toString();//领导批示意见
					processOpinionMap(mPolicyReceiveDetail.getFileTypes(),ldpsopinion,opinionMap,OpinionSeirer.leadershipOppinion);
					String zgpsopinion=((SoapObject) data).getProperty("zgpsopinion").toString();//主管领导批示意见
					processOpinionMap(mPolicyReceiveDetail.getFileTypes(),zgpsopinion,opinionMap,OpinionSeirer.zgldOpinion);
					String kyopinion=((SoapObject) data).getProperty("kyopinion").toString();//科员批示意见
					processOpinionMap(mPolicyReceiveDetail.getFileTypes(),kyopinion,opinionMap,OpinionSeirer.kyOpinion);
					mPolicyReceiveDetail.setOpinionSet(opinionMap);
					
					//进行设置主题
					mPolicyReceiveDetail.setTitle(title);
					//进行设置来文单位
					mPolicyReceiveDetail.setTounit(tounit);
					//进行设置来文号
					mPolicyReceiveDetail.setReceiptNum(receiptnum);
					//进行设置密级
					mPolicyReceiveDetail.setDense(dense);
					//进行设置文件数
					mPolicyReceiveDetail.setFileNums(filenums);
					//进行设置接收时间
					mPolicyReceiveDetail.setReceivetime(DateUtil.dateFormat(DateUtil.parse2Date(incomedate)));
					//没有操作集合
					mPolicyReceiveDetail.setOperationSet(null);
				}
			}
		}
		return mPolicyReceiveDetail;
	}



	/**
	 * 进行处理意见
	 * @param nbopinion
	 * @param nbopinion 
	 * @param opinionMap
	 * @param intendscomment 
	 */
	private void processOpinionMap(int fileTypes,
			String nbopinion, Map<OpinionSeirer, Opinion> opinionMap, OpinionSeirer intendscomment) {
		// TODO Auto-generated method stub
		if(MobileOAConstant.CONSTANT_NULL_SOAP_STRING.equals(nbopinion.trim())){
			//主要判断表单类型不为为:区领导请示批示单 ,以及意见类型为quldpsOpinion
			if(fileTypes!=FormType.QLDQSPBDAN.getValue() && intendscomment==OpinionSeirer.quldpsOpinion){
				
			}else{
				opinionMap.put(intendscomment, new Opinion(false, null));
			}
			
		}else{
			if(fileTypes!=FormType.QLDQSPBDAN.getValue() && intendscomment==OpinionSeirer.quldpsOpinion){
				
			}else{
				List<String> opinionList=new ArrayList<String>();
				opinionList.add(nbopinion);
				Opinion opinion=new Opinion(false,opinionList);
				opinionMap.put(intendscomment, opinion);
			}
		}
	}

}
