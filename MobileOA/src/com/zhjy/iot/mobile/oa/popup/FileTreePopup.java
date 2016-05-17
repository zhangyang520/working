package com.zhjy.iot.mobile.oa.popup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.tree.MyTreeListViewAdapter;
import com.zhjy.iot.mobile.oa.tree.TreeNode;
import com.zhjy.iot.mobile.oa.tree.inner.TreeListViewAdapter.OnTreeNodeClickListener;

/**
 * 
 * @author zhangyang
 * 
 * @日期 2016-3-31上午11:40:59
 * 
 * @描述 展现的人员树的工具类
 */
public class FileTreePopup{
	
	private static FileTreePopup instance;
	
	private OnFileTreePopupClickListenter okListener;
	
	private FileTreePopup(){
		
	}
	
	public static FileTreePopup getInstance(){
		if(instance==null){
			synchronized (FileTreePopup.class) {
				if(instance==null){
					instance=new FileTreePopup();
				}
			}
		}
		
		return instance;
	}
	private  List<TreeNode> checkedTreeNodes;
	private  PopupWindow popupWindow;
	private  View contentView;
	public  void showPopupFileList(final Activity activity,final View parent,List<Node> nodesList){
		
		if(checkedTreeNodes!=null){
			checkedTreeNodes.clear();
		}
		//首先进行从数据库查询所有的人员的信息
		try {
			if(popupWindow==null){
				popupWindow = new PopupWindow(
						(int)activity.getResources().getDimension(R.dimen.file_popup_width),
						(int)activity.getResources().getDimension(R.dimen.file_popup_height));
				contentView = View.inflate(activity, R.layout.popup_file_tree_content, null);
			}
			//取得取消按钮
			Button btn_cancel=(Button) contentView.findViewById(R.id.btn_cancel);
			btn_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v){
				    //取消按钮
					popupWindow.dismiss();
				}
			});
			
			//取得确定按钮
			Button btn_ok=(Button) contentView.findViewById(R.id.btn_ok);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v){
					//进行执行监听器
					if(okListener!=null){
						okListener.onOkClickListener();
					}
				}
			});
			
			//进行获取listView
			ListView listView=(ListView) contentView.findViewById(R.id.lv);
			//final List<Node> datas = initDatas();
			//进行获取要显示"下一阶段的办理人"的集合
//			final List<Node> datas = data.getOperationSet().get(index).getParticipanSet();
			@SuppressWarnings("rawtypes")
			MyTreeListViewAdapter adapter = new MyTreeListViewAdapter<Node>(listView, activity,
					nodesList, 10, false);

			
			adapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
				
				@Override
				public void onClick(TreeNode node, int position) {
//					if (node.isLeaf()) {
//						Toast.makeText(activity, node.getName(),Toast.LENGTH_SHORT).show();
//					}
				}

				@Override
				public void onCheckChange(TreeNode node, int position,
						List<TreeNode> checkedNodes) {

					checkedTreeNodes=checkedNodes;
//					Toast.makeText(activity,"onCheckChange:"+node.getName()+"..isChecked:"+node.isChecked()+"size:"+checkedNodes.size(),0).show();
				}
			});
			
			listView.setAdapter(adapter);
			listView.setCacheColorHint(R.color.white);
			listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_INSET);
			
			popupWindow.setContentView(contentView);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.popup_file_round_rect));
			popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	
	}


	//jinx
	private static List<Node> initDatas() {
		List<Node> mDatas=new ArrayList<Node>();
		mDatas.add(new Node("中国古代","50", "0"));
		mDatas.add(new Node("唐朝","2", "50"));
		mDatas.add(new Node("宋朝","3", "50"));
		mDatas.add(new Node("明朝","4", "50"));
		mDatas.add(new Node("李世民","5", "2"));
		mDatas.add(new Node("李白","6", "2"));

		mDatas.add(new Node("赵匡胤","7", "3"));
		mDatas.add(new Node("苏轼","8", "3"));

		mDatas.add(new Node("朱元璋","9", "4"));
		mDatas.add(new Node("唐伯虎","10", "4"));
		mDatas.add(new Node("文征明","11", "4" ));
		mDatas.add(new Node("赵建立","12", "7" ));
		mDatas.add(new Node( "苏东东","13", "8"));
		mDatas.add(new Node("秋香","14", "10"));
		mDatas.add(new Node("魏征","15", "2" ));
		mDatas.add(new Node("程咬金","16", "2"));
		mDatas.add(new Node("薛丁山","17", "2" ));
		mDatas.add(new Node("秦叔宝","18", "2" ));
		mDatas.add(new Node("宇文成都","19", "2" ));
		mDatas.add(new Node("宇文成都","20", "2"));
		mDatas.add(new Node("宇文成都","21", "2" ));
		mDatas.add(new Node("陆游","22", "3" ));
		mDatas.add(new Node("宋江","23", "3" ));
		mDatas.add(new Node("卢俊义","24", "3"));
		mDatas.add(new Node("吴用","25", "3"));
		mDatas.add(new Node("关胜","26", "3"));
		mDatas.add(new Node( "徐达","27", "4"));
		mDatas.add(new Node("常遇春","28", "4"));
		mDatas.add(new Node("胡惟庸","29","4" ));
		mDatas.add(new Node("刘伯温","30", "4"));
		mDatas.add(new Node( "陈友谅","31", "4"));
		mDatas.add(new Node("朱棣","32", "4"));
		return mDatas;
	}

	public  PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public  void setPopupWindow(PopupWindow popupWindow) {
		popupWindow = popupWindow;
	}
	
	
	/**
	 * @return the okListener
	 */
	public OnFileTreePopupClickListenter getOkListener() {
		return okListener;
	}

	/**
	 * @param okListener the okListener to set
	 */
	public void setOkListener(OnFileTreePopupClickListenter okListener) {
		this.okListener = okListener;
	}

	
	/**
	 * @return the checkedTreeNodes
	 */
	public List<TreeNode> getCheckedTreeNodes() {
		return checkedTreeNodes;
	}

	/**
	 * @param checkedTreeNodes the checkedTreeNodes to set
	 */
	public void setCheckedTreeNodes(List<TreeNode> checkedTreeNodes) {
		this.checkedTreeNodes = checkedTreeNodes;
	}


	public interface OnFileTreePopupClickListenter{
		void onOkClickListener();
		void onCancelClickListener();
	}
	
	public void dismisss(){
		if(popupWindow!=null){
			popupWindow.dismiss();
		}
	}
	
}

