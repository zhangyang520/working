package com.zhjy.iot.mobile.oa.application;

import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Debug;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;
import com.zhjy.iot.mobile.oa.entity.FileTransaction;
import com.zhjy.iot.mobile.oa.entity.LoginUser;
import com.zhjy.iot.mobile.oa.entity.Node;
import com.zhjy.iot.mobile.oa.entity.PolicyReceiveFile;
import com.zhjy.iot.mobile.oa.entity.SystemSetting;
import com.zhjy.iot.mobile.oa.utils.SDCardUtils;
import com.zhjy.iot.mobile.oa.utils.UiUtils;
/**
 * 
 * @author zhangyang
 * 
 * @日期 2016年3月17日上午10:10:51
 * 
 * @描述 application类
 */
public class MobileOaApplication extends Application{
	
	public static final String TAG = "com.zhjy.iot.mobile.oa.application.MobileOaApplication";
	public static final String FILE_PATH = "0A-GZW";//系统文件存储路径
	public static SharedPreferences sp ;//文件存储引用
	public static final String APP_CHARSET = "utf-8";//系统默认编码方式
	public static final int SECRET_FLAG = 8;
	public static final int TIME_OUT = 5*1000;//设置请求超时时间
	
	public static String MOBILESERVER = "";//服务器路径
	public static int MAX_WIDTH = 0;
	
	public static MobileOaApplication application;
	public static int mainId;
	private static Handler handler;
	public static  DbUtils dbUtils;
	
	public static  LoginUser user;
	@Override
	public void onCreate() {
		super.onCreate();
		
		application=this;
		handler=new Handler();
		mainId=android.os.Process.myTid();
		
		//进行初始化sdcardUtils中的路径
		SDCardUtils.getInstance().initScardUtils();
		//参数一 包名_preferences
	    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());//获取本应用的偏好
	    
	    //进行创建数据库
	   createDataBase();
	   
	   //进行创建表
	   createTable();
	   
	   //进行日志的记录
	   CrashHandler crashHandler=CrashHandler.getInstance();
	   crashHandler.init(getApplicationContext());
	   Debug.stopMethodTracing();
	}
	
	/**
	 * 进行获取数据
	 */
	private void getData(){
		try {
			List<LoginUser> datas=dbUtils.findAll(LoginUser.class);
			System.out.println("getData:"+datas.size());
			
//			List<Node> nodes=dbUtils.findAll(Selector.from(Node.class).where(WhereBuilder.b("nodeId", "=", "123")));
//			List<Node> nodes=dbUtils.findAll(Node.class);
//			System.out.println("getData nodes:"+nodes.toString());
		} catch (DbException e) {
			e.printStackTrace(); 
			Toast.makeText(this, "getData failed!...",0).show();
		}
	}

	/**
	 * 添加数据测试
	 */
	private void insertData() {
		try {
			Node node=new Node("张扬","123","456",false);
			Node node2=new Node("张扬","456","789",false);
			Node node3=new Node("张扬","111","456",false);
			dbUtils.save(node);
			dbUtils.save(node2);
			dbUtils.save(node3);
			LoginUser user=new LoginUser("zhangyang","123456");
			LoginUser user2=new LoginUser("zhangyang","123456");
			dbUtils.save(user);
			dbUtils.save(user2);
		
		} catch (DbException e) {
			e.printStackTrace();
			Toast.makeText(UiUtils.getContext(),"插入数据失败...",0).show();
		}
	}

	/**
	 * 进行创建表
	 */
	private void createTable(){
		try {
			//进行创建行政收文表
			dbUtils.createTableIfNotExist(PolicyReceiveFile.class);
			//进行创建文件传阅表
			dbUtils.createTableIfNotExist(FileTransaction.class);
			//进行创建用户表
			dbUtils.createTableIfNotExist(LoginUser.class);
			//进行创建系统设置表
			dbUtils.createTableIfNotExist(SystemSetting.class);
			dbUtils.createTableIfNotExist(Node.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * 创建数据库
	 */
	private void createDataBase() {
		try {
			DaoConfig daoConfig=new DaoConfig(this);
			daoConfig.setDbDir(getFilesDir().getAbsolutePath());
			daoConfig.setDbName("HaiDianOA.db");
			daoConfig.setDbUpgradeListener(new DbUpgradeListener() {
				
				@Override
				public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
					
				}
			});
			/**获取配置文件的信息*/
			PackageInfo packageInfo=getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_ACTIVITIES);
			daoConfig.setDbVersion(packageInfo.versionCode);
			dbUtils = DbUtils.create(daoConfig);
			dbUtils.configAllowTransaction(true);
			dbUtils.configDebug(true);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static int getMainId() {
		return mainId;
	}

	public static void setMainId(int mainId) {
		MobileOaApplication.mainId = mainId;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		MobileOaApplication.handler = handler;
	}

	
	public static DbUtils getDbUtils(){
		return dbUtils;
	}

	public static void setDbUtils(DbUtils dbUtils){
		MobileOaApplication.dbUtils = dbUtils;
	}
	
	
}
