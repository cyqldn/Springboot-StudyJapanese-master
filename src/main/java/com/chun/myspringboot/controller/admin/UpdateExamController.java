package com.chun.myspringboot.controller.admin;

import com.chun.myspringboot.pojo.Questions;
import com.chun.myspringboot.pojo.Word;
import com.chun.myspringboot.service.Impl.ExamQuestionsServiceImpl;
import com.chun.myspringboot.service.Impl.NoticeServiceImpl;
import com.chun.myspringboot.util.DataUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;

@Controller
public class UpdateExamController {
    @Autowired
    NoticeServiceImpl noticeService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    ExamQuestionsServiceImpl examQuestionsService;

    /**
     *
     * 查看
     */
    @RequestMapping("/admin/viewQuestions")
    public String viewQuestions(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum, 6);
        List<Questions> questions= examQuestionsService.queryAllQuestions();
        PageInfo<Questions> pageInfo = new PageInfo<Questions>(questions);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("questions",questions);
        return "admin/Questions-View";
    }


    /**
     *
     *  修改试题
     */
    //去修改页面
    @RequestMapping("/admin/toUpdateQuestions/{Id}")
    public String toUpdateQuestion(@PathVariable("Id")Integer Id,Model model){

        Questions questions = examQuestionsService.queryQuestionsById(Id);
        model.addAttribute("questions",questions);
        return "admin/Questions-Update";
    }
    //进行修改提交，回到view页面
    @RequestMapping("/admin/updateQuestions/{Id}")
    public String updateQuestions(@PathVariable("Id")Integer Id,Questions questions){


        examQuestionsService.updateQuestions(questions);
        return "redirect:/admin/viewQuestions";
    }
    /**
     *
     *  删除试题
     */

    @RequestMapping("/admin/deleteQuestions/{Id}")
    public String deleteQuestions(@PathVariable("Id") Integer Id){

       examQuestionsService.deleteQuestions(Id);

        return "redirect:/admin/viewQuestions";
    }

    /**
     *
     *  增加试题
     */

    @RequestMapping("/admin/toAddQuestions")
    public String toAddQuestions(){
        return "admin/Questions-Add";
    }
    @PostMapping("/admin/addQuestions")
    public String addQuestions(Questions questions) throws ParseException {


       examQuestionsService.addQuestions(questions);
        return "redirect:/admin/viewQuestions";
    }

}
