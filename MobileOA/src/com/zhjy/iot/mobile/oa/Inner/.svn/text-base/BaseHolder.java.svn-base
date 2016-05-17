package com.zhjy.iot.mobile.oa.Inner;

import android.view.View;

public abstract class BaseHolder<Data>  {
	private int type;
	private View contentView;
	private Data data;
	private int position;
	public BaseHolder(){
		contentView=initView();
		contentView.setTag(this);
	}
	
	public BaseHolder(int type){
		this.type=type;
		contentView=initView();
		contentView.setTag(this);
	}
	/** 初始化view*/
	public  abstract View initView();
	public View getContentView() {
		return contentView;
	}
	public void setData(Data data){
		this.data=data;
		refreshView(data);
	}
	
	public void setData(Data data,int position){
		this.data=data;
		this.position=position;
		refreshView(data);
	}
	/** 刷新数据*/
	public abstract void refreshView(Data data);
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}
	
	
}
