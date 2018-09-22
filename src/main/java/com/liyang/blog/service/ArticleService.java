package com.liyang.blog.service;

import com.liyang.blog.pojo.Article;

import javax.xml.soap.SAAJResult;
import java.util.List;

public interface ArticleService {
    //add Article，，，，，并且返回articleId
    public int addArticle(Article article);

    //查询所有 article
    public List<Article> findAllArticle();

    // 根据 id 查询article
    public Article getArticleById(int id);

    /**
     * 更新 commentCount
     * @param done  1 为 增加，0 为删除
     * @param articleId
     */
    public void updateCommentCount(int done,int articleId);
}
