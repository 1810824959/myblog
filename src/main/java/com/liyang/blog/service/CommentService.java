package com.liyang.blog.service;

import com.liyang.blog.pojo.Comment;

import java.util.List;

public interface CommentService {
    //add comment
    public Comment addComment(String content,int userId,int articleId);

    //根据 articleId 查询 comment
    public List<Comment> getCommentsListByArticleId(int articleId);
}
