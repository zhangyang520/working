package com.zhjy.iot.mobile.oa.holder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjy.iot.mobile.oa.R;
import com.zhjy.iot.mobile.oa.Inner.BaseHolder;
import com.zhjy.iot.mobile.oa.activity.LoadingActivity;
import com.zhjy.iot.mobile.oa.activity.OperationProcessActivity;
import com.zhjy.iot.mobile.oa.application.MobileOaApplication;
import com.zhjy.iot.mobile.oa.dao.NodeDao;
import com.zhjy.iot.mobile.oa.entity.FormType;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.Operation;
import com.zhjy.iot.mobile.oa.entity.Opinion;
import com.zhjy.iot.mobile.oa.entity.OpinionSeirer;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveDetail;
import com.zhjy.iot.mobile.oa.entity.ReceiveFileState;
import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.popup.FileAppendPopup;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup;
import com.zhjy.iot.mobile.oa.popup.FileTreePopup.OnFileTreePopupClickListenter;
import com.zhjy.iot.mobile.oa.popup.IsFileTransPopup;
import com.zhjy.iot.mobile.oa.tree.TreeNode;
import com.zhjy.iot.mobile.oa.utils.DateUtil;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
import com.zhjy.iot.mobile.oa.utils.NetUtils;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/***
 * 
 * @项目名：MobileOA 
 * @类名称：QingShiPiBanDanHolder   
 * @类描述：   四种表单的Holder
 * 			1.普通收文；2.文件批办单；3.请示批办单；4.区领导批示批办单
 * 
 * @创建人：HuangXianFeng 
 * @修改人：    
 * @创建时间：2016-3-30 上午10:39:25  
 * @version    
 *
 */
public class FormHolder extends BaseHolder<PolicyReceiveDetail>{
	
	private Activity activity;
	private TextView title;
	private TextView toUnit;
	private TextView receiptNum;
	private TextView dense;
	private TextView fileNums;
	private TextView receivetime;
	private Button button;
	
	private TextView opinion_0;
	private EditText edittext_0;
	private RelativeLayout rl_0;
	private Button signName_0;
	
	private TextView opinion_1;
	private EditText edittext_1;
	private RelativeLayout rl_1;
	private Button signName_1;
	
	private TextView opinion_2;
	private EditText edittext_2;
	private RelativeLayout rl_2;
	private Button signName_2;
	
	private TextView opinion_3;
	private EditText edittext_3;
	private RelativeLayout rl_3;
	private Button signName_3;
	
	private TextView opinion_4;
	private EditText edittext_4;
	private RelativeLayout rl_4;
	private Button signName_4;
	
	private TextView opinion_5;
	private EditText edittext_5;
	private RelativeLayout rl_5;
	private Button signName_5;
	
	
	private Button operatonaProcess;
	
	private LinearLayout ll;
	private RelativeLayout rl_tounit;
	private LinearLayout  ll_receivetime;
	
	private boolean signName0Flag;
	private boolean signName1Flag;
	private boolean signName2Flag;
	private boolean signName3Flag;
	private boolean signName4Flag;
	private boolean signName5Flag;
	
	private int fileState;
	private String sign_delegate_char1="\n                    	   	 ";
	private String sign_delegate_char2="    ";
	public FormHolder(int type,Activity activity) {
		super(type);
		this.activity=activity;
	}
	
	public void setFileState(int fileState) {
		this.fileState = fileState;
	}

	@Override
	public View initView() {
		View convertView=null;
		System.out.println("FormHolder  initView.....type:"+getType());
		if (getType() == 1) {
			convertView = UiUtils.inflate(R.layout.file_form_pu_tong_shou_wen_biao_dan);
			initViewHolder(convertView);
		}else if (getType() == 2) {
			convertView = UiUtils.inflate(R.layout.file_form_wen_jian_pi_ban_dan);
			initViewHolder(convertView);
		}else if (getType() ==3) {
			convertView = UiUtils.inflate(R.layout.file_form_qing_shi_pi_ban_dan);
			initViewHolder(convertView);
		}else{
			convertView = UiUtils.inflate(R.layout.file_form_qu_ling_dao_pi_ban_dan);
			initViewHolder(convertView);
		}
		return convertView;
	}

