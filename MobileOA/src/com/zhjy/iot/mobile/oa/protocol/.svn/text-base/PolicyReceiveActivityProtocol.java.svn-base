package com.zhjy.iot.mobile.oa.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import com.zhjy.iot.mobile.oa.Inner.BaseProtocol;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.AppendDoc;
import com.zhjy.iot.mobile.oa.entity.FormType;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.Operation;
import com.zhjy.iot.mobile.oa.entity.Opinion;
import com.zhjy.iot.mobile.oa.entity.OpinionIsWrite;
import com.zhjy.iot.mobile.oa.entity.OpinionSeirer;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveDetail;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.SortArrayList;
import com.zhjy.iot.mobile.oa.utils.SortArrayList.ListAddListener;

/**
 * 
 * @项目名：MobileOA
 * @类名称：PolicyReceiveActivityProtocol
 * @类描述： 行政收文的Activity的已办数据解析
 * @创建人：HuangXianFeng
 * @修改人：
 * @创建时间：2016-3-29 下午5:57:04
 * @version
 * 
 */
public class PolicyReceiveActivityProtocol extends
		BaseProtocol<PolicyReceiveDetail> {
	
	private String yiBanType;
	

	public void setYiBanType(String yiBanType) {
		this.yiBanType = yiBanType;
	}

	PolicyReceiveDetail mPolicyReceiveDetail = null;
	private SoapObject successSoapObject;
	private String successJsonStr;
	
	
	@Override
	public PolicyReceiveDetail parseJson(String jsonStr)
			throws JsonParseException, ContentException{
		successJsonStr=jsonStr;
		System.out.println("PolicyReceiveActivityProtocol......parseJson::::" + jsonStr);
		try {
			System.out.println("jsonStr:"+jsonStr);
			//进行解析:
			JSONObject allContentJson=new JSONObject(jsonStr);
			//进行获取状态值:
			JSONObject valuesObject=(JSONObject) allContentJson.get("values");
			System.out.println("PolicyReceiveActivityProtocol...PolicyReceiveDetail.....===" + "1".equals(valuesObject.getString("status")));
			if("1".equals(valuesObject.getString("status"))){
				
				PolicyReceiveDetail detail=new PolicyReceiveDetail();
				//进行数据解析
				JSONObject infoObject=new JSONObject(allContentJson.getString("info"));
				String procedureId=infoObject.getString("procedureId");
				detail.setProcedureId(procedureId);
				String processInstId=infoObject.getString("processInstId");
				detail.setProcessInstId(processInstId);
				String processName=infoObject.getString("processName");
				detail.setProcessName(processName);
				String activityId=infoObject.getString("activityId");
				detail.setActivityId(activityId);
				String workItemId=infoObject.getString("workItemId");
				detail.setWorkItemId(workItemId);
				boolean canNotify=infoObject.getBoolean("canNotify");
				detail.setCanNotify(canNotify);
				int  fileTypes=infoObject.getInt("fileTypes");
				detail.setFileTypes(fileTypes);
				String title=infoObject.getString("title");
				detail.setTitle(title);
				String receivetime=infoObject.getString("receivetime");
				detail.setReceivetime(receivetime);
				String tounit=infoObject.optString("tounit");
				detail.setTounit(tounit);
				String receiptNum=infoObject.optString("receiptNum");
				detail.setReceiptNum(receiptNum);
				String dense=infoObject.optString("dense");
				detail.setDense(dense);
				String fileNums=infoObject.optString("fileNums");
				detail.setFileNums(fileNums);
				
				//进行获取附件集合
				JSONArray appendDocSet=infoObject.getJSONArray("appendDocSet");
				List<AppendDoc> appendDocs=new ArrayList<AppendDoc>();
				for(int i=0;i<appendDocSet.length();++i){
					JSONObject appendDoc=appendDocSet.getJSONObject(i);
					String docName=appendDoc.getString("docName");
					String docId=appendDoc.getString("docId");
					AppendDoc doc=new AppendDoc();
					doc.setDocId(docId);
					doc.setDocName(docName);
					appendDocs.add(doc);
				}
				detail.setAppendDocSet(appendDocs);
				
				//进行获取意见jsonObject
				JSONObject opinionObject=infoObject.getJSONObject("opinionSet");
				Map<OpinionSeirer,Opinion> opinionMap=new HashMap<OpinionSeirer, Opinion>();
				//进行获取:intendsComment
				if(!opinionObject.optString("intendsComment").equals("")){
					JSONObject intendsCommentObject=(JSONObject)opinionObject.get("intendsComment");
					boolean canWrite=intendsCommentObject.getBoolean("canWrite");
					JSONArray contentJSONArray=intendsCommentObject.getJSONArray("content");
					List<String> contentList=new ArrayList<String>();
					if(contentJSONArray!=null){
						for(int i=0;i<contentJSONArray.length();++i){
							contentList.add(contentJSONArray.getString(i));
						}
					}
					Opinion opinion=new Opinion();
					opinion.setCanWrite(canWrite);
					opinion.setContentList(contentList);
					opinionMap.put(OpinionSeirer.intendsComment, opinion);
				}
				//进行获取:leadershipOppinion
				if(!opinionObject.optString("leadershipOppinion").equals("")){
					JSONObject intendsCommentObject=(JSONObject)opinionObject.get("leadershipOppinion");
					boolean canWrite=intendsCommentObject.getBoolean("canWrite");
					JSONArray contentJSONArray=intendsCommentObject.getJSONArray("content");
					List<String> contentList=new ArrayList<String>();
					if(contentJSONArray!=null){
						for(int i=0;i<contentJSONArray.length();++i){
							contentList.add(contentJSONArray.getString(i));
						}
					}
					Opinion opinion=new Opinion();
					opinion.setCanWrite(canWrite);
					opinion.setContentList(contentList);
					opinionMap.put(OpinionSeirer.leadershipOppinion, opinion);
				}
				//进行获取:handleComment
				if(!opinionObject.optString("handleComment").equals("")){
					JSONObject intendsCommentObject=(JSONObject)opinionObject.get("handleComment");
					boolean canWrite=intendsCommentObject.getBoolean("canWrite");
					JSONArray contentJSONArray=intendsCommentObject.getJSONArray("content");
					List<String> contentList=new ArrayList<String>();
					if(contentJSONArray!=null){
						for(int i=0;i<contentJSONArray.length();++i){
							contentList.add(contentJSONArray.getString(i));
						}
					}
					Opinion opinion=new Opinion();
					opinion.setCanWrite(canWrite);
					opinion.setContentList(contentList);
					opinionMap.put(OpinionSeirer.handleComment, opinion);
				}
				
				//进行获取:quldpsOpinion
				if(!opinionObject.optString("quldpsOpinion").equals("")){
					JSONObject intendsCommentObject=(JSONObject)opinionObject.get("quldpsOpinion");
					boolean canWrite=intendsCommentObject.getBoolean("canWrite");
					JSONArray contentJSONArray=intendsCommentObject.getJSONArray("content");
					List<String> contentList=new ArrayList<String>();
					if(contentJSONArray!=null){
						for(int i=0;i<contentJSONArray.length();++i){
							contentList.add(contentJSONArray.getString(i));
						}
					}
					
					Opinion opinion=new Opinion();
					opinion.setCanWrite(canWrite);
					opinion.setContentList(contentList);
					opinionMap.put(OpinionSeirer.quldpsOpinion, opinion);
				}
				
				detail.setOpinionSet(opinionMap);
				
				//进行设置操作集合
				System.out.println("operationSet is kong:"+infoObject.optString("operationSet").equals(""));
				if(!infoObject.optString("operationSet").equals("")){
					//进行获取操作集合
					JSONArray operationJSONArray=infoObject.getJSONArray("operationSet");
					List<Operation> operationSet=new ArrayList<Operation>();
					for(int i=0;operationJSONArray!=null && i<operationJSONArray.length();++i){
						JSONObject jsonObject=(JSONObject) operationJSONArray.get(i);
						String operationName=jsonObject.getString("operationName");
						String operationId=jsonObject.getString("operationId");
						Operation operation=new Operation();
						operation.setOperationName(operationName);
						operation.setOperationId(operationId);
						if(!jsonObject.optString("participantSet").equals("") && !jsonObject.isNull("participantSet")){
							JSONArray participantSet=jsonObject.getJSONArray("participantSet");
							List<Node> nodeList=new ArrayList<Node>();
							for(int j=0;j<participantSet.length();++j){
								JSONObject particpant=participantSet.getJSONObject(j);
//								Node node=new Node();
								String userId=particpant.getString("userId");
								System.out.println("PolicyReceiveActivityProtocol userId:"+userId);
								Node node=NodeDao.getInstance().getNodeById(userId);
								node.setNodeId(userId);
								String deptId=particpant.getString("deptId");
								node.setDeptId(deptId);
								//Node node=NodeDao.getInstance().getNodeById(userId);
								nodeList.add(node);
							}
							operation.setParticipanSet(nodeList);
						}
						operationSet.add(operation);
					}
					detail.setOperationSet(operationSet);
				}
				
				detail.setAllNodeList(NodeDao.getInstance().getNodeList());
				return detail;
			}else{
				//错误信息
				throw new ContentException(valuesObject.getString("msg"));
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			throw new JsonParseException("数据格式出错!");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.zhjy.iot.mobile.oa.Inner.BaseProtocol#parseJson(org.ksoap2.serialization.SoapObject)
	 */
	@SuppressWarnings("null")
	@Override
	public PolicyReceiveDetail parseJson(SoapObject soapObject)
			throws JsonParseException, ContentException {
		successSoapObject = soapObject;
		//System.out.println("PolicyReceProtocol parseJson soapObject:"+String.valueOf(soapObject.toString()));
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
			
			String canWrite = soapObject.getProperty("canWrite").toString();
			
			if (data instanceof SoapPrimitive) {
				
			}else if(data instanceof SoapObject){
				if (((SoapObject) data).hasProperty("msg")) {
					
				}else if(!((SoapObject) data).hasProperty("title") || !((SoapObject) data).hasProperty("id") ||
						!((SoapObject) data).hasProperty("filetypes") || !((SoapObject) data).hasProperty("receiptnum") || 
						!((SoapObject) data).hasProperty("tonum") || !((SoapObject) data).hasProperty("tounit") || 
						!((SoapObject) data).hasProperty("dense") || !((SoapObject) data).hasProperty("attachment") ||
						!((SoapObject) data).hasProperty("totypes") || !((SoapObject) data).hasProperty("filenums") ||
					    !((SoapObject) data).hasProperty("processName") || !((SoapObject) data).hasProperty("incomedate") ||
						!((SoapObject) data).hasProperty("nbopinion") || !((SoapObject) data).hasProperty("foradvice") ||
						!((SoapObject) data).hasProperty("ldpsopinion") || !((SoapObject) data).hasProperty("qldpsopinion")){
					
					System.out.println("!((SoapObject) data).hasProperty(title)" + ((SoapObject) data).hasProperty("title"));
					System.out.println("!((SoapObject) data).hasProperty(filetypes)" + ((SoapObject) data).hasProperty("filetypes"));
					System.out.println("!((SoapObject) data).hasProperty(receiptnum)" + ((SoapObject) data).hasProperty("receiptnum"));
					System.out.println("!((SoapObject) data).hasProperty(tonum)" + ((SoapObject) data).hasProperty("tonum"));
					System.out.println("!((SoapObject) data).hasProperty(tounit)" + ((SoapObject) data).hasProperty("tounit"));
					System.out.println("!((SoapObject) data).hasProperty(dense)" + ((SoapObject) data).hasProperty("dense"));
					System.out.println("!((SoapObject) data).hasProperty(attachment)" + ((SoapObject) data).hasProperty("attachment"));
					System.out.println("!((SoapObject) data).hasProperty(totypes)" + ((SoapObject) data).hasProperty("totypes"));
					System.out.println("!((SoapObject) data).hasProperty(filenums)" + ((SoapObject) data).hasProperty("filenums"));
					System.out.println("!((SoapObject) data).hasProperty(processName)" + ((SoapObject) data).hasProperty("processName"));
					System.out.println("!((SoapObject) data).hasProperty(incomedate)" + ((SoapObject) data).hasProperty("incomedate"));
					System.out.println("!((SoapObject) data).hasProperty(nbopinion)" + ((SoapObject) data).hasProperty("nbopinion"));
					System.out.println("!((SoapObject) data).hasProperty(foradvice)" + ((SoapObject) data).hasProperty("foradvice"));
					System.out.println("!((SoapObject) data).hasProperty(ldpsopinion)" + ((SoapObject) data).hasProperty("ldpsopinion"));
					System.out.println("!((SoapObject) data).hasProperty(qldpsopinion)" + ((SoapObject) data).hasProperty("qldpsopinion"));
					
					
					throw new ContentException("数据格式传输错误!");
					
				}else{
					String id = ((SoapObject) data).getProperty("id").toString();
					String title = ((SoapObject) data).getProperty("title").toString();//标题
					String filetypes = ((SoapObject) data).getProperty("filetypes").toString();//文件类型{1-普通收文,2-文件批办单,3-请示批办单,4-区领导批示批办单
//					String receiptnum = ((SoapObject) data).getProperty("receiptnum").toString();//收文号
					String tonum = ((SoapObject) data).getProperty("tonum").toString();//来文号
					String tounit = ((SoapObject) data).getProperty("tounit").toString();//发文单位
					String dense = ((SoapObject) data).getProperty("dense").toString();//密级
					
					
					String incomedate = ((SoapObject) data).getProperty("incomedate").toString();//收发文时间
					String filenums = ((SoapObject) data).getProperty("filenums").toString();//文件数
					
					if (soapObject.hasProperty("attachmentInfos")) {
						//进行附件的解析
						SoapObject attachmentInfos = (SoapObject) soapObject.getProperty("attachmentInfos");
						if (!attachmentInfos.hasProperty("id") && !attachmentInfos.hasProperty("originalName")) {
							throw new  ContentException("数据格式传输错误!");
						}else{
							List<AppendDoc> list = new ArrayList<AppendDoc>();
							for (int i = 0; i < soapObject.getPropertyCount(); i++) {
								Object object = soapObject.getProperty(i);
								if (object instanceof SoapObject) {
									if (((SoapObject) object).hasProperty("id") && ((SoapObject) object).hasProperty("originalName")) {
										String docId = ((SoapObject) object).getProperty("id").toString();
										String docName = ((SoapObject) object).getProperty("originalName").toString();
										AppendDoc appendDoc = new AppendDoc(docName, docId);
										list.add(appendDoc);
									}
								}
							}
							mPolicyReceiveDetail.setAppendDocSet(list);
						}
					}
					
					String totypes = ((SoapObject) data).getProperty("totypes").toString();//来文类型
					String processName = ((SoapObject) data).getProperty("processName").toString();//流程定义名称
					String receiptnum = ((SoapObject) data).getProperty("receiptnum").toString();
					
					mPolicyReceiveDetail.setId(id);
					mPolicyReceiveDetail.setProcedureId("");
					mPolicyReceiveDetail.setProcessName(processName);
					mPolicyReceiveDetail.setActivityId("");
					mPolicyReceiveDetail.setWorkItemId("");
					
					if (filetypes.equals("1")) {
						mPolicyReceiveDetail.setFileTypes(1);
					}else if (filetypes.equals("2")) {
						mPolicyReceiveDetail.setFileTypes(2);
					}else if (filetypes.equals("3")) {
						mPolicyReceiveDetail.setFileTypes(3);
					}else if (filetypes.equals("4")) {
						mPolicyReceiveDetail.setFileTypes(4);
					}else{
						throw new ContentException("文件类型不存在!");
					}
					
					Map<OpinionSeirer,Opinion> opinionMap=new HashMap<OpinionSeirer, Opinion>();
					
					String nbopinion = ((SoapObject) data).getProperty("nbopinion").toString();//拟办意见
					if (yiBanType.equals("yiban")) {
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), nbopinion, opinionMap, OpinionSeirer.intendsComment ,false);
					}else{
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), nbopinion, opinionMap, OpinionSeirer.intendsComment ,isCanWrite(canWrite,"nbopinion"));
					}
					
					String foradvice = ((SoapObject) data).getProperty("foradvice").toString();//办理意见
					if (yiBanType.equals("yiban")) {
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), foradvice, opinionMap, OpinionSeirer.handleComment,false);
					}else{
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), foradvice, opinionMap, OpinionSeirer.handleComment,isCanWrite(canWrite,"foradvice"));
					}
					
					String ldpsopinion = ((SoapObject) data).getProperty("ldpsopinion").toString();//领导批示意见]
					if (yiBanType.equals("yiban")) {
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), ldpsopinion, opinionMap, OpinionSeirer.leadershipOppinion,false);
					}else{
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), ldpsopinion, opinionMap, OpinionSeirer.leadershipOppinion,isCanWrite(canWrite,"ldpsopinion"));
					}
					
					String qldpsopinion = ((SoapObject) data).getProperty("qldpsopinion").toString();//区领导批示意见
					if (yiBanType.equals("yiban")) {
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), qldpsopinion, opinionMap, OpinionSeirer.quldpsOpinion,false);
					}else{
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), qldpsopinion, opinionMap, OpinionSeirer.quldpsOpinion,isCanWrite(canWrite,"qldpsopinion"));
					}					
					String zgpsopinion = ((SoapObject) data).getProperty("zgpsopinion").toString();//主管领导批示意见
					if (yiBanType.equals("yiban")) {
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), zgpsopinion, opinionMap, OpinionSeirer.zgldOpinion,false);
					}else{
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), zgpsopinion, opinionMap, OpinionSeirer.zgldOpinion,isCanWrite(canWrite,"zgpsopinion"));
					}
					
					String kyopinion = ((SoapObject) data).getProperty("kyopinion").toString();//科员批示意见
					if (yiBanType.equals("yiban")) {
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), kyopinion, opinionMap, OpinionSeirer.kyOpinion,false);
					}else{
						processOpinionMap(mPolicyReceiveDetail.getFileTypes(), kyopinion, opinionMap, OpinionSeirer.kyOpinion,isCanWrite(canWrite,"kyopinion"));
					}
					
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
				}
			}
			
			SoapObject wfprocessinstInfo = (SoapObject) soapObject.getProperty("wfprocessinst");
			if (!wfprocessinstInfo.hasProperty("processInstName") || !wfprocessinstInfo.hasProperty("processInstID")) {
				throw new ContentException("数据格式传输错误!");
			}else{
				String processInstName = wfprocessinstInfo.getProperty("processInstName").toString();
				String processInstID = wfprocessinstInfo.getProperty("processInstID").toString();
				
				mPolicyReceiveDetail.setProcessInstId(processInstID);
				mPolicyReceiveDetail.setProcessInstName(processInstName);
			}
			
			String canNotify = soapObject.getProperty("canNotify").toString();
			if (canNotify.equals("true")) {
				mPolicyReceiveDetail.setCanNotify(true);
			}else{
				mPolicyReceiveDetail.setCanNotify(false);
			}
			String activityInfo = soapObject.getProperty("activityInfo").toString();
			SortArrayList<Operation> operationSet = new SortArrayList<Operation>();
			
			operationSet.setListAddListener(new ListAddListener<Operation>() {

				@Override
				public int processAddList(SortArrayList<Operation> datas,
						Operation data) {
					//进行条件的预处理
					if(data.getOperationName().equals("办理")){
						//如果插入的数据为"办理",进行查看前方有无"办结",如果有"办结",将"办理"插入在"办结"前方!
						int index=-1;
						for(Operation operation:datas){
							index++;
							if("办结".equals(operation.getOperationName())){
								return index;
							}
						}
					}
					return -1;
				}
			});
			Operation mOperation = null;
			
			System.out.println("--canNotify=" + canNotify);
			System.out.println("--activityInfo=" + activityInfo);
			if (activityInfo.equals("finishActivity,结束,")) {
				mOperation = new Operation();
				mOperation.setOperationName("结束");
				operationSet.add(mOperation);
				mPolicyReceiveDetail.setOperationSet(operationSet);
			}else{
				String[] split = activityInfo.split(";");
				
				for (int i = 0; i < split.length; i++) {
					List<Node> nodeList=new ArrayList<Node>();
					mOperation = new Operation();
					String[] split2 = split[i].split(",");
					System.out.println("split2 = " + split2.length);
					
					String activitySign = split2[0];
					mOperation.setOperationId(activitySign);
					
					if (split2.length == 2) {
						String activityName = split2[1];
						mOperation.setOperationName(activityName);
						mOperation.setParticipanSet(nodeList);
					}else{
						String activityName = split2[1];
						mOperation.setOperationName(activityName);
						String activityList = split2[2];
						if (activityList.indexOf("&&") == -1) {//不包含
							String[] split3 = activityList.split("\\|");
							System.out.println("split3.length = " + split3.length);
							String userId = split3[0];
							System.out.println("activityList=" +  activityList + ";userId====" + userId);
							Node node=NodeDao.getInstance().getNodeById(Double.valueOf(Integer.parseInt(userId)).toString());
							node.setNodeId(userId);
							String userName = split3[2];
							node.setNodeName(userName);
							nodeList.add(node);
						}else{//包含
							String[] split3 = activityList.split("&&");
							for (int j = 0; j < split3.length; j++) {
								String string = split3[j];
								String[] split4 = string.split("\\|");
								String userId = split4[0];
								System.out.println("string=" +  string + ";userId====" + userId);
								Node node=NodeDao.getInstance().getNodeById(Double.valueOf(Integer.parseInt(userId)).toString());
								node.setNodeId(userId);
								if (split3.length == 3) {
									String userName = split4[2];
									node.setNodeName(userName);
								}
								nodeList.add(node);
							}
						}
						mOperation.setParticipanSet(nodeList);
					}
					operationSet.add(mOperation);
				}
				mPolicyReceiveDetail.setOperationSet(operationSet);
			}
		}
		mPolicyReceiveDetail.setAllNodeList(NodeDao.getInstance().getNodeList());
		return mPolicyReceiveDetail;
	}

	private boolean isCanWrite(String canWrite,String str) {
		System.out.println("PolicyReceiveActivityProtocol canWrite = " + canWrite);
		if (canWrite.equals(OpinionIsWrite.NBOPINION.getType()) && str.equals("nbopinion")) {
			return true;
		}else if (canWrite.equals(OpinionIsWrite.LDPSOPINION.getType()) && str.equals("ldpsopinion")) {
			return true;
		}else if (canWrite.equals(OpinionIsWrite.FORADVICE.getType()) && str.equals("foradvice")) {
			return true;
		}else if (canWrite.equals(OpinionIsWrite.QLDPSOPINION.getType()) && str.equals("qldpsopinion")) {
			return true;
		}else if (canWrite.equals(OpinionIsWrite.ZGPSOPINION.getType()) && str.equals("zgpsopinion")) {
			return true;
		}else if (canWrite.equals(OpinionIsWrite.KYOPINION.getType()) && str.equals("kyopinion")) {
			return true;
		}
		return false;
	}
	
	
	

	/**
	 * 进行处理意见
	 * @param nbopinion
	 * @param nbopinion 
	 * @param opinionMap
	 * @param intendscomment 
	 */
	private void processOpinionMap(int fileTypes,
			String nbopinion, Map<OpinionSeirer, Opinion> opinionMap, OpinionSeirer intendscomment ,boolean canWirte) {
		if(MobileOAConstant.CONSTANT_NULL_SOAP_STRING.equals(nbopinion.trim())){
			//主要判断表单类型不为为:区领导请示批示单 ,以及意见类型为quldpsOpinion
			if(fileTypes!=FormType.QLDQSPBDAN.getValue() && intendscomment==OpinionSeirer.quldpsOpinion){
				
			}else{
				opinionMap.put(intendscomment, new Opinion(canWirte, null));
			}
			
		}else{
			if(fileTypes!=FormType.QLDQSPBDAN.getValue() && intendscomment==OpinionSeirer.quldpsOpinion){
				
			}else{
				List<String> opinionList=new ArrayList<String>();
				opinionList.add(nbopinion);
				Opinion opinion=new Opinion(canWirte,opinionList);
				opinionMap.put(intendscomment, opinion);
			}
		}
	}

	

	/**
	 * @return the successSoapObject
	 */
	public SoapObject getSuccessSoapObject() {
		return successSoapObject;
	}

	/**
	 * @param successSoapObject the successSoapObject to set
	 */
	public void setSuccessSoapObject(SoapObject successSoapObject) {
		this.successSoapObject = successSoapObject;
	}

	/**
	 * @return the successJsonStr
	 */
	public String getSuccessJsonStr() {
		return successJsonStr;
	}

	/**
	 * @param successJsonStr the successJsonStr to set
	 */
	public void setSuccessJsonStr(String successJsonStr) {
		this.successJsonStr = successJsonStr;
	}
	
}
