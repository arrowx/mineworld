package com.mineworld.dao.map;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.mineworld.dao.obj.User;


public interface UserMapper {
	@Select("SELECT * FROM merchant_appuser WHERE username = #{param1} limit 1")
	public List<User> select(String user);
	
	@Insert("insert into merchant_appuser(username,auth_value,password,createtime) values(#{username},1,#{password},#{createtime})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public boolean insert(User user);
	
}
