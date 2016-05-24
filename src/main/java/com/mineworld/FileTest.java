package com.mineworld;

import java.io.File;

public class FileTest {

	public static void main(String[] args) {
		System.out.println("Thread.currentThread().getContextClassLoader().getResource : " + Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println("FileTest.class.getClassLoader().getResource : " + FileTest.class.getClassLoader().getResource(""));
        System.out.println("ClassLoader.getSystemResource : " + ClassLoader.getSystemResource(""));
        System.out.println("FileTest.class.getResource : " + FileTest.class.getResource(""));
        System.out.println("FileTest.class.getResource : " + FileTest.class.getResource("/")); // Class文件所在路径
        System.out.println("new File(\"/\").getAbsolutePath : " + new File("/").getAbsolutePath());
        System.out.println("System.getProperty(\"user.dir\") : " + System.getProperty("user.dir"));
        System.out.println("class.getResourceAsStream(\"\") : " + FileTest.class.getClassLoader().getResourceAsStream("MineWorld.java"));
	}

}
