package com.liyang.blog.service.impl;

import com.liyang.blog.mapper.LoginTicketMapper;
import com.liyang.blog.mapper.UserMapper;
import com.liyang.blog.pojo.LoginTicket;
import com.liyang.blog.pojo.LoginTicketExample;
import com.liyang.blog.pojo.User;
import com.liyang.blog.pojo.UserExample;
import com.liyang.blog.service.UserService;
import com.liyang.blog.util.JblogUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;


@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;
    @Resource
    private LoginTicketMapper loginTicketMapper;

    //根据name 查询用户
    @Override
    public User getUserByName(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username);

        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size()>0){
            return users.get(0);
        }

        return null;
    }

    // 根据ID
    @Override
    public User getUserById(Integer id) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size()>0){
            return users.get(0);
        }

        return null;
    }


    //register注册
    @Override
    @Transactional
    public Map register(String username, String password) {
        Map<Object, Object> map = new HashMap<>();
        Random random = new Random();
        //先查看传的值是否为空
        if (StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        //查询是否占用
        User userByName = getUserByName(username);
        if (userByName!=null){
            map.put("msg","用户名已经被占用");
            return map;
        }

        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dm.png",random.nextInt(1000)));
        user.setPassword(JblogUtil.MD5(password+user.getSalt()));
        user.setRole("user");
        userMapper.insert(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;


    }

    //login登录
    @Override
    public Map login(String username, String password) {
        Map<Object, Object> map = new HashMap<>();
        Random random = new Random();
        //先查看传的值是否为空
        if (StringUtils.isEmpty(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        //查询是否存在该用户
        User userByName = getUserByName(username);
        if (userByName==null){
            map.put("msg","用户名不存在");
            return map;
        }

        if (!(userByName.getPassword().equals(JblogUtil.MD5(password+userByName.getSalt())))){
            map.put("msg","密码错误");
            return map;
        }

        String ticket = addLoginTicket(userByName.getId());
        map.put("ticket",ticket);
        return map;
    }

    @Override
    @Transactional
    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*30);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));

        loginTicketMapper.insert(loginTicket);

        return loginTicket.getTicket();
    }

    @Override
    @Transactional
    public void loginout(String ticket) {
        LoginTicketExample example = new LoginTicketExample();
        example.createCriteria().andTicketEqualTo(ticket);

        LoginTicket newTicket = new LoginTicket();
        newTicket.setStatus(1);
        loginTicketMapper.updateByExampleSelective(newTicket,example);
    }

    @Override
    public LoginTicket getLoginTicketByTicket(String ticket) {
        LoginTicketExample example = new LoginTicketExample();
        example.createCriteria().andTicketEqualTo(ticket);
        List<LoginTicket> loginTicketList = loginTicketMapper.selectByExample(example);
        LoginTicket Ticket = loginTicketList.get(0);
        return Ticket;
    }

}
