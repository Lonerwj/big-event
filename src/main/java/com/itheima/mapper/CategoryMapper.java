package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Insert("insert into big_event.category(category_name, category_alias, create_user, create_time, update_time) " +
            "VALUES (#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);

    @Select("select * from big_event.category where create_user=#{userId}")
    List<Category> list(Integer userId);

    @Select("select * from big_event.category where id = #{id}")
    Category getById(Integer id);

    @Update("update big_event.category set category_name=#{categoryName},category_alias=#{categoryAlias}," +
            "update_time=now() where id = #{id}")
    void update(Category category);

    @Delete("delete from big_event.category where id=#{id}")
    void delete(Integer id);
}
