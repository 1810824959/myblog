package com.liyang.blog.service.impl;

import com.liyang.blog.mapper.ArticleMapper;
import com.liyang.blog.pojo.Article;
import com.liyang.blog.pojo.ArticleExample;
import com.liyang.blog.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(Article article) {
        articleMapper.insert(article);
    }

    @Override
    public List<Article> findAllArticle() {
        ArticleExample example = new ArticleExample();
        example.createCriteria();
        List<Article> list = articleMapper.selectByExample(example);
        return list;
    }
}
