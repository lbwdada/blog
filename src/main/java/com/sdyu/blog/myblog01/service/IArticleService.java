package com.sdyu.blog.myblog01.service;

import com.github.pagehelper.PageInfo;
import com.sdyu.blog.myblog01.entity.Article;

import java.util.List;

public interface IArticleService {
    //分页查询文章列表
    PageInfo<Article> getArticles(Integer page,Integer count);
    //获取热度前十的文章
    List<Article> getTopArticle();

    Article selectArticleById(Integer id);

    //发布文章
    void publishArticle(Article article);
    //修改文章
    void updateArticleById(Article article);
    //删除文章
    void deleteArticleById(int id);
}
