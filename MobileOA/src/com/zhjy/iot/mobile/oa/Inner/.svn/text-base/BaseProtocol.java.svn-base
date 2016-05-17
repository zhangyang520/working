package com.zhjy.iot.mobile.oa.Inner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.zhjy.iot.mobile.oa.entity.UrlParamsEntity;
import com.zhjy.iot.mobile.oa.exception.ContentException;
import com.zhjy.iot.mobile.oa.exception.JsonParseException;
import com.zhjy.iot.mobile.oa.utils.FileUtils;
import com.zhjy.iot.mobile.oa.utils.MobileOAConstant;
/**
 * 
 * @项目名：MoblieOA
 * @类名称：BaseProtocol
 * @类描述：基础解析协议类
 * @创建人：zhangyang
 * @修改人：
 * @创建时间：2016-4-26 下午3:26:09
 * @version 1.1
 * 
 */
public abstract class BaseProtocol<T>{
	/**
	 * 访问网络
	 * @param  url
	 * @throws JsonParseException 
	 * @throws ContentException 
	 */
	public T load(String urlStr) throws JsonParseException,ContentException{
		try {
			URL url=new URL(urlStr);
			HttpURLConnection httpUrlConn=(HttpURLConnection) url.openConnection();
			httpUrlConn.setConnectTimeout(MobileOAConstant.INTERNET_TIME_OUT);
			httpUrlConn.setReadTimeout(MobileOAConstant.INTERNET_TIME_OUT);
			httpUrlConn.setRequestMethod("GET");
			//进行连接
			httpUrlConn.connect();
			
			if(httpUrlConn.getResponseCode()==200){
				//如果访问网络成功！
				String jsonStr=FileUtils.readString(httpUrlConn.getInputStream());
				
				if(jsonStr==null){
					throw new ContentException("获取数据失败!");
				}
				//保存到本地
				saveLocal(jsonStr);
			    System.out.println("load .......11111111111111");
				//进行解析josn
				return parseJson(jsonStr);
			}else{
				   System.out.println("load .......22222222222222222");
				//服务端的异常
				throw new ContentException(httpUrlConn.getResponseCode(),"连接服务器失败!");
			}
		} catch(SocketTimeoutException e){
			System.out.println("链接超时异常"+e.getMessage());
			throw new ContentException("链接超时,请查看网络");
		}catch (IOException e){
			System.out.println("打开链接url:"+urlStr+"..问题");
			e.printStackTrace();
			//网络连接超时的异常!
			throw new ContentException("请查看网络");
		}
	}
	
	//解析json交给子类
	protected abstract T parseJson(String jsonStr) throws JsonParseException,ContentException;
	
	
	//通过webService进行解析soapObject
	protected T parseJson(SoapObject soapObject) throws JsonParseException,ContentException{
		
		return null;
	}
	/**
	 * 将内容保存到本地:
	 * 	 但是并不是所有都要进行保存到本地
	 * @param json
	 * @throws JsonParseException 
	 */
	protected void saveLocal(String json) throws JsonParseException{
		
	}
	
	public T upLoad(String urlStr,String uploadFilePath) throws JsonParseException, ContentException{
		
		BufferedInputStream bis=null;
		HttpURLConnection httpUrlConn=null;
		//进行重新连接
		int i=0;
		while(i<1){
		try{
		    System.out.println("while i:"+i);
			URL url=new URL(urlStr);
			System.out.println("1------------");
			httpUrlConn=(HttpURLConnection)url.openConnection();
			System.out.println("2------------");
			httpUrlConn.setConnectTimeout(5000);
			System.out.println("connectTimeOut:"+httpUrlConn.getConnectTimeout());
			System.out.println("3------------");
			httpUrlConn.setReadTimeout(5000);
			System.out.println("readTimeOut:"+httpUrlConn.getReadTimeout());
			System.out.println("4------------");
			httpUrlConn.setRequestMethod("POST");
			System.out.println("setDoOutput------------");
			httpUrlConn.setDoOutput(true);
			System.out.println("getOutputStream------------");
//			httpUrlConn.getOutputStream().write(content.getBytes("utf-8"));
			OutputStream os=httpUrlConn.getOutputStream();
			System.out.println("..==---====");
			//进行读文件:
			
			bis=new BufferedInputStream(new FileInputStream(uploadFilePath));
			byte []buffers=new byte[1024];
			int len=-1;
			System.out.println("..==-gegegegeg====");
			while((len=bis.read(buffers))!=-1){
				os.write(buffers, 0, len);
			}
//			System.out.println("....----------------------");
			//将流关闭
			bis.close();
			os.close();
//			System.out.println("5----------------------");
			//进行连接
			httpUrlConn.connect();
//			System.out.println("6----------------------");	
			if(httpUrlConn.getResponseCode()==200){
					//如果访问网络成功！
					String jsonStr=FileUtils.readString(httpUrlConn.getInputStream());
					
					//将文件进行关闭:
					File file=new File(uploadFilePath);
					file.delete();
					//保存到本地
					saveLocal(jsonStr);
					//进行解析josn
					return parseJson(jsonStr);
			}else{
				i++;
			}
		
			   //服务端的异常
			   throw new ContentException(httpUrlConn.getResponseCode());
		   } catch (IOException e) {
			  e.printStackTrace();
//			  System.out.println("catch.......");
			  //网络连接超时的异常!
			  i++;
		   }
		}
		 return null;
	}
	
