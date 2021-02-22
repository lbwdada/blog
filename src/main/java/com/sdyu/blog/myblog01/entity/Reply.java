package com.sdyu.blog.myblog01.entity;

import java.io.Serializable;
import java.util.Date;

/*
* 回复实体类
* */
public class Reply implements Serializable {
    private Integer id;
    private Integer commentId;
    private Date create;
    private String ip;
    private String content;
    private String author;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setcommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
