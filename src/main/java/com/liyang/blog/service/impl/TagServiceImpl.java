package com.liyang.blog.service.impl;

import com.liyang.blog.mapper.ArticleTagMapper;
import com.liyang.blog.mapper.TagMapper;
import com.liyang.blog.pojo.ArticleTag;
import com.liyang.blog.pojo.ArticleTagExample;
import com.liyang.blog.pojo.Tag;
import com.liyang.blog.pojo.TagExample;
import com.liyang.blog.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public int addTag(Tag tag) {
        tagMapper.insert(tag);
        return tag.getId();
    }

    @Override
    public void updateTag(String tag) {
        TagExample example = new TagExample();
        example.createCriteria().andNameEqualTo(tag);
        List<Tag> tags = tagMapper.selectByExample(example);
        if (tags.size()>0){
            Integer count = tags.get(0).getCount();
            tags.get(0).setCount(count+1);
            tagMapper.updateByExample(tags.get(0),example);
        }

    }

    @Override
    public void addArticleTag(ArticleTag articleTag) {
        articleTagMapper.insert(articleTag);
    }

    @Override
    public Tag getTagByName(String name) {
        TagExample example = new TagExample();
        example.createCriteria().andNameEqualTo(name);
        List<Tag> tags = tagMapper.selectByExample(example);
        if (tags.size()>0){
            return tags.get(0);
        }
        return null;
    }

    @Override
    public List<String> getTagByArticleId(int articleId) {
        ArticleTagExample example = new ArticleTagExample();
        example.createCriteria().andArticleIdEqualTo(articleId);
        List<ArticleTag> articleTags = articleTagMapper.selectByExample(example);

        ArrayList<String> tagName = new ArrayList<>();
        for(ArticleTag articleTag:articleTags){
            Tag tag = tagMapper.selectByPrimaryKey(articleTag.getTagId());
            tagName.add(tag.getName());
        }
        return tagName;
    }
}
