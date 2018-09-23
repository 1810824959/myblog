package com.liyang.blog.service.impl;

import com.liyang.blog.mapper.CommentMapper;
import com.liyang.blog.pojo.Comment;
import com.liyang.blog.pojo.CommentExample;
import com.liyang.blog.service.ArticleService;
import com.liyang.blog.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class CommentServiceImpl  implements CommentService{
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ArticleService articleService;

    @Override
    public Comment addComment(String content, int userId, int articleId) {
        //插入comment表
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setStatus(1); //1 为生效
        comment.setUserId(userId);
        commentMapper.insert(comment);

        //更新article 表的 commentCount
        articleService.updateCommentCount(1,articleId);
        return comment;
    }

    @Override
    public List<Comment> getCommentsListByArticleId(int articleId) {
        CommentExample example = new CommentExample();
        example.createCriteria().andArticleIdEqualTo(articleId);
        List<Comment> comments = commentMapper.selectByExample(example);
        return comments;
    }


}
