package com.sdyu.blog.myblog01;


import com.sdyu.blog.myblog01.entity.Article;
import com.sdyu.blog.myblog01.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Myblog01ApplicationTests {

    @Autowired
    private ArticleMapper articleMapper;
    @Test
    void contextLoads() {
    }
    @Test
    void selectArticle(){
        Article article = articleMapper.selectByPrimaryKey(1);
        System.out.println(article);
    }

}
