package com.example.multisendfile.adapter;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multisendfile.R;
import com.example.multisendfile.utils.UiUtils;

/***接收人显示的ListView的适配器**/
public class NameAdapter extends BaseAdapter{

	private List<String> datas;
	private DeleteClickListener listener;
	
	
	public DeleteClickListener getListener() {
		return listener;
	}

	public void setListener(DeleteClickListener listener) {
		this.listener = listener;
	}

	public NameAdapter(List<String> datas) {
		super();
		this.datas = datas;
	}

	
	
	public List<String> getDatas() {
		return datas;
	}

	public void setDatas(List<String> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas != null ? datas.size() : 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(UiUtils.getContext(), R.layout.listview_item_name_list, null);
			holder = new ViewHolder();
			holder.tv_Name = (TextView) convertView.findViewById(R.id.listview_name);
			holder.delete_Name = (ImageView) convertView.findViewById(R.id.btn_name);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_Name.setText(datas.get(position));
		holder.delete_Name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datas.remove(position);
				if(listener!=null){
					listener.deleteClick(position);
				}
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	class ViewHolder{
		TextView tv_Name;
		ImageView delete_Name;
	}
	
	 public interface DeleteClickListener{
    	 void deleteClick(int position);
     }
}
