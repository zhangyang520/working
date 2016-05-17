package com.zhjy.iot.mobile.oa.dao;

import java.util.List;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.entity.LoginUser;
import com.zhjy.iot.mobile.oa.exception.ContentException;

/**
 * 
 * @项目名：MobileOA 
 * @类名称：UserDao   
 * @类描述： 用户的Dao  
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-24 下午7:21:55  
 * @version    
 *
 */
public class LoginUserDao {
	
	private static LoginUserDao userDao;

	private  LoginUserDao(){
		
	}
	
	/**
	 * 描述：单例模式
	 */
	public static LoginUserDao getInstance(){
		if (userDao == null) {
			synchronized (LoginUserDao.class) {
				if (userDao == null) {
					userDao = new LoginUserDao();
				}
			}
		}
		return userDao;
	}
	
	/**
	 * 根据loginName进行模糊查询list<LoginUser>
	 * @return
	 * @throws ContentException 
	 */
	public List<LoginUser> getUserListLikeName(String loginName) throws ContentException{
		try {
			return MobileOaApplication.dbUtils.findAll(Selector.from(LoginUser.class).where(WhereBuilder.b("loginName", "like", loginName+"%")));
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException(e.getMessage());
		}
	}
	
	/**
	 * 通过userId进行获取LoginUser
	 * @param userId
	 * @return
	 * @throws ContentException 
	 */
	public LoginUser getUserByUserId(String userId) throws ContentException{
		try {
			List<LoginUser> datas=MobileOaApplication.dbUtils.findAll(Selector.from(LoginUser.class).where(WhereBuilder.b("userId", "=", userId)));
			if(datas.size()>0){
				return datas.get(0);
			}else{
				return null;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  ContentException(e.getMessage());
		}
	}
	/**
	 * 进行通过loginName进行获取用户集合
	 * @param loginName
	 * @return
	 * @throws ContentException
	 */
	public List<LoginUser> getUserListByName(String loginName) throws ContentException{
		try {
			return MobileOaApplication.dbUtils.findAll(Selector.from(LoginUser.class).where(WhereBuilder.b("loginName", "=",loginName)));
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException(e.getMessage());
		}
	}
	/**
	 * 通过userId进行保存用户的MD5密码
	 * @param userId 用户的id
	 * @param user
	 * @throws ContentException
	 */
	public void saveUserPwdByUserId(String operationId,LoginUser user) throws ContentException{
		try {
			MobileOaApplication.dbUtils.update(user,//
									WhereBuilder.b("operatorId","=",operationId),"MD5Pwd");
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException(e.getMessage());
		}
	}
	
	/**
	 * 通过UserId进行删除loginUser
	 * @throws ContentException 
	 */
	public void deleteUserByUserId(String userId) throws ContentException{
		
		try {
			MobileOaApplication.dbUtils.delete(LoginUser.class,WhereBuilder.b("userId", "=", userId));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ContentException(e.getMessage());
		}
	}
	/**
	 * 通过userId进行保存用户的MD5密码
	 * @param userId 用户的id
	 * @param user
	 * @throws ContentException
	 */
	public void saveUser(LoginUser user) throws ContentException{
		try {
			if(getUserByUserId(user.getUserId())!=null){
				//如果不为空,先将之前的删除
				deleteUserByUserId(user.getUserId());
			}
			MobileOaApplication.dbUtils.save(user);
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException(e.getMessage());
		}
	}
	
	/**
	 * 进行获取上一次的登录的用户信息
	 * @return
	 * @throws ContentException 
	 */
	public LoginUser getLastUser() throws ContentException{
		
		try {
			List<LoginUser> datas=MobileOaApplication.dbUtils.findAll(LoginUser.class);
			if(datas.size()>0){
				return datas.get(datas.size()-1);
			}
			return null;
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException(e.getMessage());
		}
	}
}
