package com.liyang.blog.interceptor;



import com.liyang.blog.pojo.HostHolder;
import com.liyang.blog.pojo.LoginTicket;
import com.liyang.blog.pojo.User;
import com.liyang.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tuzhenyu on 17-7-20.
 * @author tuzhenyu
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;


    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies()!=null){
            for (Cookie cookie : httpServletRequest.getCookies()){
                if ("ticket".equals(cookie.getName())){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket!=null){
            LoginTicket loginTicket = userService.getLoginTicketByTicket(ticket);
            if (loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0){
                return true;     //if 里头表示登录过期，或者失效
            }
            User user = userService.getUserById(loginTicket.getUserId());
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("ticket",ticket);
            map.put("user",user);
            hostHolder.setUser(map);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView!=null){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
