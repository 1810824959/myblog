package com.liyang.blog.service;

public interface JedisService {
    //得到阅读量
    public String getReadCount(String id);

    // 增加阅读量，，排行榜增加，本身也增加
    public void addCount(String uri);
}
