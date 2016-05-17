package com.zhjy.iot.mobile.oa.entity;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：OpinionIsWirte   
 * @类描述：   签名按钮是否编辑常量值 
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-28 下午2:52:49  
 * @version    
 *
 */
public enum OpinionIsWrite {

	NBOPINION("entity.nbopinion"), LDPSOPINION("entity.ldpsopinion"), FORADVICE(
			"entity.foradvice"), QLDPSOPINION("entity.qldpsopinion"), ZGPSOPINION(
			"entity.zgpsopinion"), KYOPINION("entity.kyopinion");

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private OpinionIsWrite(String type) {
		this.type = type;
	}

}
