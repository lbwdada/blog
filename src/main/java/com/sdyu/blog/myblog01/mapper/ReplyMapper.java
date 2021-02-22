package com.sdyu.blog.myblog01.mapper;

import com.sdyu.blog.myblog01.entity.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReplyMapper {

//    通过comment_id查询每条评论的回复，用于文章详情页显示评论树和后台查询某评论下的全部评论
    List<Reply> selectByCid(Integer cid);

//    插入回复
    int insert(Reply reply);

//    无条件查询全部的回复并按照id逆序（实现按时间从新到旧显示），用于回复管理页面分页显示
    List<Reply> selectAllReply();

//    删除回复
    int deleteReply(Integer id);
}
