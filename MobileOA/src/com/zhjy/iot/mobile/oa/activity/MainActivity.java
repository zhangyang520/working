package com.zhjy.iot.mobile.oa.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseActivity;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日上午11:18:04
 * 
 * @描述   入口的主界面
 */
public class MainActivity extends BaseActivity implements OnItemClickListener{
	
	private GridView gridView;//gridView
	String[] menus;
	MenuAdapter menuAdapter;
	private float tvHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,8, UiUtils.getContext().getResources().getDisplayMetrics());
	private int height;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		customLayout=R.layout.activity_main;
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void initViews(){
		
		gridView=(GridView)findViewById(R.id.mainMenu);
		menus = getResources().getStringArray(R.array.mainMenu);
		gridView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			

			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				int windowsHeight = UiUtils.getDefaultHeight();//屏幕的高度
				gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				int top = gridView.getTop();
				height = ((int) (windowsHeight - top - tvHeight * 2 - gridView.getVerticalSpacing()*3)) / 2;
				menuAdapter = new MenuAdapter();
				gridView.setAdapter(menuAdapter);
				gridView.measure(MeasureSpec.makeMeasureSpec(10000, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(10000, MeasureSpec.AT_MOST));
			}
		});
	    gridView.setOnItemClickListener(this);
	}
	
	
	@Override
	protected void initInitevnts(){
		
	}
	
	 class MenuAdapter extends BaseAdapter{


			@Override
			public int getCount() {
				return menus==null?0:menus.length;
			}

			@Override
			public Object getItem(int position) {
				return menus==null?0:position;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(menus == null)
					return null;
				TextView tv = null;
				if(convertView == null){
					tv = new TextView(MainActivity.this);
					tv.setTextColor(getResources().getColor(R.color.mainmenuColor));
					tv.setTextSize(tvHeight);
					tv.setGravity(Gravity.CENTER);
					Drawable top = null;
					switch (position) {
					case 0:
						top = getResources().getDrawable(R.drawable.menubutton1);
						break;
					case 1:
						top = getResources().getDrawable(R.drawable.menubutton2);
						break;
					case 2:
						top = getResources().getDrawable(R.drawable.menubutton3);
						break;
					case 3:
						top = getResources().getDrawable(R.drawable.menubutton4);
						break;
					default:
						break;
					}
					top.setBounds(0, 0, height, height);
					tv.setCompoundDrawables(null,top,null, null);
				}else{
					tv = (TextView) convertView;
				}
				tv.setText(menus[position]);
				tv.measure(MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST));
				System.out.println("tv measured height:"+tv.getMeasuredHeight());
				return tv;
			}
	    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
		intent.putExtra("showIndex", position);
		startActivity(intent);
	}
}
