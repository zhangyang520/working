package com.zhjy.iot.mobile.oa.tree;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.tree.inner.TreeListViewAdapter;

public class MyTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

	public MyTreeListViewAdapter(ListView mTree, Context context,
			List<T> datas, int defaultExpandLevel,boolean isHide)
			throws IllegalArgumentException, IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel,isHide);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public View getConvertView(TreeNode node, int position, View convertView,
			ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.item_tree_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.id_treenode_icon);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.id_treenode_name);
			viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.id_treeNode_check);
			
			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -1)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
		} else
		{
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
		}

		if(node.isHideChecked()){
			viewHolder.checkBox.setVisibility(View.GONE);
		}else{
			viewHolder.checkBox.setVisibility(View.VISIBLE);
			setCheckBoxBg(viewHolder.checkBox,node.isChecked());
		}
		viewHolder.label.setText(node.getName());
		
		
		return convertView;
	}
	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
		CheckBox checkBox;
	}
	
	/**
	 * checkbox是否显示
	 * @param cb
	 * @param isChecked
	 */
	private void setCheckBoxBg(CheckBox cb,boolean isChecked){
		if(isChecked){
			cb.setBackgroundResource(R.drawable.check_box_bg_check);
		}else{
			cb.setBackgroundResource(R.drawable.check_box_bg);
		}
	}
}
