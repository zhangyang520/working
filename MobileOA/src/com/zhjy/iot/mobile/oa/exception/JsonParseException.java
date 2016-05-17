package com.zhjy.iot.mobile.oa.exception;
/**
 * json解析异常！
 * @author zhangyang
 *
 */
public class JsonParseException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonParseException(){
		super();
	}

	public JsonParseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public JsonParseException(String detailMessage) {
		super(detailMessage);
	}

	public JsonParseException(Throwable throwable) {
		super(throwable);
	}
	
}
