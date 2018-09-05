package com.liyang.blog.pojo;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 为了在拦截器获取用户，并在controller使用
 * @author tuzhenyu
 */
@Component
public class HostHolder {
    private static ThreadLocal<HashMap<String,Object>> users = new ThreadLocal<>();

    public HashMap<String, Object> getUser(){
        return users.get();
    }

    public void setUser(HashMap<String,Object> map){
        users.set(map);
    }

    public void clear(){
        users.remove();
    }
}

