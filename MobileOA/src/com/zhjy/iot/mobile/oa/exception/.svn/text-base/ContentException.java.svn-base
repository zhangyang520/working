package com.zhjy.iot.mobile.oa.exception;
/**
 * 服务端的异常!
 * @author zhangyang
 *
 */
public class ContentException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//错误的代码的叙述
	private int errorCode;
	
	public ContentException(){
		super();
	}
	
	public ContentException(int errorCode){
		super();
		this.errorCode=errorCode;
	}
	
	public ContentException(int errorCode,String detailMessage){
		super(detailMessage);
		this.errorCode=errorCode;
	}
	public ContentException(String detailMessage,Throwable throwable) {
		super(detailMessage, throwable);
	}

	public ContentException(String detailMessage){
		super(detailMessage);
	}

	public ContentException(Throwable throwable){
		super(throwable);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}
