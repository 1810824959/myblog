package com.liyang.blog.mapper;

import com.liyang.blog.pojo.LoginTicket;
import com.liyang.blog.pojo.LoginTicketExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface LoginTicketMapper {
    int countByExample(LoginTicketExample example);

    int deleteByExample(LoginTicketExample example);

    int insert(LoginTicket record);

    int insertSelective(LoginTicket record);

    List<LoginTicket> selectByExample(LoginTicketExample example);

    int updateByExampleSelective(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);

    int updateByExample(@Param("record") LoginTicket record, @Param("example") LoginTicketExample example);
}