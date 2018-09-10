package com.liyang.blog.util;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class test {
//	@Test
//	public void test(){
//		//1.创建redis对象
//		Jedis jedis = new Jedis("47.106.71.188",6379);
//		//插入
//		jedis.set("name", "liyang");
//		System.out.println(jedis.get("name"));
//		//jedis关闭
//		jedis.close();
//	}
//	
//	@Test
//	public void testJedisPool(){
//		//1.创建redis
//		JedisPool pool = new JedisPool("47.106.71.188",6379);
//		//2.从pool中得到一个redis对象
//		Jedis jedis = pool.getResource();
//		//3.���в���
//		jedis.set("name", "liyang");
//		System.out.println(jedis.get("name"));
//		//4.jedis对象关闭
//		jedis.close();
//		
//		//5.连接池关闭
//		pool.close();
//	}
//	
//	


//	@Test
//	public void testJedisByUtils(){
//		Jedis jedis = jedisUtils.getJedis();
////		Long hotArticles = jedis.z
//		System.out.println(String.valueOf(hotArticles));
//		jedis.close();
//
//	}
}
