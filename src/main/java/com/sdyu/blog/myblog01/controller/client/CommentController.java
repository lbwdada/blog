package com.sdyu.blog.myblog01.controller.client;

import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.entity.Reply;
import com.sdyu.blog.myblog01.entity.responsedata.ResponseData;
import com.sdyu.blog.myblog01.service.ICommentService;
import com.sdyu.blog.myblog01.utils.MyUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @PostMapping("/publish")
    @ResponseBody
    public ResponseData pushComment(HttpServletRequest request, @RequestParam Integer aid, @RequestParam String text){
        //防XSS
        text = MyUtils.cleanXSS(text);
        text = EmojiParser.parseToAliases(text);
        //获取当前登录用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //封装评论
        Comment comment = new Comment();
        comment.setArticleId(aid);
        comment.setAuthor(user.getUsername());
        comment.setContent(text);
        comment.setCreated(new Date());
        comment.setStatus("need approve");
        comment.setIp(request.getRemoteAddr());
        //发布评论，返回响应数据
        try {
            commentService.pushComment(comment);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    @PostMapping("/pushReply")
    @ResponseBody
    public ResponseData pushReply(HttpServletRequest request, @RequestParam Integer cid,@RequestParam String replyText){
        //防XSS
        replyText = MyUtils.cleanXSS(replyText);
        replyText = EmojiParser.parseToAliases(replyText);
        //获取当前登录用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reply reply = new Reply();
        reply.setcommentId(cid);
        reply.setAuthor(user.getUsername());
        reply.setContent(replyText);
        reply.setCreate(new Date());
        reply.setIp(request.getRemoteAddr());
        try{
            commentService.pushReply(reply);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
}
