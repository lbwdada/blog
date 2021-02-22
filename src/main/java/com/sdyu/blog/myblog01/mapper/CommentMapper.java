package com.sdyu.blog.myblog01.mapper;

import com.sdyu.blog.myblog01.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    //删除
    int deleteByPrimaryKey(Integer id);
    //插入
    int insert(Comment record);
    //选择性插入
    int insertSelective(Comment record);
    //根据id查询
    Comment selectByPrimaryKey(Integer id);
    //更新
    int updateByPrimaryKeySelective(Comment record);
    //更新
    int updateByPrimaryKeyWithBLOBs(Comment record);
    //更新
    int updateByPrimaryKey(Comment record);

    //查询审核完成的文章评论
    List<Comment> selectByAid(Integer aid);
    //获取所有评论按id逆序排序
    List<Comment> selectComments();
    //获取总评论数
    Integer countComment();
    //删除文章下评论
    int deleteByAid(Integer aid);
    //审核评论
    int updateStatus(Integer id);
}