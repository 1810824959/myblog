package com.liyang.blog.service;

import com.liyang.blog.pojo.Article;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ArticleService {
    //add Article
    public void addArticle(Article article);

    //查询所有 article
    public List<Article> findAllArticle();
}
