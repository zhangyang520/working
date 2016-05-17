package com.zhjy.iot.mobile.oa.adapter;

import java.util.List;

import android.widget.ListView;

import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.Inner.DefaultAdapter;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.holder.FileTransYifaHolder;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：FileTransYifaAdapter   
 * @类描述：    文件传阅-已发文件的Adapter
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-20 下午1:46:32  
 * @version    
 *
 */
public class FileTransYifaAdapter extends DefaultAdapter<FileTransaction> {

	FileTransYifaAdapter fileTransYifaAdapter;
	public FileTransYifaAdapter(List<FileTransaction> datas, ListView lv) {
		super(datas, lv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BaseHolder<FileTransaction> getHolder() {
		// TODO Auto-generated method stub
		return new FileTransYifaHolder();
	}

	@Override
	protected void processDatasList() {
		// TODO Auto-generated method stub
		
	}

}