	@SuppressWarnings("deprecation")
	public T upLoad1(String urlStr,String uploadFilePath) throws JsonParseException, ContentException{
	        try {
	          MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	          HttpClient httpClient = new HttpClient(connectionManager);
	        
	          PostMethod postMethod = new PostMethod(urlStr);
	          /** *将内容放入postMethod中** */
	          postMethod.setRequestBody(readFile(uploadFilePath));
	          /** *执行postMethod,并获取状态码 */
	          int statusCode = httpClient.executeMethod(postMethod);
	          
	          if (statusCode == 200) {// 判断是否调用成功
	             //请求成功!
	        	  String responseXML = postMethod.getResponseBodyAsString();
	        	  responseXML = responseXML.trim();
	        	  /** 连接释放 */
		          postMethod.releaseConnection();
		          System.out.println("BaseProtocol--responseXML----=" + responseXML.equals("") + ";" +  (responseXML == null) +";" + responseXML);
	        	  return parseJson(responseXML);
	          }else{
	        	  /** 连接释放 */
		          postMethod.releaseConnection();
	        	  throw new ContentException("访问网络失败!");
	          }
	          
	        } catch (SocketTimeoutException e) {
	          e.printStackTrace();
	          throw new ContentException("连接超时!");
	        } catch (IOException e) {
	        	throw new ContentException("访问网络失败!");
	        }
	}
	
	 public InputStream readFile(String fileName) {  
	        File file = new File(fileName);  
	        InputStream in = null;  
	       try {
	    	   in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
		        return in;
		    }  
	
	
	//进行访问webServicec的服务端
	public T loadDataFromWebService(UrlParamsEntity urlParamEntity) throws ContentException, JsonParseException{
		
		// 命名空间
		String nameSpace = urlParamEntity.getNamingSpace();
		// 调用的方法名称
		String methodName =urlParamEntity.getMethodName();
		// EndPoint
		String endPoint =urlParamEntity.getWebServiceUrl();
		// SOAP Action
		String soapAction = nameSpace+methodName;

	    // 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		// 设置需调用WebService接口需要传入的参数 设置参数
		if(urlParamEntity.getParamsHashMap()!=null){
			for(Map.Entry<String, String> entity:urlParamEntity.getParamsHashMap().entrySet()){
				//进行设置参数
				System.out.println("BaseProtocol...key:"+entity.getKey()+"..value:"+entity.getValue());
				rpc.addProperty(entity.getKey(),entity.getValue());
			}
		}
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = false;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);
	    HttpTransportSE transport = new HttpTransportSE(endPoint);
		try {
			// 调用WebService
			transport.call(soapAction, envelope);
			if (envelope.bodyIn instanceof SoapFault){
				  //失败信息服务端出现问题
				   final  String str= ((SoapFault) envelope.bodyIn).faultstring;
				   System.out.println("(SoapFault) envelope.bodyIn).faultstring = " + str);
				   throw new ContentException("获取数据失败!");
			}else{
			       final SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
//				   //进行将bodyIn传递的结果字符串--->转换为可以用json进行解析的字符串	    
				   System.out.println("right result:"+String.valueOf(resultsRequestSOAP.toString()));
				   System.out.println("---------------------------------------------------------");
				   //进行解析
				   return parseJson(resultsRequestSOAP);
				}
						
			} catch(SocketTimeoutException e){
				System.out.println("链接超时异常"+e.getMessage());
				throw new ContentException("链接超时,请查看网络");
			}catch (IOException e){
				e.printStackTrace();
				//网络连接超时的异常!
				throw new ContentException("请查看网络");
			} catch (XmlPullParserException e) {
				e.printStackTrace();
				throw new ContentException("获取数据失败!");
			}
	}
}
