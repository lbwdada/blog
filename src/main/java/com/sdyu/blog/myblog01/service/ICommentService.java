package com.sdyu.blog.myblog01.service;

import com.github.pagehelper.PageInfo;
import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.entity.Reply;

import java.util.List;

public interface ICommentService {
    //分页查询某篇文章下的评论，用于文章详情页展示
    PageInfo<Comment> getComments(Integer aid,int page,int count);
    //用户发表评论
    void  pushComment(Comment  comment);
    //对所有评论分页查询，用于评论管理页面
    PageInfo<Comment> getAllComments(int page,int count);
//   删除评论
    void deleteComment(int id,int aid);
    //审核评论
    void reviewComment(int id);
//    添加回复
    void pushReply(Reply reply);
    //对所有回复分页查询，用于后台回复管理
    PageInfo<Reply> getAllReplys(int page,int count);
    //删除回复id用来确定回复，cid即commentId用来更新统计信息
    void deleteReply(int id,int cid);
    //通过commentId分页查询某一评论的所有回复
    PageInfo<Reply> getAllReplysByCid(Integer cid,int page,int count);
}
