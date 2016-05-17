package com.zhjy.iot.mobile.oa.adapter;

import java.util.List;

import android.widget.ListView;

import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.Inner.DefaultAdapter;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.holder.FileTransYishouHolder;

public class FileTransYishouAdapter extends DefaultAdapter<FileTransaction>{

	FileTransYishouHolder fileTransHolder;
	public FileTransYishouAdapter(List<FileTransaction> datas, ListView lv) {
		super(datas, lv);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BaseHolder<FileTransaction> getHolder() {
		// TODO Auto-generated method stub
//		if(fileTransHolder==null){
//			fileTransHolder=new FileTransYishouHolder();
//		}
//		return fileTransHolder;
		
		return  new FileTransYishouHolder();
	}

	@Override
	protected void processDatasList() {
		// TODO Auto-generated method stub
		
	}

}
