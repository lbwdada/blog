package com.sdyu.blog.myblog01.controller.admin;

import com.github.pagehelper.PageInfo;
import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.entity.Reply;
import com.sdyu.blog.myblog01.entity.responsedata.ResponseData;
import com.sdyu.blog.myblog01.service.IArticleService;
import com.sdyu.blog.myblog01.service.ICommentService;
import com.sdyu.blog.myblog01.service.ISiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICommentService commentService;
    @GetMapping(value = {"","/index"})
    public String articleIndex(HttpServletRequest request){
        request.setAttribute("articles",siteService.recentArticle(5));
        request.setAttribute("comments",siteService.recentComment(5));
        request.setAttribute("statistics",siteService.getStatistics());
        return "back/index";
    }
    //跳转到文章发布页
    @GetMapping("/article/toEditPage")
    public String newArticle(){
        return "back/article_edit";
    }
    //发布文章
    @PostMapping("/article/publish")
    @ResponseBody
    public ResponseData publishArticle(Article article){
        if(StringUtils.isBlank(article.getCategories())){
            article.setCategories("默认分类");
        }
        try{
            articleService.publishArticle(article);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //跳转到后台文章l列表页面
    @GetMapping("/article")
    public String index(@RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "count",defaultValue = "10") int count,
                        HttpServletRequest request){
        PageInfo<Article> articles = articleService.getArticles(page, count);
        request.setAttribute("articles",articles);
        return "back/article_list";
    }

    //跳转到文章修改页面
    @GetMapping("/article/{id}")
    public String editArticle(@PathVariable("id") Integer id,HttpServletRequest request){
        Article article = articleService.selectArticleById(id);
        request.setAttribute("contents",article);
        request.setAttribute("categories",article.getCategories());
        return "back/article_edit";
    }
    //修改文章
    @PostMapping("/article/modify")
    @ResponseBody
    public ResponseData modifyArticle(Article article){
        try {
            articleService.updateArticleById(article);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //删除文章
    @PostMapping("/article/delete")
    @ResponseBody
    public ResponseData deleteArticle(@RequestParam int id){
        try{
            articleService.deleteArticleById(id);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }

    /**
    **评论
    **/
    //跳转到后台评论列表页面
    @GetMapping("/comment")
    public String commentIndex(@RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "count",defaultValue = "10") int count,
                        HttpServletRequest request){
        PageInfo<Comment> allComments = commentService.getAllComments(page, count);
        request.setAttribute("comments",allComments);
        return "back/comment_list";
    }
    //删除评论
    @PostMapping("/comment/delete")
    @ResponseBody
    public ResponseData deleteComment(@RequestParam(value = "id") int id,@RequestParam(value = "aid") int aid){
        try{
            commentService.deleteComment(id,aid);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //审核评论
    @PostMapping("/comment/review")
    @ResponseBody
    public ResponseData reviewComment(@RequestParam(value = "id") int id){
        try{
            commentService.reviewComment(id);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    /**
    **回复
    **/
    //跳转到后台回复列表页面
    @GetMapping("/reply")
    public String replyIndex(@RequestParam(value = "page",defaultValue = "1") int page,
                               @RequestParam(value = "count",defaultValue = "10") int count,
                               HttpServletRequest request){
        PageInfo<Reply> allReplies = commentService.getAllReplys(page, count);
        request.setAttribute("replies",allReplies);
        return "back/reply_list";
    }
    //删除回复
    @PostMapping("/reply/delete")
    @ResponseBody
    public ResponseData deleteReply(@RequestParam(value = "id") int id, @RequestParam(value = "cid") int cid){
        try{
            commentService.deleteReply(id,cid);
            return ResponseData.ok();
        }catch (Exception e){
            return ResponseData.fail();
        }
    }
    //查询某评论下的全部回复
    @GetMapping("/reply/{cid}")
    public String getAllReplyByCid(@PathVariable("cid") Integer cid,
                                   @RequestParam(value = "page",defaultValue = "1") int page,
                                   @RequestParam(value = "count",defaultValue = "10") int count,
                                   HttpServletRequest request){
        PageInfo<Reply> allReplysByCid = commentService.getAllReplysByCid(cid, page, count);
        request.setAttribute("replies",allReplysByCid);
        return "back/reply_list";
    }
}
