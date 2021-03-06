package com.liyang.blog.service;

import com.liyang.blog.pojo.ArticleTag;
import com.liyang.blog.pojo.Tag;

import java.util.List;

public interface TagService {
    //插入 Tag 到 Tag表
    public int addTag(Tag tag);

    //update Tag表
    public void updateTag(String tag);

    //插入 关系 到 atricle-tag表
    public void addArticleTag(ArticleTag articleTag);

    //查询是否有这个 tag 在表中
    public Tag getTagByName(String name);

    //在 article_tag 表中根据article_id 查询 tagID，，最终返回该article对应的tagname List
    public List<String> getTagByArticleId(int articleId);

    // get 全部标签
    public List<Tag> getAllTag();
}
