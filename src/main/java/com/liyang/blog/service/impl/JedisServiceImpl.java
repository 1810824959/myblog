package com.liyang.blog.service.impl;

import com.liyang.blog.service.JedisService;
import com.liyang.blog.util.jedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

@Service
public class JedisServiceImpl implements JedisService {
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
}
