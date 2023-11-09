package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from big_event.user where username = #{username}")
    User findByUserName(String username);


    @Insert("INSERT big_event.user(username, password, create_time, update_time) " +
            "VALUES (#{username},#{password}, now(),now())")
    void add(String username, String password);
}
