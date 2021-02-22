package com.sdyu.blog.myblog01.mapper;

import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StatisticMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Statistic record);

    int insertSelective(Statistic record);

    Statistic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Statistic record);

    int updateByPrimaryKey(Statistic record);

    //添加：统计文章总访问量
    long getTotalHit();
    //添加：统计文章总评论量
    long getTotalComment();
    //添加：统计文章热度信息（排序规则：先按点击量；再按评论数）
    List<Statistic> getStatistic();

    //更新文章点击量
    int updateArticleHits(Statistic statistic);

    Statistic selectByAid(Integer aid);

    //更新文章评论数
    int updateArticleComments(Statistic statistic);

    //新增文章的统计信息
    int addStatistic(Article article);
    //根据文章id删除统计数据
    int deleteByAid(Integer aid);



}