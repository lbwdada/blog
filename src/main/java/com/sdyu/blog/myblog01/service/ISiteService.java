package com.sdyu.blog.myblog01.service;

import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.entity.responsedata.StaticticsBo;

import java.util.List;

public interface ISiteService {
    //更新某个文章的统计数据
    void updateStatistics(Article article);
    //获取最新文章
    List<Article> recentArticle(int limit);
    //获取最新评论
    List<Comment> recentComment(int limit);
    //获取统计信息的总数
    StaticticsBo getStatistics();
}
