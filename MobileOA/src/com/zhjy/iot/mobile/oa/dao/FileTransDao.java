package com.zhjy.iot.mobile.oa.dao;

import java.util.List;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.entity.FileTransactionState;
import com.zhjy.iot.mobile.oa.exception.ContentException;

/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransDao   
 * @类描述：   文件传阅的dao
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-22 上午11:21:50  
 * @version    
 *
 */
public class FileTransDao {

	private static FileTransDao mFileTransDao;

	private  FileTransDao() {
		
	}
	
	/**单例模式 */
	public static FileTransDao getInstance(){
		if (mFileTransDao == null) {
			synchronized (FileTransDao.class) {
				if (mFileTransDao == null) {
					mFileTransDao = new FileTransDao();
				}
			}
		}
		return mFileTransDao;
	}
	
	
	/**
	 * 
	 * @param fileTransactionState  文件传阅状态
	 *        文件传阅类别:1.已收普通文件2.已收重要文件3.已发文件
	 * @return
	 * @throws ContentException
	 */
	public List<FileTransaction> getFileTransactionList(FileTransactionState fileTransactionState) throws ContentException{
		try {
			List<FileTransaction> datas=MobileOaApplication.dbUtils.findAll(Selector.from(FileTransaction.class).where((WhereBuilder.b("importance", "=",fileTransactionState.getValue()+""))));
			if(datas==null){
				System.out.println("getFileTransactionList is null......");
			}
			return datas;
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库查找错误");
		}
	}
	
	
	/**
	 * 清除对应的数据
	 * @param fileTransactionState
	 * @throws ContentException 
	 */
	public void clearFileTransactionList(FileTransactionState fileTransactionState) throws ContentException{
		try {
			MobileOaApplication.dbUtils.delete(FileTransaction.class, WhereBuilder.b("importance", "=", fileTransactionState.getValue()));
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库删除错误");
		}
	}
	
	
	/**
	 * 描述：添加文件传阅的相关数据
	 * @throws ContentException 
	 */
	public void addFileTransactionList(List<FileTransaction> datas) throws ContentException{
		try {
			MobileOaApplication.dbUtils.saveAll(datas);
		} catch (DbException e) {
			e.printStackTrace();
			throw new ContentException("数据库保存出错!");
		}
	}
	
	/**
	 * 通过id 进行删除文件传阅对象
	 * @param id
	 * @throws ContentException 
	 */
	public void deleteFileTransactionById(String documentId) throws ContentException{
		try {
			MobileOaApplication.dbUtils.delete(FileTransaction.class, WhereBuilder.b("documentId","=",documentId).and("importance", "=", FileTransactionState.YIFA.getValue() + ""));
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ContentException("数据库删除出错!");
		}
	}
	
	/**
	 * 进行更新对应的状态值:
	 *   "重要"-->"普通"
	 *   "普通"-->"重要"
	 * @param fileTransaction
	 * @throws ContentException 
	 */
	public void updateFileTransactionState(FileTransaction fileTransaction) throws ContentException{
		try {
			MobileOaApplication.dbUtils.update(fileTransaction,WhereBuilder.b("documentUserId","=", fileTransaction.getDocumentUserId()), "importance");
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ContentException("更新数据库值出错!");
		}
	}
}
