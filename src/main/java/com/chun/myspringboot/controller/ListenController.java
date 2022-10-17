package com.chun.myspringboot.controller;

import com.chun.myspringboot.pojo.Listen;
import com.chun.myspringboot.service.Impl.ExamQuestionsServiceImpl;
import com.chun.myspringboot.service.Impl.ListenServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ListenController {
    @Autowired
    ListenServiceImpl listenService;
    @Autowired
    ExamQuestionsServiceImpl examQuestionsService;
    //查看所有书籍
    @RequestMapping("/viewListen")
    public String ViewListen(Model model){
        List<Listen> listen = listenService.queryAllListen();
        model.addAttribute("listen",listen);
        return "user/listen/select-listen";
    }
    //具体进入某一书籍
    @RequestMapping("/watchListen/{listenId}")
    public String watchListen(@PathVariable("listenId")Integer listenId, Model model ,HttpSession session){
        Listen listen = listenService.queryListenById(listenId);
       //存储选择的考试课程id号
        session.setAttribute("taotiid",listenId);
        //返回单选题目
        List<Map<String,Object>> questionsList  = examQuestionsService.findExamRadioQuestions(listenId);
        model.addAttribute("radioQuestionsList", questionsList);
        model.addAttribute("listen",listen);
        return "user/listen/watch-listen";
    }



}
