package com.mineworld;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mineworld.server.AcsTcpServer;

/**
 * 客户端接口机启动类
 * 
 */
public class MineWorld {
	private static final Logger log = LoggerFactory.getLogger(MineWorld.class);
	
	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		if (args != null) {
			for (String arg : args) {
				// 判断是否需要打印版本信息
				// cmd format: java -jar dls.jar -version
				/*if ("-version".equals(arg)) {
					SystemInitor.getInstance().printSystemSummary(true);
					return;
				}*/
			}
		}
		/*URL base = SmartHome.class.getResource("");
		String path = new File(base.getFile()).getCanonicalPath();*/
		
		
		long t1 = System.currentTimeMillis();
		// 启动系统
		//SystemInitor initor = SystemInitor.getInstance();
		try {
			//initor.init();
			//SysParamUtil.init();
			AcsTcpServer.getInstance().startService("0.0.0.0", 8091);
			long t2 = System.currentTimeMillis();
			log.info("start service ok in " + (t2-t1) + " ms");
			
			
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(0);
		}
	}
}
