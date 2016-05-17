package com.zhjy.iot.mobile.oa.entity;
/**
 * 
 * @类描述：   附件集合实体类
 * @创建人：HuangXianFeng 
 * @创建时间：2016-3-25 下午2:57:49  
 *
 */
public class AppendDoc {
	
	private String docName;//文件名称
	private String docId;//文档Id
	
	public AppendDoc() {
		super();
	}

	public AppendDoc(String docName, String docId) {
		super();
		this.docName = docName;
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	@Override
	public String toString() {
		return "AppendDoc [docName=" + docName + ", docId=" + docId + "]";
	}
	
}
