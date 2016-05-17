package com.zhjy.iot.mobile.oa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.zhjy.iot.mobile.oa.application.MobileOaApplication;

/**
 * 文件压缩工具类
 * @author lvxile
 *
 */
public class ZIPUtil {

	private static final String TAG = "HR-ZIPUtil";
	/**
	 * 将多个文件压缩到一个zip格式中并输出文件
	 * @param zipFilePath
	 * @param files
	 * @return
	 * @throws IOException 
	 */
	public static synchronized File compress(File zipFile,List<File> files) throws IOException{
		ZipOutputStream zos = null;
		try {
			FileOutputStream fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);
			zos.setComment(MobileOaApplication.FILE_PATH+DateUtil.dateFormat()+";");
			for(File f:files){
				if(!f.exists())
					continue;
				if(f.isDirectory()){
					zipDirectory(zos,f);
				}else{
					zipFile(zos,f);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(zos != null){
				zos.close();
			}
		}
		return zipFile;
	}
	
	/**
	 * 目录压缩
	 * @param zos 
	 * @param f
	 * @throws IOException 
	 */
	private static void zipDirectory(ZipOutputStream zos, File f) throws IOException {
		File[] files = f.listFiles();
		if(files.length>0){
			for(File fl:files){
				if(fl.isDirectory()){
					zipDirectory(zos, fl);
				}else{
					zipFile(zos, fl);
				}
			}
		}else{
			ZipEntry ze = new ZipEntry(f.getPath());
			zos.putNextEntry(ze);
		}
	}
	/**
	 * 单体文件压缩
	 * @param zos 
	 * @param f
	 * @throws IOException 
	 */
	private static void zipFile(ZipOutputStream zos, File f) throws IOException{
		FileInputStream is = new FileInputStream(f);
		try {
			ZipEntry ze = new ZipEntry(f.getName());
			zos.putNextEntry(ze);
			byte[] b = new byte[1024];
			int count =0;
			while((count = is.read(b))>0){
				zos.write(b,0,count);
			}
		} catch (IOException e) {
			LogUtils.e(TAG, "压缩文件["+f.getPath()+"]出错,"+e.getMessage());
			throw e;
		}finally{
			is.close();
		}
		
	}
	
	public static List<File> unCompressZIP(FileInputStream zipIn,String destPath) throws IOException{
		List<File> list = new ArrayList<File>();
		ZipInputStream zs = new ZipInputStream(zipIn);
		ZipEntry ze = null;
		try {
			byte[] b = new byte[1024];
			while ((ze = zs.getNextEntry())!=null) {
				String name = ze.getName();
				File zf = new File(destPath+File.separator+name);
				File parent = new File(zf.getParentFile().getPath());
				if(ze.isDirectory()){
					if(!zf.exists()){
						zf.mkdirs();
					}
					zs.closeEntry();
				}else{
					if(!parent.exists()){
						parent.mkdirs();
					}
					FileOutputStream fos = new FileOutputStream(zf);
					int x;
					while((x=zs.read(b))>0){
						fos.write(b, 0, x);
					}
					fos.close();
					zs.closeEntry();
					list.add(zf);
				}
			}
			zs.close();
			zipIn.close();
		} catch (IOException e) {
			
			throw e;
		}
		return list;
	}
	
}
