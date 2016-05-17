package com.zhjy.iot.mobile.oa.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * url链接的封装类
 * 
 * @author zhangyang
 * 
 */
public class UrlParamsEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;//
	private LinkedHashMap<String, String> paramsHashMap;
	private String methodName;// 方法名称


	 private static String webServiceUrl ="http://192.168.81.199:8089/default/serverInterface?wsdl";
//	 private static String namingSpace="http://www.primeton.com/serverInterface";
//	private static String webServiceUrl = "http://192.168.81.199:8089/default/serverInterface?wsdl";
	private static String namingSpace = "http://www.primeton.com/serverInterface";

	// 构造函数
	public UrlParamsEntity() {
		super();
	}

	public UrlParamsEntity(String ulr,
			LinkedHashMap<String, String> paramsHashMap) {
		super();
		this.url = ulr;
		this.paramsHashMap = paramsHashMap;
	}

	public HashMap<String, String> getParamsHashMap() {
		return paramsHashMap;
	}

	public void setParamsHashMap(LinkedHashMap<String, String> paramsHashMap) {
		this.paramsHashMap = paramsHashMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public static String getWebServiceUrl() {
		return webServiceUrl;
	}

	public static void setWebServiceUrl(String webServiceUrl) {
		UrlParamsEntity.webServiceUrl = webServiceUrl;
	}

	public static String getNamingSpace() {
		return namingSpace;
	}

	public static void setNamingSpace(String namingSpace) {
		UrlParamsEntity.namingSpace = namingSpace;
	}

	/**拼接Url方法**/
	public String toString() {
		if (url != null && !url.equals("") && paramsHashMap != null && paramsHashMap.size() > 0 ) {
			StringBuilder sb=new StringBuilder();
			sb.append(url).append("?");
			for(Map.Entry<String, String> entry : paramsHashMap.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key).append("=").append(value).append("&");
			}
			sb.deleteCharAt(sb.lastIndexOf("&"));
			System.out.println("UrlParamsEntity::sb.toString()" + sb.toString());
			return sb.toString();
		}else if(url != null && !url.equals("")  && (paramsHashMap==null || paramsHashMap.size()==0)){
			return url;
		}
		return "";
	}
}
