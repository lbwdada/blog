package com.sdyu.blog.myblog01.controller.client;

import com.github.pagehelper.PageInfo;
import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.entity.Comment;
import com.sdyu.blog.myblog01.service.IArticleService;
import com.sdyu.blog.myblog01.service.ICommentService;
import com.sdyu.blog.myblog01.service.ISiteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ISiteService siteService;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        return this.index(request,1,5);
    }
    @GetMapping("/page/{p}")
    public String index(HttpServletRequest request, @PathVariable("p") int page, @RequestParam(value = "count",defaultValue = "5") int count){
        PageInfo<Article> articles = articleService.getArticles(page, count);
        List<Article> topArticle = articleService.getTopArticle();
        request.setAttribute("articles",articles);
        request.setAttribute("articleList",topArticle);
        return "client/index";
    }
    @GetMapping("/article/{id}")
    public String   getArticleById(@PathVariable("id") Integer id,HttpServletRequest request){
        //获取点击文章对象
        Article article = articleService.selectArticleById(id);
        //点击文章后修改点击量
        if(article!=null){
            //更新
            siteService.updateStatistics(article);
            //获取文章对应评论内容
            getCommentById(request,article);
            request.setAttribute("article",article);
            return "client/articleDetails";
        }else{
            return "comm/error_404";
        }
    }
    private void getCommentById(HttpServletRequest request,Article article){
        //判断文章是否允许评论
        if(article.getAllowComment()){
            //cp表示评论页码，从页面传来的数据
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp)?"1":cp;
            request.setAttribute("cp",cp);
            PageInfo<Comment> comments = commentService.getComments(article.getId(), Integer.parseInt(cp), 3);
            request.setAttribute("comments",comments);
        }
    }
}
