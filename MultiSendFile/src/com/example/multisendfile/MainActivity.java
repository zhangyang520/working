package com.example.multisendfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.multisendfile.adapter.NameAdapter;
import com.example.multisendfile.adapter.NameAdapter.DeleteClickListener;
import com.example.multisendfile.utils.Constants;
/**
 * 主界面类
 * @author zhangyang
 *
 */
public class MainActivity extends Activity{
	
	private Button btnAddFile;//btn按钮
	private ListView listView;
	private NameAdapter nameAdapter;
	private List<String> fileNameLists;
	private List<File> fileList;
	private Button btn_send;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//进行获取实例
		btnAddFile=(Button)findViewById(R.id.btn_select);
		btn_send=(Button)findViewById(R.id.btn_send);
		listView=(ListView) findViewById(R.id.lv_file);
		fileNameLists=new ArrayList<String>();
		fileList=new ArrayList<File>();
		nameAdapter=new NameAdapter(fileNameLists);
		listView.setAdapter(nameAdapter);
		initListener();
	}

	/**
	 * 进行初始化监听器
	 */
	private void initListener(){
		nameAdapter.setListener(new DeleteClickListener() {
			
			@Override
			public void deleteClick(int position) {
				fileList.remove(position);
			}
		});
		//为btnAddFile设置监听器
		btnAddFile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.setAction(Intent.ACTION_GET_CONTENT);
				mIntent.setType("file/*");
				startActivityForResult(mIntent,Constants.CONSTANT_SELECT_REQUEST_CODE);
			}
		});
		
		/**
		 * 发送文件
		 */
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){	
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		//如果请求码为选择文件的请求码
		if(requestCode==Constants.CONSTANT_SELECT_REQUEST_CODE){
			if(data!=null && data.getData()!=null){
				String filePath=data.getData().getPath();
				if(!fileNameLists.contains(filePath)){
					fileNameLists.add(filePath);
					//需要进行压缩文件
					fileList.add(new File(filePath));
					nameAdapter.notifyDataSetChanged();
				}
			}
		}
	}
	
	 private void createZip(String filename, String temp_path,
		      List<File> list) {
		    File file = new File(temp_path);
		    System.err.println(temp_path);
		    File zipFile = new File(temp_path+File.separator+filename);
		    InputStream input = null;
		        try {
		    ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile,false));
		    zipOut.setEncoding("gbk");
		    zipOut.setComment(file.getName());
		            if (file.isDirectory()) {
		                for (int i = 0; i < list.size(); ++i) {
		                    input = new FileInputStream(list.get(i));
		                    zipOut.putNextEntry(new ZipEntry(list.get(i).getName()));
		                    int temp = 0;
		                    while ((temp = input.read()) != -1) {
		                        zipOut.write(temp);
		                    }
		                    input.close();
		                }
		            }
		            zipOut.close();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	 }
}
