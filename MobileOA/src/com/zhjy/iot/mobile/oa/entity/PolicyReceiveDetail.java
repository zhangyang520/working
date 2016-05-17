package com.zhjy.iot.mobile.oa.entity;

import java.util.List;
import java.util.Map;

/**
 * 
 * @类描述：   行政收文详情实体
 * @创建人：HuangXianFeng 
 * @创建时间：2016-3-25 下午3:30:45  
 *
 */
public class PolicyReceiveDetail {
	
	private String id;//后台端表示主键的标识
	private String procedureId;//流程定义Id
	private String processInstId;//流程实例Id
	private String processName;//流程定义名称
	private String activityId;//活动Id  注意常量"finishActivity"
	private String workItemId;//工作项Id
	private boolean canNotify;//是否能够待阅（true:能够待阅，反之不可以）
	private int fileTypes;//收文种类（1.普通收文；2.文件批办单；3.请示批办单；4.区领导批示批办单）
	private String title;//收文主题
	private String tounit;//来文单位
	private String receiptNum;//收文号
	private String dense;//密级（收文类型不一样，dense的值也不一样）
	private String fileNums;//文件数
	private String receivetime;//接收时间
	private String processInstName;//流程实例名称
	
	private List<AppendDoc> appendDocSet;//附件集合
	
	private Map<OpinionSeirer,Opinion> opinionSet;//意见集合  键对应的是资源id
	
	private List<Operation> operationSet;//操作集合
	
	//注意 在解析数据时,需要进行数据库查找设置,如果查找失败,那么进行提示用户进行更新数据！
    private List<Node> allNodeList;//所有人员的Node集合
	
	public PolicyReceiveDetail() {
		super();
	}


	public PolicyReceiveDetail(String procedureId, String processInstId,
			String processName, String activityId, String workItemId,
			boolean canNotify, int fileTypes, String title, String tounit,
			String receiptNum, String dense, String fileNums,
			String receivetime, List<AppendDoc> appendDocSet,
			Map<OpinionSeirer, Opinion> opinionSet, List<Operation> operationSet) {
		super();
		this.procedureId = procedureId;
		this.processInstId = processInstId;
		this.processName = processName;
		this.activityId = activityId;
		this.workItemId = workItemId;
		this.canNotify = canNotify;
		this.fileTypes = fileTypes;
		this.title = title;
		this.tounit = tounit;
		this.receiptNum = receiptNum;
		this.dense = dense;
		this.fileNums = fileNums;
		this.receivetime = receivetime;
		this.appendDocSet = appendDocSet;
		this.opinionSet = opinionSet;
		this.operationSet = operationSet;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getProcedureId() {
		return procedureId;
	}


	public void setProcedureId(String procedureId) {
		this.procedureId = procedureId;
	}


	public String getProcessInstId() {
		return processInstId;
	}


	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}


	public String getProcessName() {
		return processName;
	}


	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public String getActivityId() {
		return activityId;
	}


	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	public String getWorkItemId() {
		return workItemId;
	}


	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}


	public boolean isCanNotify() {
		return canNotify;
	}


	public void setCanNotify(boolean canNotify) {
		this.canNotify = canNotify;
	}


	public int getFileTypes() {
		return fileTypes;
	}


	public void setFileTypes(int fileTypes) {
		this.fileTypes = fileTypes;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getTounit() {
		return tounit;
	}


	public void setTounit(String tounit) {
		this.tounit = tounit;
	}


	public String getReceiptNum() {
		return receiptNum;
	}


	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}


	public String getDense() {
		return dense;
	}


	public void setDense(String dense) {
		this.dense = dense;
	}


	public String getFileNums() {
		return fileNums;
	}


	public void setFileNums(String fileNums) {
		this.fileNums = fileNums;
	}


	public String getReceivetime() {
		return receivetime;
	}


	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}
	
	public String getProcessInstName() {
		return processInstName;
	}


	public void setProcessInstName(String processInstName) {
		this.processInstName = processInstName;
	}


	public List<AppendDoc> getAppendDocSet() {
		return appendDocSet;
	}


	public void setAppendDocSet(List<AppendDoc> appendDocSet) {
		this.appendDocSet = appendDocSet;
	}


	public Map<OpinionSeirer, Opinion> getOpinionSet() {
		return opinionSet;
	}


	public void setOpinionSet(Map<OpinionSeirer, Opinion> opinionSet) {
		this.opinionSet = opinionSet;
	}


	public List<Operation> getOperationSet() {
		return operationSet;
	}


	public void setOperationSet(List<Operation> operationSet) {
		this.operationSet = operationSet;
	}
	
	/**
	 * @return the allNodeList
	 */
	public List<Node> getAllNodeList() {
		return allNodeList;
	}

	/**
	 * @param allNodeList the allNodeList to set
	 */
	public void setAllNodeList(List<Node> allNodeList) {
		this.allNodeList = allNodeList;
	}

	@Override
	public String toString() {
		return "PolicyReceiveDetail [procedureId=" + procedureId
				+ ", processInstId=" + processInstId + ", processName="
				+ processName + ", activityId=" + activityId + ", workItemId="
				+ workItemId + ", canNotify=" + canNotify + ", fileTypes="
				+ fileTypes + ", title=" + title + ", tounit=" + tounit
				+ ", receiptNum=" + receiptNum + ", dense=" + dense
				+ ", fileNums=" + fileNums + ", receivetime=" + receivetime
				+ ", appendDocSet=" + appendDocSet + ", opinionSet="
				+ opinionSet + ", operationSet=" + operationSet + "]";
	}
	
}
