package com.liyang.blog.service;

import com.liyang.blog.pojo.LoginTicket;
import com.liyang.blog.pojo.User;

import java.util.Map;


public interface UserService {
    //注册
    public Map register(String username, String password);

    //登录
    public Map login(String username, String password);

    //按照用户名查找用户
    public User getUserByName(String username);

    //按照 ID 查找用户
    public User getUserById(Integer id);

    //添加 LoginTicket
    public String addLoginTicket(int userId);

    //更改登录状态
    public void loginout(String userId);

    //根据 Ticket 查找
    public LoginTicket getLoginTicketByTicket(String ticket);
}