	private void initViewHolder(View convertView) {
		
		System.out.println("initViewHolder....." + (getContentView() == null));
		rl_tounit=(RelativeLayout) convertView.findViewById(R.id.rl_tounit);
		ll_receivetime=(LinearLayout) convertView.findViewById(R.id.ll_receivetime);
		if (getType() == FormType.CommonForm.getValue()) {
			System.out.println("initViewHolder .....type 1................");
			title = (TextView) convertView.findViewById(R.id.title);
			toUnit = (TextView) convertView.findViewById(R.id.tounit);
			receivetime = (TextView) convertView.findViewById(R.id.receivetime);
			rl_tounit.setVisibility(View.GONE);
			ll_receivetime.setVisibility(View.GONE);
		}else if (getType() ==FormType.WJPBDAN.getValue()) {
			System.out.println("initViewHolder .....type 2................");
			title = (TextView) convertView.findViewById(R.id.title);
			toUnit = (TextView) convertView.findViewById(R.id.tounit);
			receiptNum = (TextView) convertView.findViewById(R.id.receiptNum);//文号
			dense = (TextView) convertView.findViewById(R.id.dense);//密级
			fileNums = (TextView) convertView.findViewById(R.id.fileNums);//份数
			receivetime = (TextView) convertView.findViewById(R.id.receivetime);
			rl_tounit.setVisibility(View.VISIBLE);
			ll_receivetime.setVisibility(View.VISIBLE);
		}else if (getType() == FormType.QSPBDAN.getValue()) {
			System.out.println("initViewHolder .....type 3................");
			title = (TextView) convertView.findViewById(R.id.title);
			toUnit = (TextView) convertView.findViewById(R.id.tounit);
			receiptNum = (TextView) convertView.findViewById(R.id.receiptNum);//文号
			dense = (TextView) convertView.findViewById(R.id.dense);//密级
			fileNums = (TextView) convertView.findViewById(R.id.fileNums);//份数
			receivetime = (TextView) convertView.findViewById(R.id.receivetime);
			rl_tounit.setVisibility(View.VISIBLE);
			ll_receivetime.setVisibility(View.VISIBLE);
		}else{
			System.out.println("initViewHolder .....type else................");
			title = (TextView) convertView.findViewById(R.id.title);
			toUnit = (TextView) convertView.findViewById(R.id.tounit);
			receiptNum = (TextView) convertView.findViewById(R.id.receiptNum);//文号
			dense = (TextView) convertView.findViewById(R.id.dense);//密级
			fileNums = (TextView) convertView.findViewById(R.id.fileNums);//份数
			receivetime = (TextView) convertView.findViewById(R.id.receivetime);
			rl_tounit.setVisibility(View.VISIBLE);
			ll_receivetime.setVisibility(View.VISIBLE);
		}
		
		button = (Button) convertView.findViewById(R.id.AppendDoc);
		
		//附件点击事件的处理
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//如果有,进行跳转附件提示框
				if (NetUtils.checkNetwork(activity)) {
					if(getData().getAppendDocSet()!=null && getData().getAppendDocSet().size()>=1){
						FileAppendPopup.getInstance().showPopupFileList(activity, button,getData().getAppendDocSet());
					}else{
						Toast.makeText(UiUtils.getContext(),"附件为空!", 0).show();
					}
				}else{
					UiUtils.showToast("请检查网络！");
				}
			}
		});
		
		opinion_0 = (TextView) convertView.findViewById(R.id.tv_opinion_0);
		edittext_0 = (EditText) convertView.findViewById(R.id.edittext_opinion_0);
		signName_0 = (Button) convertView.findViewById(R.id.sign_name_0);
		rl_0=(RelativeLayout) convertView.findViewById(R.id.rl_0);
		
		opinion_1 = (TextView) convertView.findViewById(R.id.tv_opinion_1);
		edittext_1 = (EditText) convertView.findViewById(R.id.edittext_opinion_1);
		signName_1 = (Button) convertView.findViewById(R.id.sign_name_1);
		rl_1=(RelativeLayout) convertView.findViewById(R.id.rl_1);
		
		opinion_2 = (TextView) convertView.findViewById(R.id.tv_opinion_2);
		edittext_2 = (EditText) convertView.findViewById(R.id.edittext_opinion_2);
		signName_2 = (Button) convertView.findViewById(R.id.sign_name_2);
		rl_2=(RelativeLayout) convertView.findViewById(R.id.rl_2);
		
		opinion_3 = (TextView) convertView.findViewById(R.id.tv_opinion_3);
		edittext_3 = (EditText) convertView.findViewById(R.id.edittext_opinion_3);
		signName_3 = (Button) convertView.findViewById(R.id.sign_name_3);
		rl_3=(RelativeLayout) convertView.findViewById(R.id.rl_3);
		
		opinion_4 = (TextView) convertView.findViewById(R.id.tv_opinion_4);
		edittext_4 = (EditText) convertView.findViewById(R.id.edittext_opinion_4);
		signName_4 = (Button) convertView.findViewById(R.id.sign_name_4);
		rl_4=(RelativeLayout) convertView.findViewById(R.id.rl_4);
		
		
		opinion_5 = (TextView) convertView.findViewById(R.id.tv_opinion_5);
		edittext_5 = (EditText) convertView.findViewById(R.id.edittext_opinion_5);
		signName_5 = (Button) convertView.findViewById(R.id.sign_name_5);
		rl_5=(RelativeLayout) convertView.findViewById(R.id.rl_5);
		
		operatonaProcess = (Button) convertView.findViewById(R.id.operation_process_btn);
		
		//进行转换为linearLayout
		ll=(LinearLayout) convertView.findViewById(R.id.ll);
		ll.setGravity(Gravity.CENTER);
		setAction();
	}



	@Override
	public void refreshView(final PolicyReceiveDetail data) {
		System.out.println("FormHolder refreshView................"+(data==null)+"..hashcode:"+data.hashCode());
		System.out.println("title is null:"+(title==null)+"..........data.getTitle() is null:"+(data.getTitle()==null));
		
		//如果是普通收文: 只有标题
		if(getType() == 1){
			title.setText(data.getTitle());
		}
		
		//如果是其他收文:增加其他dense，fileNums，receivetime
		if(getType()==2 || getType()==3 || getType()==4){
			title.setText(data.getTitle());
			toUnit.setText(data.getTounit());
			receivetime.setText(data.getReceivetime());
			dense.setText(data.getDense());
			fileNums.setText(data.getFileNums());
			receiptNum.setText(data.getReceiptNum());
		}
		
		if(data.getOpinionSet()!=null && data.getOpinionSet().entrySet()!=null){
			for(Map.Entry<OpinionSeirer,Opinion> entry:data.getOpinionSet().entrySet()){
				OpinionSeirer key=entry.getKey();
				switch(key){
					case quldpsOpinion:
						Opinion opinion4=entry.getValue();
						if(opinion4!=null){
							if(opinion4.isCanWrite()){
								rl_0.setVisibility(View.VISIBLE);
								edittext_0.setFocusable(true);
								signName_0.setOnClickListener(new OnClickListener() {
									@SuppressWarnings("deprecation")
									@Override
									public void onClick(View v) {
										if(!edittext_0.getText().toString().trim().equals("")){
											if(!signName0Flag){
												signName0Flag=true;
												signName_0.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_button_bg));
												edittext_0.setText(edittext_0.getText().toString()+sign_delegate_char1+MobileOaApplication.user.getRealName()+sign_delegate_char2+DateUtil.getCurrentTime());
												edittext_0.setEnabled(!edittext_0.isEnabled());
											}else{
												Toast.makeText(UiUtils.getContext(),"已经签名了,不能再次签名",0).show();
											}
										}else{
											Toast.makeText(UiUtils.getContext(), "请填写内容!", 0).show();
										}
									}
								});
							}else{
								rl_0.setVisibility(View.GONE);
							}
							for(int i=0;opinion4.getContentList()!=null && i<opinion4.getContentList().size();++i){
								opinion_0.setText(opinion4.getContentList().get(i));
							}
						}
						break;
						
					case handleComment:
						Opinion opinion3=entry.getValue();
						if(opinion3!=null){
							if(opinion3.isCanWrite()){
								rl_3.setVisibility(View.VISIBLE);
								edittext_3.setFocusable(true);
								signName_3.setOnClickListener(new OnClickListener() {
									
									@SuppressWarnings("deprecation")
									@Override
									public void onClick(View v) {
										if(!edittext_3.getText().toString().trim().equals("")){
											if(!signName3Flag){
												signName_3.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_button_bg));
												signName3Flag=true;
												edittext_3.setText(edittext_3.getText().toString()+sign_delegate_char1+MobileOaApplication.user.getRealName()+sign_delegate_char2+DateUtil.getCurrentTime());
												edittext_3.setEnabled(!edittext_3.isEnabled());
											}else{
												Toast.makeText(UiUtils.getContext(),"已经签名了,不能再次签名",0).show();
											}
										}else{
											Toast.makeText(UiUtils.getContext(), "请填写内容!", 0).show();
										}
									}
								});
							}else{
								rl_3.setVisibility(View.GONE);
							}
							for(int i=0;opinion3.getContentList()!=null && i<opinion3.getContentList().size();++i){
								opinion_3.setText(opinion3.getContentList().get(i));
							}
						}
						break;
						
					case intendsComment:
						Opinion opinion1=entry.getValue();
						if(opinion1!=null){
							if(opinion1.isCanWrite()){
								rl_1.setVisibility(View.VISIBLE);
								edittext_1.setFocusable(true);
								signName_1.setOnClickListener(new OnClickListener() {
									@SuppressWarnings("deprecation")
									@Override
									public void onClick(View v) {
										if(!edittext_1.getText().toString().trim().equals("")){
											if(!signName1Flag){
												signName1Flag=true;
												signName_1.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_button_bg));
												edittext_1.setText(edittext_1.getText().toString()+sign_delegate_char1+MobileOaApplication.user.getRealName()+sign_delegate_char2+DateUtil.getCurrentTime());
												edittext_1.setEnabled(!edittext_1.isEnabled());
											}else{
												Toast.makeText(UiUtils.getContext(),"已经签名了,不能再次签名",0).show();
											}
										}else{
											Toast.makeText(UiUtils.getContext(), "请填写内容!", 0).show();
										}
									}
								});
							}else{
								rl_1.setVisibility(View.GONE);
							}
							for(int i=0;opinion1.getContentList()!=null && i<opinion1.getContentList().size();++i){
								opinion_1.setText(opinion1.getContentList().get(i));
							}
						}
						break;
						
					case leadershipOppinion:
						Opinion opinion2=entry.getValue();
						if(opinion2!=null){
							if(opinion2.isCanWrite()){
								rl_2.setVisibility(View.VISIBLE);
								edittext_2.setFocusable(true);
								signName_2.setOnClickListener(new OnClickListener() {
									@SuppressWarnings("deprecation")
									@Override
									public void onClick(View v) {
										if(!edittext_2.getText().toString().trim().equals("")){ 
											if(!signName2Flag){
												signName2Flag=true;
												signName_2.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_button_bg));
	//											signName_2.setBackgroundColor(R.drawable.btn_four_form_selector_bg_2);//btn_four_form_selector_bg
												edittext_2.setText(edittext_2.getText().toString()+sign_delegate_char1+MobileOaApplication.user.getRealName()+sign_delegate_char2+DateUtil.getCurrentTime());
												edittext_2.setEnabled(!edittext_2.isEnabled());
											}else{
												Toast.makeText(UiUtils.getContext(),"已经签名了,不能再次签名",0).show();
											}
										}else{
											Toast.makeText(UiUtils.getContext(), "请填写内容!", 0).show();
										}
									}
								});
							}else{
								rl_2.setVisibility(View.GONE);
							}
							for(int i=0;opinion2.getContentList()!=null && i<opinion2.getContentList().size();++i){
								opinion_2.setText(opinion2.getContentList().get(i));
							}
						}
						break;
						
					case zgldOpinion:
						Opinion opinion5=entry.getValue();
						if(opinion5!=null){
							if(opinion5.isCanWrite()){
								rl_4.setVisibility(View.VISIBLE);
								edittext_4.setFocusable(true);
								signName_4.setOnClickListener(new OnClickListener() {
									@SuppressWarnings("deprecation")
									@Override
									public void onClick(View v) {
										if(!edittext_4.getText().toString().trim().equals("")){ 
											if(!signName4Flag){
												signName4Flag=true;
												signName_4.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_button_bg));
	//											signName_2.setBackgroundColor(R.drawable.btn_four_form_selector_bg_2);//btn_four_form_selector_bg
												edittext_4.setText(edittext_4.getText().toString()+sign_delegate_char1+MobileOaApplication.user.getRealName()+sign_delegate_char2+DateUtil.getCurrentTime());
												edittext_4.setEnabled(!edittext_4.isEnabled());
											}else{
												Toast.makeText(UiUtils.getContext(),"已经签名了,不能再次签名",0).show();
											}
										}else{
											Toast.makeText(UiUtils.getContext(), "请填写内容!", 0).show();
										}
									}
								});
							}else{
								rl_4.setVisibility(View.GONE);
							}
							for(int i=0;opinion5.getContentList()!=null && i<opinion5.getContentList().size();++i){
								opinion_4.setText(opinion5.getContentList().get(i));
							}
						}
						break;
						
					case kyOpinion:
						Opinion opinion6=entry.getValue();
						if(opinion6!=null){
							if(opinion6.isCanWrite()){
								rl_5.setVisibility(View.VISIBLE);
								edittext_5.setFocusable(true);
								signName_5.setOnClickListener(new OnClickListener() {
									@SuppressWarnings("deprecation")
									@Override
									public void onClick(View v) {
										if(!edittext_5.getText().toString().trim().equals("")){ 
											if(!signName5Flag){
												signName5Flag=true;
												signName_5.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_shape_of_button_bg));
	//											signName_2.setBackgroundColor(R.drawable.btn_four_form_selector_bg_2);//btn_four_form_selector_bg
												edittext_5.setText(edittext_5.getText().toString()+sign_delegate_char1+MobileOaApplication.user.getRealName()+sign_delegate_char2+DateUtil.getCurrentTime());
												edittext_5.setEnabled(!edittext_5.isEnabled());
											}else{
												Toast.makeText(UiUtils.getContext(),"已经签名了,不能再次签名",0).show();
											}
										}else{
											Toast.makeText(UiUtils.getContext(), "请填写内容!", 0).show();
										}
									}
								});
							}else{
								rl_5.setVisibility(View.GONE);
							}
							for(int i=0;opinion6.getContentList()!=null && i<opinion6.getContentList().size();++i){
								opinion_5.setText(opinion6.getContentList().get(i));
							}
						}
						break;
				}
			}
		}
		
		
		//进行设置文字内容:
		System.out.println("data.getTitle() == " + data.getTitle() + "data.getTounit()== " + data.getTounit());
		getContentView().getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout(){
				if (fileState == ReceiveFileState.YIBAN.getValue()) {
					//如果是已办的状态:不进行任何的操作按钮的显示
				}else{
					getContentView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
					final int llWidth=ll.getMeasuredWidth();
					//进行动态生成按钮
					//进行/2生成商，%2生成余数
					if(data.getOperationSet()!=null){
						int quotient=data.getOperationSet().size()/2;
						int remainder=data.getOperationSet().size()%2;
						System.out.println("data.getOperationSet().size():"+data.getOperationSet().size()+//
																			"..quotient:"+quotient+"..remainder:"+remainder);
						if(quotient>0 || remainder>0){
							for(int i=0;i<quotient+remainder;++i){
								if(i==quotient+remainder-1){
									//如果是最后一个
									if(remainder==0){
										//余数为0 生成两个按钮
										generateTwoBtn(i,llWidth-UiUtils.getDimen(R.dimen.operation_btn_left_width));
									}else{
										//余数为1 生成一个按钮
										generateOneBtn(i,llWidth-UiUtils.getDimen(R.dimen.operation_btn_left_width));
									}
								}else{
									//不是最后一个 生成两个按钮
									generateTwoBtn(i,llWidth-UiUtils.getDimen(R.dimen.operation_btn_left_width));
								}
							}
						}
					}
				}
			}
			
			/**
			 * 生成一个按钮
			 * @param i
			 * @param llWidth 
			 */
			@SuppressWarnings("deprecation")
			private void generateOneBtn(final int i, final int llWidth) {
				int index=2*(i);
				final Button button=new Button(UiUtils.getContext());
				
				button.setLayoutParams(new LinearLayout.LayoutParams(llWidth/2, UiUtils.getDimen(R.dimen.operation_btn_height)));
			    if(i>0){
			    	((MarginLayoutParams)button.getLayoutParams()).setMargins(0, UiUtils.getDimen(R.dimen.operation_btn_margin_top), 0, 0);
			    }
				button.setText(data.getOperationSet().get(index).getOperationName());
				button.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_four_form_selector_bg_2));
				generateBtnSetOnClick(index,button,data);
				
				ll.addView(button, ll.getChildCount()-1);
				
				
			}

			/**
			 * 生成两个按钮
			 * @param i
			 * @param llWidth 
			 */
			@SuppressWarnings("deprecation")
			private void generateTwoBtn(int i, int llWidth){
				
				LinearLayout llChild=new LinearLayout(UiUtils.getContext());
				llChild.setOrientation(LinearLayout.HORIZONTAL);
				llChild.setLayoutParams(new LinearLayout.LayoutParams(//
															LinearLayout.LayoutParams.MATCH_PARENT, //
																			LinearLayout.LayoutParams.WRAP_CONTENT));
				int index=2*(i);
				System.out.println("generateTwoBtn index:"+index);
				Button button1=new Button(UiUtils.getContext());
				button1.setLayoutParams(new LinearLayout.LayoutParams(llWidth/2, UiUtils.getDimen(R.dimen.operation_btn_height)));
				button1.setText(data.getOperationSet().get(index).getOperationName());
				button1.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_four_form_selector_bg_2));
				if(i>0){
					((MarginLayoutParams)button1.getLayoutParams()).setMargins(0, UiUtils.getDimen(R.dimen.operation_btn_margin_top), UiUtils.getDimen(R.dimen.operation_btn_margin_left), 0);
				}else{
					((MarginLayoutParams)button1.getLayoutParams()).setMargins(0, 0, UiUtils.getDimen(R.dimen.operation_btn_margin_left), 0);
				}
				button1.requestLayout();
				generateBtnSetOnClick(index,button1,data);
				llChild.addView(button1, 0);
				
				Button button2=new Button(UiUtils.getContext());
				button2.setLayoutParams(new LinearLayout.LayoutParams(llWidth/2, UiUtils.getDimen(R.dimen.operation_btn_height)));
				button2.setText(data.getOperationSet().get(index+1).getOperationName());
				button2.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.btn_four_form_selector_bg_2));
				
				if(i>0){
					((MarginLayoutParams)button2.getLayoutParams()).setMargins(0, UiUtils.getDimen(R.dimen.operation_btn_margin_top), 0, 0);
				}
				button2.requestLayout();
				generateBtnSetOnClick(index + 1,button2,data);
				llChild.addView(button2, 1);
				
				ll.addView(llChild, ll.getChildCount()-1);
			}
		});
	}
	/**
	 * 
	 * 描述：
	 * 1.需要传递的参数：操作项的id：（operationId）
	 * 2.判断下一节点办理人集合的大小：
	 * 				(1).如果大于1获取下一节点办理人集合
	 * 				(2).如果小于1设置默认的一个办理人， 则不弹出下一办理人列表
	 */
	protected void generateBtnSetOnClick(final int index, final Button button,
			final PolicyReceiveDetail data) {
		List<Operation> operationSet = data.getOperationSet();
		Operation operation = operationSet.get(index);
		String operationId = operation.getOperationId();
		final List<Node> participanSet = operation.getParticipanSet();
		final List<TreeNode> nextProcessDatas=new ArrayList<TreeNode>();
		final List<TreeNode> daiYueNodesData=new ArrayList<TreeNode>();
		final Map<String,String> opinionMap=new HashMap<String,String>();
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/**
				 * 判断是否是最后一个办理人：
				 * 点击之后判断,填写意见内容判断的逻辑,以及签名按钮的变灰的逻辑
				 * 1.true时，直接走else，反之
				 */
				nextProcessDatas.clear();//清除数据
				daiYueNodesData.clear();//清除数据
				boolean signName_2_visible=rl_2.getVisibility()==View.VISIBLE;
				boolean signName_0_visible=false;
				if(getType() ==4){
					signName_0_visible=rl_0.getVisibility()==View.VISIBLE;
				}
				boolean signName_1_visible=rl_1.getVisibility()==View.VISIBLE;
				boolean signName_3_visible=rl_3.getVisibility()==View.VISIBLE;
				boolean signName_4_visible=rl_4.getVisibility()==View.VISIBLE;
				boolean signName_5_visible=rl_5.getVisibility()==View.VISIBLE;
				if(signName_2_visible || signName_0_visible || signName_3_visible || signName_1_visible || signName_4_visible || signName_5_visible){ 
					//如果有一个有签名的按钮：进行需要填写内容
					if(getType()==4 && signName_0_visible && edittext_0.getEditableText().toString().trim().equals("")){
						Toast.makeText(UiUtils.getContext(), "请填写局领导批示意见!",0).show();
						return ;
					}else if(getType()==4 && signName_0_visible && !edittext_0.getEditableText().toString().trim().equals("")){
						opinionMap.put(OpinionSeirer.quldpsOpinion.getOpinionName(), edittext_0.getEditableText().toString());
					}
					
					if(signName_1_visible && edittext_1.getEditableText().toString().trim().equals("")){
						Toast.makeText(UiUtils.getContext(), "请填写拟办意见!",0).show();
						return ;
					}else if(signName_1_visible && !edittext_1.getEditableText().toString().trim().equals("")){
						opinionMap.put(OpinionSeirer.intendsComment.getOpinionName(), edittext_1.getEditableText().toString());
					}
					
					if(signName_2_visible && edittext_2.getEditableText().toString().trim().equals("")){
						Toast.makeText(UiUtils.getContext(), "请填写领导批示意见!",0).show();
						return ;
					}else if(signName_2_visible && !edittext_2.getEditableText().toString().trim().equals("")){
						opinionMap.put(OpinionSeirer.leadershipOppinion.getOpinionName(), edittext_2.getEditableText().toString());
					}
					
					if(signName_3_visible && edittext_3.getEditableText().toString().trim().equals("")){
						Toast.makeText(UiUtils.getContext(), "请填写办理意见!",0).show();
						return ;
					}else if(signName_3_visible && !edittext_3.getEditableText().toString().trim().equals("")){
						opinionMap.put(OpinionSeirer.handleComment.getOpinionName(), edittext_3.getEditableText().toString());
					}
					
					if(signName_4_visible && edittext_4.getEditableText().toString().trim().equals("")){
						Toast.makeText(UiUtils.getContext(), "请填写主管领导批示意见!",0).show();
						return ;
					}else if(signName_4_visible && !edittext_4.getEditableText().toString().trim().equals("")){
						opinionMap.put(OpinionSeirer.zgldOpinion.getOpinionName(), edittext_4.getEditableText().toString());
					}
					
					if(signName_5_visible && edittext_5.getEditableText().toString().trim().equals("")){
						Toast.makeText(UiUtils.getContext(), "请填写科员意见!",0).show();
						return ;
					}else if(signName_5_visible && !edittext_5.getEditableText().toString().trim().equals("")){
						opinionMap.put(OpinionSeirer.kyOpinion.getOpinionName(), edittext_5.getEditableText().toString());
					}
				}
				
				if (!data.getActivityId().equals(MobileOAConstant.WORKITEM_CONSTANT)) {
					/**
					 * 判断下一办理人集合大小
					 * 1.true时，传递办理人集合进行显示
					 * 2.false时，
					 */
					if (participanSet!=null && participanSet.size() > 1){
						//需要从数据库中查询对应的结构树
						try {
							//将父节点进行取出
							List<Node> copyNodesList=new ArrayList<Node>();
							for(Node data:participanSet){
								System.out.println("data.getDeptId()" + data.getDeptId() + ";data.getDeptId() Name = " + data.getNodeName());
								Node parent=NodeDao.getInstance().getNodeById(data.getDeptId());
								if(!parent.getDeptId().equals(MobileOAConstant.DEFAULT_DEPT_STRING)){
									while(parent!=null){
										if(parent.getDeptId().equals(MobileOAConstant.DEFAULT_DEPT_STRING)){
											break;
										}else{
											if(!copyNodesList.contains(parent) && !participanSet.contains(parent)){
												copyNodesList.add(parent);
											}
											parent=NodeDao.getInstance().getNodeById(parent.getDeptId());
										}
									}
								}
							}
							//将其增加到对应的数据中
							participanSet.addAll(copyNodesList);
							//进行设置ok的监听器
							FileTreePopup.getInstance().setOkListener(new OnFileTreePopupClickListenter() {
								@Override
								public void onOkClickListener(){
										//主要是进行判断是否选择了,下一阶段的选择了代理人
									//判断需要选择的下一阶段办理人的集合是否>=1
									if(FileTreePopup.getInstance().getCheckedTreeNodes()!=null && //
															FileTreePopup.getInstance().getCheckedTreeNodes().size()>=1){
										//需要进行选择的下一阶段的代理人拷贝到nextProcessDatas
										for(TreeNode treeNode:FileTreePopup.getInstance().getCheckedTreeNodes()){
											if(!treeNode.isIdDeptFlag()){
												nextProcessDatas.add(treeNode);
											}
										}
										
										if(nextProcessDatas.size()>0){
											System.out.println("nextProcessDatas:"+nextProcessDatas.toString());
											if(FileTreePopup.getInstance().getPopupWindow().isShowing()){
												FileTreePopup.getInstance().getPopupWindow().dismiss();
											}
											
											//进行判断能够待阅
											if(data.isCanNotify()){
												initIsFileTransPopup(daiYueNodesData);
												IsFileTransPopup.getInstance().showPopupIsFileTran(activity,button,data.getAllNodeList());
											}else{
												//不能够待阅
												processActionIntent();
											}
										}else{
											Toast.makeText(UiUtils.getContext(),"请选择对应的办理人!", 0).show();
										}
									}else{
										Toast.makeText(UiUtils.getContext(),"请选择对应的办理人!", 0).show();
									}
								}

								@Override
								public void onCancelClickListener() {
									//不做处理
								}
							});
							FileTreePopup.getInstance().showPopupFileList(activity,button,participanSet);
						} catch (ContentException e) {
							//查找失败 需要提示更新数据
							e.printStackTrace();
						}
					}else{
						/***直接获取办理人信息然后判断是否传阅**/
						if (data.isCanNotify()){
							initIsFileTransPopup(daiYueNodesData);
							IsFileTransPopup.getInstance().showPopupIsFileTran(activity, button, data.getAllNodeList());
						}else{
							//等待修改中.......
							processActionIntent();
						}
					}
				}else{
					if (data.isCanNotify()){
						initIsFileTransPopup(daiYueNodesData);
						IsFileTransPopup.getInstance().showPopupIsFileTran(activity,button,data.getAllNodeList());
					}else{
						processActionIntent();
					}
				}
			}

			
			private void initIsFileTransPopup(
					final List<TreeNode> daiYueNodesData) {
				//进行设置"点击  是否待阅的确定按钮"
				IsFileTransPopup.getInstance().setListener(new OnFileTreePopupClickListenter() {
					@Override
					public void onOkClickListener(){
						
						if(FileTreePopup.getInstance().getCheckedTreeNodes()!=null && FileTreePopup.getInstance().getCheckedTreeNodes().size()>=1){
							//需要选择的待阅的人  拷贝到daiYueNodesData集合中
							for(TreeNode treeNode:FileTreePopup.getInstance().getCheckedTreeNodes()){
								if(!treeNode.isIdDeptFlag()){
									daiYueNodesData.add(treeNode);
								}
							}
							//除去部门之外 看人员集合的长度
							if(daiYueNodesData.size()>0){
								System.out.println("daiYueNodesData:"+daiYueNodesData.toString());
								if(FileTreePopup.getInstance().getPopupWindow().isShowing()){
									FileTreePopup.getInstance().getPopupWindow().dismiss();
								}
								processActionIntent();
							}else{
								Toast.makeText(UiUtils.getContext(), "请选择待阅人员!", 0).show();
							}
							
						}else{
							Toast.makeText(UiUtils.getContext(), "请选择待阅人员!", 0).show();
						}
					}
					
					//待阅处理点击取消后:
					@Override
					public void onCancelClickListener() {
						//进行访问网络:操作
						processActionIntent();
					}
				});
			}
			
			private void processActionIntent() {
				try {
					//等待修改中.......
					Intent intent = new Intent(activity,LoadingActivity.class);
					intent.putExtra(MobileOAConstant.LOADING_IS_PREVENT_BACK, true);
					intent.putExtra(MobileOAConstant.LOADING_TITLE_KEY, "办理中...");
					//com.zhjy.iot.mobile.oa.protocol.PolicyReceiveProcessActionProtocol  protocolClassName
					intent.putExtra("protocolClassName", "com.zhjy.iot.mobile.oa.protocol.PolicyReceiveProcessActionProtocol");
					UrlParamsEntity mUrlParamsEntity = new UrlParamsEntity();
					mUrlParamsEntity.setMethodName(MobileOAConstant.POLICY_RECEIVE_ACTION);
					LinkedHashMap<String, String> paramsMap = new LinkedHashMap<String, String>();
					paramsMap.put("tranCode", "tran00007");
					
					//进行设置userId:
					paramsMap.put("userId",MobileOaApplication.user.getUserId());
					
					//进行获取entity的详情字符串
					String entityJsonContent=getEntityJSONContent();
					paramsMap.put("entity1", entityJsonContent);
					
					//进行设置是否有子节点
					paramsMap.put("hasSubGrid", false+"");
					
					//进行设置选择发送待阅人的Json数据
					String notifyNodes=getNodeListJsonString(daiYueNodesData);
					paramsMap.put("notifyPersons1", notifyNodes);
					
					//进行设置下一流程的办理人:
					String nextProcessNodes=getNodeListJsonString(nextProcessDatas);
					paramsMap.put("Participants1", nextProcessNodes);
					
					//进行设置点击的操作流程名称   获取操作名称的标识
					String operactionId=data.getOperationSet().get(index).getOperationId();
					paramsMap.put("appointActivity", operactionId);
					
					intent.putExtra("urlParamsEntitiy", mUrlParamsEntity);
					mUrlParamsEntity.setParamsHashMap(paramsMap);
					if(mUrlParamsEntity.getParamsHashMap()!=null){
						for(Map.Entry<String, String> entity:mUrlParamsEntity.getParamsHashMap().entrySet()){
							//进行设置参数
							System.out.println("key:"+entity.getKey()+"..value:"+entity.getValue());
						}
					}
					activity.startActivityForResult(intent, MobileOAConstant.BANLI_REQUEST_CODE);
				} catch (ContentException e) {
					e.printStackTrace();
					UiUtils.showToast(e.getMessage());
				}
			}
			
			/**
			 * 进行获取nodeList的JsonString
			 * @param nodeList
			 * @return
			 * @throws ContentException 
			 */
			private String getNodeListJsonString(List<TreeNode> nodeList) throws ContentException {
				JSONArray jsonArray=new JSONArray();
				try {
					for(TreeNode data:nodeList){
						JSONObject jsonObject=new JSONObject();
						jsonObject.put("id",data.getNodeId());
						jsonObject.put("name", data.getNodeName());
						jsonObject.put("nodeType","emp");
						jsonArray.put(jsonObject);
					}
					return jsonArray.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new ContentException("数据格式出错!");
				}
			}

			/**
			 * 进行获取entityJsonContent字符串
			 * @return
			 * @throws ContentException 
			 */
			private String getEntityJSONContent() throws ContentException {
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject();
					//设置id
					jsonObject.put("id", data.getId());
					jsonObject.put("title", data.getTitle());
					jsonObject.put("filetypes", data.getFileTypes());
					
					//进行获取拟办意见内容
					String nbOpinion=getNbOpinion(OpinionSeirer.intendsComment);
					jsonObject.put(OpinionSeirer.intendsComment.getOpinionName(), nbOpinion);
					
					//进行设置领导批示意见
					String ldpsOpinion=getNbOpinion(OpinionSeirer.leadershipOppinion);
					jsonObject.put(OpinionSeirer.leadershipOppinion.getOpinionName(), ldpsOpinion);
					
					//进行设置办理意见
					String handleOpinion=getNbOpinion(OpinionSeirer.handleComment);
					jsonObject.put(OpinionSeirer.handleComment.getOpinionName(), handleOpinion);
					
					//进行设置流程实例id
					jsonObject.put("processInstId", data.getProcessInstId());
					
					//设置流程定义名称
					jsonObject.put("processName", data.getProcessName());
					
					//进行设置区领导批示意见
					String qldpsOpinion=getNbOpinion(OpinionSeirer.quldpsOpinion);
					jsonObject.put(OpinionSeirer.quldpsOpinion.getOpinionName(), qldpsOpinion);
					
					//进行设置主管领导批示意见
					String zgldOpinion=getNbOpinion(OpinionSeirer.zgldOpinion);
					jsonObject.put(OpinionSeirer.zgldOpinion.getOpinionName(), zgldOpinion);
					
					//进行设置科员意见
					String kyOpinion=getNbOpinion(OpinionSeirer.kyOpinion);
					jsonObject.put(OpinionSeirer.kyOpinion.getOpinionName(), kyOpinion);
					
					//设置流程实例名称
					jsonObject.put("processInstName", data.getProcessInstName());  
					
					//进行设置工作项的id
					jsonObject.put("workitemId", data.getWorkItemId());  
					return jsonObject.toString();
				} catch (JSONException e) {
					e.printStackTrace();
					throw new ContentException("数据格式出错!");
				}
			}

			/**
			 * 进行获取拟办意见内容
			 * @param intendscomment 
			 * @return
			 */
			private String getNbOpinion(OpinionSeirer intendscomment){
				
				if(data.getOpinionSet().containsKey(intendscomment)){
					//如果存在该意见 
					Opinion opinion=data.getOpinionSet().get(intendscomment);
					if(opinion!=null && opinion.getContentList()!=null){
						//先进行获取其中的内容   
						String opinionContent="";
						//进行获取以前的意见
						if(opinion.getContentList().size()>=1){
							opinionContent=opinion.getContentList().get(0);
						}
						
						if(opinion.isCanWrite()){//进行获取编辑的意见
							//进行获取单独写的意见内容
							String signOpinionContent=getOpinionSignContent(intendscomment);
							//进行组合意见
							opinionContent+=MobileOAConstant.CONSTANT_OPINION_BETWEEN_DELEAGET+signOpinionContent;
						}
						
						System.out.println("name:"+intendscomment.getOpinionName()+"...value:"+opinionContent);
						//进行返回意见
						return opinionContent;
					}else if(opinion!=null && opinion.getContentList()==null){
						//先进行获取其中的内容   
						String opinionContent="";
						if(opinion.isCanWrite()){//进行获取编辑的意见
							//进行获取单独写的意见内容
							String signOpinionContent=getOpinionSignContent(intendscomment);
							//进行组合意见
							opinionContent+=MobileOAConstant.CONSTANT_OPINION_BETWEEN_DELEAGET+signOpinionContent;
						}
						
						System.out.println("name:"+intendscomment.getOpinionName()+"...value:"+opinionContent);
						//进行返回意见
						return opinionContent;
					}else{
						return "";
					}
					
				}
				return "";
			}

			
			/**
			 * 进行获取签名意见的内容
			 * @param opinionSeirer 意见的类型
			 * @return
			 */
			private String getOpinionSignContent(OpinionSeirer opinionSeirer) {
				if(opinionSeirer==OpinionSeirer.intendsComment){
					//如果为拟办意见
					return edittext_1.getEditableText().toString().trim();
				}else if(opinionSeirer==OpinionSeirer.leadershipOppinion){
					//如果为拟办意见
					return edittext_2.getEditableText().toString().trim();
				}else if(opinionSeirer==OpinionSeirer.handleComment){
					//如果为拟办意见
					return edittext_3.getEditableText().toString().trim();
				}else if(opinionSeirer==OpinionSeirer.quldpsOpinion){
					//如果为拟办意见
					return edittext_0.getEditableText().toString().trim();
				}else if(opinionSeirer==OpinionSeirer.zgldOpinion){
					//如果为拟办意见
					return edittext_4.getEditableText().toString().trim();
				}else{
					//如果为拟办意见
					return edittext_5.getEditableText().toString().trim();
				}
			}
		});
	}

	private void setAction() {
		operatonaProcess.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(UiUtils.getContext(),OperationProcessActivity.class);
				//进行传输processInstId,userId
				System.out.println("formHoder processInstId:"+getData().getProcessInstId());
				System.out.println("formHoder userid:"+MobileOaApplication.user.getUserId());
				mIntent.putExtra("processInstId", getData().getProcessInstId());
				mIntent.putExtra("userId", MobileOaApplication.user.getUserId());
				activity.startActivity(mIntent);
			}
		});
		
	}
}
