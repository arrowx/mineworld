package com.mineworld.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mineworld.MineWorld;

public class ReadAll {
	private static final Logger log = LoggerFactory.getLogger(ReadAll.class);
	private ReadAll(){
		
	}
	
	public static String readAll(String file,String encoding){

		File f = new File(file);
		byte[] fileBuff = new byte[(int)f.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(f);
			in.read(fileBuff);
			return new String(fileBuff,encoding);
		} catch (Exception e) {
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
	}

	
	public static byte[] readAllByte(String file){

		File f = new File(file);
		byte[] fileBuff = new byte[(int)f.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(f);
			in.read(fileBuff);
			return fileBuff;
		} catch (Exception e) {
			return null;
		}finally{
			try {
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
			}
		}
	}
}
