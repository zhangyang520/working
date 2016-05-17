package com.zhjy.iot.mobile.oa.entity;

import java.util.List;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-26下午5:13:37
 * 
 * @描述  操作流程
 */
public class OperationProcess {
	private List<Node> nodes;//办理人节点
	private String handleStage;//办理阶段
	private String completionTime;//办结时间
	private String receiptTime;//接收时间
	public OperationProcess() {
		super();
	}
	
	
	public OperationProcess(List<Node> nodes, String handleStage, String completion,
			String receiptTime) {
		super();
		this.nodes = nodes;
		this.handleStage = handleStage;
		this.completionTime = completion;
		this.receiptTime = receiptTime;
	}
	
	
	public List<Node> getNodes() {
		return nodes;
	}


	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}


	public String getHandleStage() {
		return handleStage;
	}
	public void setHandleStage(String handleStage) {
		this.handleStage = handleStage;
	}
	public String getCompletion() {
		return completionTime;
	}
	public void setCompletion(String completion) {
		this.completionTime = completion;
	}
	public String getReceiptTime() {
		return receiptTime;
	}
	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
	
}
