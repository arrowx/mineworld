package com.mineworld.dao;

import java.io.ByteArrayInputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mineworld.com.ReadAll;
import com.mineworld.dao.map.UserMapper;

public class SessionFactory {
	
	private SessionFactory(){
		
	}
	
	private static SqlSessionFactory sqlSessionFactory;

	public static SqlSessionFactory getInstance() {
		if (sqlSessionFactory != null){
			return sqlSessionFactory;
		}
		String fileContent = ReadAll.readAll("config/mybatis-config.xml", "utf-8");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(new ByteArrayInputStream(fileContent.getBytes()));
		sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		sqlSessionFactory.getConfiguration().setDefaultStatementTimeout(10);
		return sqlSessionFactory;
	}
	
}
