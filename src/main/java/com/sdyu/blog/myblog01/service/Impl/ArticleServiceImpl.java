package com.sdyu.blog.myblog01.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.entity.Statistic;
import com.sdyu.blog.myblog01.mapper.ArticleMapper;
import com.sdyu.blog.myblog01.mapper.CommentMapper;
import com.sdyu.blog.myblog01.mapper.StatisticMapper;
import com.sdyu.blog.myblog01.service.IArticleService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<Article> getArticles(Integer page, Integer count) {
        PageHelper.startPage(page,count);
        List<Article> articleList = articleMapper.selectArticles();
        for (Article article:articleList) {
            Statistic statistic = statisticMapper.selectByPrimaryKey(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
        PageInfo<Article> articlePageInfo = new PageInfo<>(articleList);
        return articlePageInfo;
    }

    @Override
    public List<Article> getTopArticle() {
        List<Statistic> statisticList = statisticMapper.getStatistic();
        List<Article> articles = new ArrayList<>();
        for (Statistic statistic:statisticList){
            Article article = articleMapper.selectByPrimaryKey(statistic.getArticleId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
            articles.add(article);
        }
        return articles;
    }

    @Override
    public Article selectArticleById(Integer id) {
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_" + id);
        if(o!=null){
            article =(Article)o;
        }else{
            article = articleMapper.selectByPrimaryKey(id);
            redisTemplate.opsForValue().set("article_"+id,article);
        }
        return article;
    }

    @Override
    public void publishArticle(Article article) {
        //去除文章中的表情
        article.setContent(EmojiParser.parseToAliases(article.getContent()));
        article.setCreated(new Date());
        article.setHits(0);
        article.setCommentsNum(0);
        articleMapper.insert(article);
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleById(Article article) {
        article.setModified(new Date());
        articleMapper.updateByPrimaryKeySelective(article);
        redisTemplate.delete("article_"+article.getId());
    }

    @Override
    public void deleteArticleById(int id) {
        //1.删除文章
        articleMapper.deleteByPrimaryKey(id);
        //2.删除缓存
        redisTemplate.delete("article_"+id);
        //3.删除统计数据
        statisticMapper.deleteByAid(id);
        //4.删除评论
        commentMapper.deleteByAid(id);

    }
}
