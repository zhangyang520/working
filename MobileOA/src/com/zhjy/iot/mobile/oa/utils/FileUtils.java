package com.zhjy.iot.mobile.oa.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 
 * @author zhangyang
 *
 */
public class FileUtils{
	
	public static String readString(InputStream inputStream) {

		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			int len = -1;
			byte[] buffers = new byte[1024];
			baos = new ByteArrayOutputStream();
			while ((len = bis.read(buffers)) != -1) {
				baos.write(buffers, 0, len);
			}
			return baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
