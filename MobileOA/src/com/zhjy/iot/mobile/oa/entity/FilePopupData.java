package com.zhjy.iot.mobile.oa.entity;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-31上午11:48:42
 * 
 * @描述 文件数据
 */
public class FilePopupData {
	String fileName;
	
	
	public FilePopupData(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	
	public FilePopupData() {
		super();
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
   
	
}
