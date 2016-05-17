package com.zhjy.iot.mobile.oa.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.entity.AppendDoc;
import com.zhjy.iot.mobile.oa.entity.FilePopupData;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-31上午11:50:17
 * 
 * @描述 listView的适配器
 */
public class FileAdapter extends BaseAdapter{

	private List<AppendDoc> datas;
	private Context context;
	private PopupWindow popupWindow;
	private FilePopupDismissListener listener;
	
	public FileAdapter(List<AppendDoc> datas) {
		super();
		this.datas = datas;
	}

	
	public FileAdapter(List<AppendDoc> datas, Context context) {
		super();
		this.datas = datas;
		this.context = context;
	}

	
	
	public FileAdapter(List<AppendDoc> datas, Context context,
			PopupWindow popupWindow) {
		super();
		this.datas = datas;
		this.context = context;
		this.popupWindow = popupWindow;
	}


	public int getCount() {
		return datas.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		return datas.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View contentView=View.inflate(context, R.layout.item_popup_file,null);
		TextView fileName=(TextView) contentView.findViewById(R.id.fileName);
		final CheckBox cb=(CheckBox) contentView.findViewById(R.id.cb);
		fileName.setText(datas.get(position).getDocName());
		
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
				System.out.println("setOnCheckedChangeListener isCheckedV:"+isChecked+"..position:"+position);
			    cb.setChecked(isChecked);
			    UiUtils.getHandler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						if(isChecked && popupWindow.isShowing()){
							listener.getItemClickFile(position);
						}
					}
				}, 200);
				
			}
		});
		
		contentView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//直接进行关闭
				cb.setChecked(true);
				UiUtils.getHandler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if(popupWindow.isShowing()){
							listener.getItemClickFile(position);
						}
					}
				}, 200);
			}
		});
		return contentView;
	}
	
	public interface FilePopupDismissListener{
		public void getItemClickFile(int position);
	}

	public FilePopupDismissListener getListener() {
		return listener;
	}

	public void setListener(FilePopupDismissListener listener) {
		this.listener = listener;
	}
}
