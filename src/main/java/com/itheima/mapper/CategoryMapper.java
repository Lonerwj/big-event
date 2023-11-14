package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    @Insert("insert into big_event.category(category_name, category_alias, create_user, create_time, update_time) " +
            "VALUES (#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);
}
