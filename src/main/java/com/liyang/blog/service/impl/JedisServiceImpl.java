package com.liyang.blog.service.impl;

import com.liyang.blog.pojo.Article;
import com.liyang.blog.service.ArticleService;
import com.liyang.blog.service.JedisService;
import com.liyang.blog.util.jedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class JedisServiceImpl implements JedisService {

    @Autowired
    private ArticleService articleService;

    @Override
    public String getReadCount(String id) {
        Jedis jedis = jedisUtils.getJedis();
        String count = jedis.hget("articles",id);
        jedis.close();
        return count;
    }

    @Override
    @Transactional
    public void addCount(String uri) {
        Jedis jedis = jedisUtils.getJedis();
        String count = jedis.hget("articles",uri);

        //为阅读量本身设置字段
        if (count == null){
            jedis.hset("articles",uri,"1");
        }else {
            jedis.hincrBy("articles",uri,1);
        }
        jedis.close();

        Jedis jedis_1 = jedisUtils.getJedis();
        //排行榜
        jedis_1.zincrby("hotArticles",1,uri);

        jedis_1.close();
    }

    @Override
    public List<Article> getRank() {
        Jedis jedis = jedisUtils.getJedis();
        Set<String> hotArticles = jedis.zrevrange("hotArticles", 0, 6);
        //返回的 list
        List<Article> result = new ArrayList<Article>();
        for(String articleId:hotArticles){
            Article articleById = articleService.getArticleById(Integer.parseInt(articleId));
            result.add(articleById);
        }
        return result;
    }
}
