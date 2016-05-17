package com.zhjy.iot.mobile.oa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 
 * @项目名：MobileOA 
 * @类名称：ListViewForScrollView   
 * @类描述：   文件传阅详情页收件人显示的ListView
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-4-1 下午3:05:05  
 * @version    
 *
 */
public class ListViewForScrollView extends ListView {

	public ListViewForScrollView(Context context) {
		super(context);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewForScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	/**复写这个方法，MeasureSpec能更好地测量布局内容宽度和高度*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		        MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
