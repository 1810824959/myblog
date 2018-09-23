package com.liyang.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyang.blog.pojo.*;
import com.liyang.blog.service.*;
import com.sun.javafx.util.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class articleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;


    private Logging logging = new Logging();

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
                tagService.updateTag(t); //只是增加这个标签的数量

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
                              Model model,
                              @RequestParam(value = "pageNumber",required = false,defaultValue = "1")int pageNumber){

        Article articleById = articleService.getArticleById(id);

        List<String> tagNames = tagService.getTagByArticleId(articleById.getId());
        String readCount = jedisService.getReadCount(String.valueOf(articleById.getId()));
        model.addAttribute("article",articleById);
        model.addAttribute("tagNames",tagNames);
        model.addAttribute("readCount",readCount);

        PageHelper.startPage(pageNumber,5);
        List<Comment> commentsListByArticleId = commentService.getCommentsListByArticleId(id);
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(commentsListByArticleId);
        model.addAttribute("pageInfo",pageInfo);

        List<Item> Comments = new ArrayList<>();
        for(Comment comment:commentsListByArticleId){
            Item item = new Item();
            item.set("comment",comment);
            item.set("username",userService.getUserNameById(comment.getUserId()));
            Comments.add(item);
        }
        model.addAttribute("Comments",Comments);


        User user = (User) hostHolder.getUser().get("user");
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

    @RequestMapping("/addComment/{id}")
    public String addComment(@PathVariable int id,String content){
        User user = (User) hostHolder.getUser().get("user");
        Comment comment = commentService.addComment(content, user.getId(), id);
        Map<String,Object> CommentResult = new HashMap<String, Object>();
        CommentResult.put("userId",comment.getUserId());
        CommentResult.put("articleId",comment.getArticleId());
        CommentResult.put("content",comment.getContent());
        CommentResult.put("username",user.getName());
        CommentResult.put("createDate",comment.getCreatedDate());
        return String.format("redirect:/article/%s",id);
    }

    /**
     * 下面这个是原本准备给ajax的，但是最后还是没用
     */
//    @RequestMapping("/addComment/{id}")
//    @ResponseBody
//    public Map addComment(@PathVariable int id,String content){
//        User user = (User) hostHolder.getUser().get("user");
//        Comment comment = commentService.addComment(content, user.getId(), id);
//        Map<String,Object> CommentResult = new HashMap<String, Object>();
//        CommentResult.put("userId",comment.getUserId());
//        CommentResult.put("articleId",comment.getArticleId());
//        CommentResult.put("content",comment.getContent());
//        CommentResult.put("username",user.getName());
//        CommentResult.put("createDate",comment.getCreatedDate());
//        return CommentResult;
//    }
}
