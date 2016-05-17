package com.zhjy.iot.mobile.oa.dao;

import java.util.List;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.entity.ReceiveFileState;
import com.zhjy.iot.mobile.oa.exception.ContentException;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-20 下午12:52:48
 * 
 * @描述 行政收文的相关的dao
 */
public class PolicyReceDao{

	private static PolicyReceDao policyReceDao;
	private PolicyReceDao(){
		
	}
	
	//单利模式
	public static PolicyReceDao getInstance(){
		if(policyReceDao==null){
			synchronized (PolicyReceDao.class) {
				if(policyReceDao==null){
					policyReceDao=new PolicyReceDao();
				}
			}
		}
		return policyReceDao;
	}
	/**
	 * @param （1.已办2.未办3.待阅4.已阅）
	 * @return 返回行政收文集合
	 */
	public List<PolicyReceiveFile> getPolicyReceiveList(ReceiveFileState receiveFileState){
		/**
		 * 进行查询数据库中对应“已办”,和未办的数据
		 */
		try {
			List<PolicyReceiveFile> datas=MobileOaApplication.dbUtils.findAll(Selector.from(PolicyReceiveFile.class).where(WhereBuilder.b("state", "=",receiveFileState.getValue())));
			return datas;
		} catch (DbException e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 进行清除行政收文的表中的数据
	 */
	public void clearPolicyReceiveList(){
		try {
			MobileOaApplication.dbUtils.deleteAll(PolicyReceiveFile.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param flag :true--已办  false--未办
	 */
	public void clearPolicyReceiveList(ReceiveFileState receiveFileState){
		try {
			MobileOaApplication.dbUtils.delete(PolicyReceiveFile.class, WhereBuilder.b("state", "=", receiveFileState.getValue()));
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 行政收文集合
	 * @param datas
	 */
	public void addPolicyReceiveList(List<PolicyReceiveFile> datas){
		try {
			MobileOaApplication.dbUtils.saveAll(datas);
		} catch (DbException e) {
			e.printStackTrace();
		}
	} 
	
	/**
	 * 进行更新行政收文的实体状态:
	 *    "未办"-->"未办"
	 *    "待阅"-->"已阅"
	 * @param policyReceiveFile
	 * @throws ContentException 
	 */
	public void updatePolicyReceiveList(PolicyReceiveFile policyReceiveFile) throws ContentException{
		try {
			MobileOaApplication.dbUtils.update(policyReceiveFile,WhereBuilder.b("id", "=", policyReceiveFile.getId()), "state");
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库操作失败！");
		}
	}
}
