package com.liyang.blog.service;

import com.liyang.blog.pojo.Article;

import java.util.List;

public interface JedisService {
    //得到阅读量
    public String getReadCount(String id);

    // 增加阅读量，，排行榜增加，本身也增加
    public void addCount(String uri);

    //get 排行榜
    public List<Article> getRank();
}
