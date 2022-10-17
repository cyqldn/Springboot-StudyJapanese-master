package com.chun.myspringboot.controller;


import com.chun.myspringboot.pojo.Comment;
import com.chun.myspringboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @RequestMapping("/toComment")
    public String comment() {
        return "comment";
    }

    @RequestMapping("/toVideo")
    public String video(){
        return "video";
    }

    @GetMapping("/comment")
    public String comments(Model model ,HttpSession session) {
        List<Comment> comments = commentService.listComment();

        model.addAttribute("comments", comments);
        return "comment :: commentList";
    }

    @PostMapping("/comment")
    public String post(Comment comment , HttpSession session) {

        //设置头像
        comment.setAvatar(avatar);
        if (comment.getParentComment().getId() != null) {
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        return "redirect:/comment";
    }
}