package com.zhjy.iot.mobile.oa.entity;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-19上午9:23:50
 * 
 * @描述 行政收文的实体类
 */
@Table(name="policyReceiveFile")
public class PolicyReceiveFile extends EntityBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String workItemId;    //工作项id
	
	private String title;//主题内容
	
	private String toUnit;  //来文单位
	
	private String receFileTime;//接收时间
	
	private String processId;//流程id
	
	@Column(column="state",defaultValue="1")
	private int state;    //收文状态：（1.已办2.未办3.待阅4.已阅）
	
	public PolicyReceiveFile() {
		super();
	}

	public PolicyReceiveFile(String workItemId, String title, String toUnit,
			String receFileTime, int state) {
		super();
		this.workItemId = workItemId;
		this.title = title;
		this.toUnit = toUnit;
		this.receFileTime = receFileTime;
		this.state = state;
	}

	public String getWorkItemId() {
		return workItemId;
	}
	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getToUnit() {
		return toUnit;
	}
	public void setToUnit(String toUnit) {
		this.toUnit = toUnit;
	}
	public String getReceFileTime() {
		return receFileTime;
	}
	public void setReceFileTime(String receFileTime) {
		this.receFileTime = receFileTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Override
	public String toString() {
		return "PolicyReceiveFile [workItemId=" + workItemId + ", title="
				+ title + ", toUnit=" + toUnit + ", receFileTime="
				+ receFileTime + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((workItemId == null) ? 0 : workItemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolicyReceiveFile other = (PolicyReceiveFile) obj;
		if (workItemId == null) {
			if (other.workItemId != null)
				return false;
		} else if (!workItemId.equals(other.workItemId))
			return false;
		return true;
	}
}
