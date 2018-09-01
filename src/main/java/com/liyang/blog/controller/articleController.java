package com.liyang.blog.controller;

import com.liyang.blog.pojo.Article;
import com.liyang.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class articleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/addArticle")
    public String addArticle(@RequestParam("title")String title, @RequestParam("category")String category,
                             @RequestParam("tag")String tag, @RequestParam("describe")String describe,
                             @RequestParam("content")String content){
        Article article = new Article();
        Date date = new Date();
        article.setTitle(title);
        article.setDescribes(describe);
        article.setContent(content);
        article.setCategory(category);
        article.setCreateddate(date);
        article.setCommentcount(0);
        //add
        articleService.addArticle(article);
        return "redirect:/index";
    }
}
