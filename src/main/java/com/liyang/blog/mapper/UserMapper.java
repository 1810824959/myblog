package com.liyang.blog.mapper;

import com.liyang.blog.pojo.User;
import com.liyang.blog.pojo.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);
}