package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from big_event.user where username = #{username}")
    User findByUserName(String username);


    @Insert("INSERT big_event.user(username, password, create_time, update_time) " +
            "VALUES (#{username},#{password}, now(),now())")
    void add(String username, String password);


    @Update("update big_event.user set nickname=#{nickname},email=#{email},update_time=#{updateTime}")
    void update(User user);

    @Update("update big_event.user set update_time=now(),user_pic=#{avatarUrl} where id=#{userId}")
    void updateAvatar(String avatarUrl, Integer userId);

    @Update("update big_event.user set password=#{newPwd} where username=#{username}")
    void updatePassword(String newPwd, String username);
}
