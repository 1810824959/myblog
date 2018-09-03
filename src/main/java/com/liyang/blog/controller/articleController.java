package com.liyang.blog.controller;

import com.liyang.blog.pojo.*;
import com.liyang.blog.service.ArticleService;
import com.liyang.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class articleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping("/addArticle")
    public String addArticle(@RequestParam("title")String title, @RequestParam("category")String category,
                             @RequestParam("tag")String tag, @RequestParam("describe")String describe,
                             @RequestParam("content")String content){
        //插入 article
        Article article = new Article();
        Date date = new Date();
        article.setTitle(title);
        article.setDescribes(describe);
        article.setContent(content);
        article.setCategory(category);
        article.setCreatedDate(date);
        article.setCommentCount(0);
        //add
        int articleId = articleService.addArticle(article);

        String[] tags = tag.split(","); //这里用的是 英文的逗号切割，中文的不行
        for (String t : tags){ //t ,每一个标签的name
            Tag tag1 = tagService.getTagByName(t);
            if (tag1==null){//没有这个标签
                Tag tag2 = new Tag();
                tag2.setName(t);
                tag2.setCount(1);
                int tagId = tagService.addTag(tag2);

                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tagId);
                articleTag.setArticleId(articleId);
                tagService.addArticleTag(articleTag);
            }else {
                tagService.updateTag(t);

                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag1.getId());
                articleTag.setArticleId(articleId);
                tagService.addArticleTag(articleTag);
            }
        }
        return "redirect:/index";
    }



    @RequestMapping("/article/{id}")
    public String showArticle(@PathVariable("id") int id,
                              Model model){
        Article articleById = articleService.getArticleById(id);

        List<String> tagNames = tagService.getTagByArticleId(articleById.getId());
        model.addAttribute("article",articleById);
        model.addAttribute("tagNames",tagNames);

        User user = hostHolder.getUser();
        if (user!=null){
            if("admin".equals(user.getRole())){//user非空，且是admin用户
                model.addAttribute("create",1);
            }else {
                //已登录，不过是普通用户
                model.addAttribute("create",0);
            }

        }else {//user==null,那就是没有登录注册过
            model.addAttribute("create",0);
        }
        return "article";
    }
}
