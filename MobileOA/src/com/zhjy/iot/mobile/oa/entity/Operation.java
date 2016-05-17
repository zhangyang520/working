package com.zhjy.iot.mobile.oa.entity;

import java.util.List;
/**
 * 
 * @类描述：   操作实体
 * @创建人：HuangXianFeng 
 * @创建时间：2016-3-25 下午3:56:45  
 *
 */
public class Operation{
	private String operationName;//操作名称
	private String operationId;//操作Id
	private List<Node> participanSet;//参与者集合
	
	public Operation(){
		super();
	}
	
	public Operation(String operationName, String operationId,
			List<Node> participanSet) {
		super();
		this.operationName = operationName;
		this.operationId = operationId;
		this.participanSet = participanSet;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public List<Node> getParticipanSet() {
		return participanSet;
	}
	public void setParticipanSet(List<Node> participanSet) {
		this.participanSet = participanSet;
	}
	@Override
	public String toString() {
		return "Operation [operationName=" + operationName + ", operationId="
				+ operationId + ", participanSet=" + participanSet + "]";
	}
	
}
