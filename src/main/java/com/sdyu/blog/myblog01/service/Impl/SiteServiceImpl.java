package com.sdyu.blog.myblog01.service.Impl;

import com.github.pagehelper.PageHelper;
import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.entity.Statistic;
import com.sdyu.blog.myblog01.entity.responsedata.StaticticsBo;
import com.sdyu.blog.myblog01.mapper.ArticleMapper;
import com.sdyu.blog.myblog01.mapper.CommentMapper;
import com.sdyu.blog.myblog01.mapper.StatisticMapper;
import com.sdyu.blog.myblog01.service.ISiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements ISiteService {
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public void updateStatistics(Article article) {
        Statistic statistic = statisticMapper.selectByAid(article.getId());
        statistic.setHits(statistic.getHits()+1);
        statisticMapper.updateArticleHits(statistic);
    }

    @Override
    public List<Article> recentArticle(int limit) {
        PageHelper.startPage(1,limit>10||limit<1?10:limit);
        List<Article> articles = articleMapper.selectArticles();
        for(Article article:articles){
            Statistic statistic = statisticMapper.selectByAid(article.getId());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        return articles;
    }

    @Override
    public List<Comment> recentComment(int limit) {
        PageHelper.startPage(1,limit>10||limit<1?10:limit);
        List<Comment> comments = commentMapper.selectComments();
        return comments;
    }

    @Override
    public StaticticsBo getStatistics() {
        StaticticsBo staticticsBo = new StaticticsBo();
        staticticsBo.setArticles(articleMapper.countArticle());
        staticticsBo.setComments(commentMapper.countComment());
        return staticticsBo;
    }
}
