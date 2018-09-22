package com.liyang.blog.service.impl;

import com.liyang.blog.mapper.ArticleMapper;
import com.liyang.blog.pojo.Article;
import com.liyang.blog.pojo.ArticleExample;
import com.liyang.blog.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public int addArticle(Article article) {
        articleMapper.insert(article);
        return article.getId();

    }

    @Override
    public List<Article> findAllArticle() {
        ArticleExample example = new ArticleExample();
        example.createCriteria();
        List<Article> list = articleMapper.selectByExample(example);
        return list;
    }

    @Override
    public Article getArticleById(int id) {
//        ArticleExample example = new ArticleExample();
//        example.createCriteria().andIdEqualTo(id);
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public void updateCommentCount(int done, int articleId) {
        ArticleExample example = new ArticleExample();
        example.createCriteria().andIdEqualTo(articleId);
        List<Article> articles = articleMapper.selectByExample(example);
        if (articles!=null){
            Integer oldCount = articles.get(0).getCommentCount();
            if (done==1){
                articles.get(0).setCommentCount(oldCount+1);
            }
            if (done==0){
                articles.get(0).setCommentCount(oldCount-1);
            }
            articleMapper.updateByExample(articles.get(0),example);
        }
    }
}
