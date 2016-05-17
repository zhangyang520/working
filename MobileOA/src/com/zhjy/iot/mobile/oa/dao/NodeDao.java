package com.zhjy.iot.mobile.oa.dao;

import java.util.List;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;

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
public class NodeDao {
	
	private static NodeDao userDao;

	private  NodeDao(){
		
	}
	
	/**
	 * 描述：单例模式
	 */
	public static NodeDao getInstance(){
		if (userDao == null) {
			synchronized (NodeDao.class) {
				if (userDao == null) {
					userDao = new NodeDao();
				}
			}
		}
		return userDao;
	}
	
	/**
	 * 通过id进行查询Node实体
	 * @return
	 * @throws ContentException 
	 */
	public  Node getNodeById(String nodeId) throws ContentException{
		try {
			List<Node> datas=MobileOaApplication.dbUtils.findAll(//
											Selector.from(Node.class).where(//
															WhereBuilder.b("nodeId", "=", nodeId)));
			if(datas.size()==0){
				throw new ContentException(MobileOAConstant.NO_NODE_ERROR_CODE);
			}else{
				return datas.get(0);
			}
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库操作有误");
		}
	}
	
	/**
	 * 通过id进行查询Node实体
	 * @return
	 * @throws ContentException 
	 */
	public  Node getNodeByName(String nodeName) throws ContentException{
		try {
			List<Node> datas=MobileOaApplication.dbUtils.findAll(//
											Selector.from(Node.class).where(//
															WhereBuilder.b("nodeName", "=", nodeName)));
			if(datas.size()==0){
				throw new ContentException(MobileOAConstant.NO_DATAS_ERROR_CODE);
			}else{
				return datas.get(0);
			}
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库操作有误");
		}
	}
	
	/**
	 * 从数据库查找所有数据
	 * @return
	 * @throws ContentException
	 */
	public List<Node> getNodeList() throws ContentException{
		try {
			List<Node> nodeDatas=MobileOaApplication.dbUtils.findAll(Node.class);
			if(nodeDatas.size()==0){
				throw new ContentException(MobileOAConstant.NO_DATAS_ERROR_CODE);
			}else{
				return nodeDatas;
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ContentException("数据库操作有误");
		}
	}
	
	/**
	 * 保存node集合
	 * @param nodes
	 * @throws ContentException
	 */
	public void saveNodeList(List<Node> nodes) throws ContentException{
		try{
			MobileOaApplication.dbUtils.deleteAll(Node.class);
			MobileOaApplication.dbUtils.saveAll(nodes);
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库操作有误");
		}
	}
}
