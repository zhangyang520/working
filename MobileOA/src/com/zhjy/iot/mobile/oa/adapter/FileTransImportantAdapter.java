package com.zhjy.iot.mobile.oa.adapter;

import java.util.List;

import android.widget.ListView;

import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.Inner.DefaultAdapter;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.holder.FileTransImportantHolder;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransImportantAdapter   
 * @类描述：   文件传阅-重要文件的Adapter
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-20 下午2:14:00  
 * @version    
 *
 */
public class FileTransImportantAdapter extends DefaultAdapter<FileTransaction> {

	FileTransImportantHolder fileTransImportantHolder;
	public FileTransImportantAdapter(List<FileTransaction> datas, ListView lv) {
		super(datas, lv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public  BaseHolder<FileTransaction> getHolder() {
//		if(fileTransImportantHolder==null){
//			fileTransImportantHolder=new FileTransImportantHolder();
//		}
//		return fileTransImportantHolder;
		return new FileTransImportantHolder();
	}

	@Override
	protected void processDatasList() {
		// TODO Auto-generated method stub
		
	}

}
