package com.chun.myspringboot.controller.admin;


import com.chun.myspringboot.pojo.Comment;
import com.chun.myspringboot.pojo.Word;
import com.chun.myspringboot.service.Impl.CommentServiceImpl;
import com.chun.myspringboot.service.Impl.WordServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UpdateCommentController {
    @Autowired
    CommentServiceImpl commentService;

    @RequestMapping("/admin/viewComment")
    public String viewWord(Model model ,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {
        PageHelper.startPage(pageNum,6);
        List<Comment> comments = commentService.queryAllComment();
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(comments);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("commentse",comments);

        return "admin/Comment-View";
    }
    /**
     *
     *  删除单词
     */

    @RequestMapping("/admin/deleteComment/{Id}")
    public String deleteNotice(@PathVariable("Id") Integer Id){

        commentService.deleteComment(Id);


        return "redirect:/admin/viewComment";
    }

}
