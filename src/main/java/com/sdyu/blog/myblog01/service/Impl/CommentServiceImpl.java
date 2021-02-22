package com.sdyu.blog.myblog01.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.entity.Reply;
import com.sdyu.blog.myblog01.entity.Statistic;
import com.sdyu.blog.myblog01.mapper.CommentMapper;
import com.sdyu.blog.myblog01.mapper.ReplyMapper;
import com.sdyu.blog.myblog01.mapper.StatisticMapper;
import com.sdyu.blog.myblog01.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private StatisticMapper statisticMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> comments = commentMapper.selectByAid(aid);
        for(Comment c:comments){
            List<Reply> replies = replyMapper.selectByCid(c.getId());
            c.setReplyList(replies);
        }
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return commentPageInfo;
    }

    @Override
    public void pushComment(Comment comment) {
        //增加一条评论
        commentMapper.insert(comment);
        //更新文章统计数据
        Statistic statistic = statisticMapper.selectByAid(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleComments(statistic);
    }

    @Override
    public PageInfo<Comment> getAllComments(int page, int count) {
        PageHelper.startPage(page,count);
        List<Comment> comments = commentMapper.selectComments();
        PageInfo<Comment> commentPageInfo = new PageInfo<>(comments);
        return commentPageInfo;
    }

    @Override
    public void deleteComment(int id,int aid) {
        //
        //删除评论
        commentMapper.deleteByPrimaryKey(id);
        //更新统计信息
        Statistic statistic = statisticMapper.selectByAid(aid);
        statistic.setCommentsNum(statistic.getCommentsNum()-1);
        statisticMapper.updateArticleComments(statistic);
    }

    @Override
    public void reviewComment(int id) {
        //审核评论 更新status为approved
        commentMapper.updateStatus(id);
    }

    @Override
    public void pushReply(Reply reply) {
        //增加一条回复
        replyMapper.insert(reply);
        Comment comment = commentMapper.selectByPrimaryKey(reply.getCommentId());
        //更新文章统计数据
        Statistic statistic = statisticMapper.selectByAid(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()+1);
        statisticMapper.updateArticleComments(statistic);
    }



    /**
     * 回复
     */
    @Override
    public PageInfo<Reply> getAllReplys(int page, int count) {
        PageHelper.startPage(page,count);
        List<Reply> replies = replyMapper.selectAllReply();
        PageInfo<Reply> replyPageInfo = new PageInfo<>(replies);
        return replyPageInfo;
    }

    @Override
    public void deleteReply(int id,int cid) {
        //删除回复
        replyMapper.deleteReply(id);
        //更新统计信息
        Comment comment = commentMapper.selectByPrimaryKey(cid);
        Statistic statistic = statisticMapper.selectByAid(comment.getArticleId());
        statistic.setCommentsNum(statistic.getCommentsNum()-1);
        statisticMapper.updateArticleComments(statistic);
    }

    @Override
    public PageInfo<Reply> getAllReplysByCid(Integer cid,int page, int count) {
        PageHelper.startPage(page, count);
        List<Reply> replies = replyMapper.selectByCid(cid);
        PageInfo<Reply> replyPageInfo = new PageInfo<>(replies);
        return replyPageInfo;
    }
}
