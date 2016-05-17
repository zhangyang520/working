package com.zhjy.iot.mobile.oa.utils;

import java.util.ArrayList;
/**
 * 
 * @项目名：MobileOA
 * @类名称：移动oa
 * @类描述：自定义集合排序
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-5-11 下午4:17:15
 * @version 1
 * 
 */

public class SortArrayList<E> extends ArrayList<E>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ListAddListener<E> listAddListener;
	
	
	public ListAddListener<E> getListAddListener() {
		return listAddListener;
	}


	public void setListAddListener(ListAddListener<E> listAddListener) {
		this.listAddListener = listAddListener;
	}


	@Override
	public boolean add(E object) {
		if(listAddListener!=null){
			int index=listAddListener.processAddList(this, object);
			if(index==-1){
				//如果不存在自定义条件的位置！
				return super.add(object);
			}else{
				//否则进行在<=size的位置进行add object
				super.add(index, object);
				return true;
			}
		}else{
			return super.add(object);
		}
		
	}
	
	
	/**
	 * 集合接口的增加
	 * @author zhangyang
	 *
	 */
	public interface ListAddListener<E>{
		int processAddList(SortArrayList<E> datas,E data);
	}
}
