package com.chun.myspringboot.service;



import com.chun.myspringboot.pojo.Comment;
import com.chun.myspringboot.pojo.Word;

import java.util.List;

public interface CommentService {

    //查询评论列表
    List<Comment> listComment();

    //保存评论
    int saveComment(Comment comment);

    int deleteComment(Integer Id);

    //查询所有单词信息
    List<Comment> queryAllComment();

}