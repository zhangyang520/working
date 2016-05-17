package com.zhjy.iot.mobile.oa.entity;


/**
 * 
 * @项目名：MobileOA 
 * @类名称：LoginUser   
 * @类描述：   登录实体类
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-25 下午2:39:55  
 * @version    
 *
 */
public class LoginUser extends EntityBase{
	
	private String loginName;//用户登录名
	private String MD5Pwd;//用户的md5密码
	private String realName;//真实姓名
	private String userId; //用户的唯一标识
	private String operatorId;//用户操作的Id
	
	public LoginUser(String loginName, String mD5Pwd) {
		super();
		this.loginName = loginName;
		MD5Pwd = mD5Pwd;
	}
	
	public LoginUser() {
		super();
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getMD5Pwd() {
		return MD5Pwd;
	}
	
	public void setMD5Pwd(String mD5Pwd) {
		MD5Pwd = mD5Pwd;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getOperationId() {
		return operatorId;
	}

	public void setOperationId(String operationId) {
		this.operatorId = operationId;
	}
    
	@Override
	public String toString() {
		return "LoginUser [loginName=" + loginName + ", MD5Pwd=" + MD5Pwd
				+ ", realName=" + realName + ", userId=" + userId
				+ ", operationId=" + operatorId + "]";
	}
}
