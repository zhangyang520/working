package com.zhjy.iot.mobile.oa.entity;

import com.lidroid.xutils.db.annotation.Table;

/**
 * @author zhangyang
 * @日期 2016-3-19上午9:32:51
 * @描述   文件传阅的实体类
 */
@Table(name="fileTransaction")
public class FileTransaction extends EntityBase{
	
	private String documentUserId;//文档用户id(作用是重要普通转换时用) 后台表中的唯一值
	private String sender;//发件人
	private String documentId;//文档id(文档ID 已发删除/打开详情)
	private String description;
	private boolean isReading;//读写状态（true:已读，false：未读）（1未读，2已读（后台））
	private String title;//内容主体
	//private int fileTransType;//文件传阅类别:1.已收普通文件2.已收重要文件3.已发文件 
	private String senderId;//发送人id
	private int importance;//文件名重要程度：文件传阅类别:1.已收普通文件2.已收重要文件3.已发文件 
	private String sendTime;
	
	public FileTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FileTransaction(String documentUserId, String sender,
			String documentId, String description, boolean isReading,
			String title, String senderId, 
			int importance, String sendTime) {
		super();
		this.documentUserId = documentUserId;
		this.sender = sender;
		this.documentId = documentId;
		this.description = description;
		this.isReading = isReading;
		this.title = title;
		this.senderId = senderId;
		this.importance = importance;
		this.sendTime = sendTime;
	}

	public String getDocumentUserId() {
		return documentUserId;
	}

	public void setDocumentUserId(String documentUserId) {
		this.documentUserId = documentUserId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isReading() {
		return isReading;
	}

	public void setReading(boolean isReading) {
		this.isReading = isReading;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}


	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "FileTransaction [documentUserId=" + documentUserId
				+ ", documentId=" + documentId + ", title=" + title
				+ ", importance=" + importance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((documentUserId == null) ? 0 : documentUserId.hashCode());
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
		FileTransaction other = (FileTransaction) obj;
		if (documentUserId == null) {
			if (other.documentUserId != null)
				return false;
		} else if (!documentUserId.equals(other.documentUserId))
			return false;
		return true;
	}
	
	
}
