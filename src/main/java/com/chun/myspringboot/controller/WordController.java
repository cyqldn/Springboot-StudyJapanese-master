package com.chun.myspringboot.controller;

import com.chun.myspringboot.pojo.Grade;
import com.chun.myspringboot.pojo.Word;
import com.chun.myspringboot.service.Impl.GradeServiceImpl;
import com.chun.myspringboot.service.Impl.WordServiceImpl;
import com.chun.myspringboot.util.DataUtils;
import com.chun.myspringboot.util.ProgressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WordController {
    @Autowired
    private WordServiceImpl wordService;
    @Autowired
    GradeServiceImpl gradeService;
    @Autowired
    private ProgressUtils progressUtils;
    @Autowired
    private DataUtils dataUtils;


    /**
     * 学习操作
     */
    //选择日语书籍
    @RequestMapping("/selectWord")
    public String SelectWord(Model model){
        List<Grade> grades = gradeService.queryAllGrade();
        Grade N1 = grades.get(0);
        Grade N2 = grades.get(1);
        Grade N3 = grades.get(2);
        Grade N4 = grades.get(3);
        //拿出id选择
        model.addAttribute("N1",N1.getGradeId());
        model.addAttribute("N2",N2.getGradeId());
        model.addAttribute("N3",N3.getGradeId());
        model.addAttribute("N4",N4.getGradeId());

        /**
         * N1操作，查出已记住单词数量与所有单词数量，得到百分比
         */

        int N1Remember = wordService.queryRememberNumberByGrade(N1.getGradeId());
        int N1Number = wordService.queryAllWordNumberByGrade(N1.getGradeId());
        model.addAttribute("N1Remember",N1Remember);
        model.addAttribute("N1Number", N1Number );
        //调用工具类得到百分比
        String percentn1 = dataUtils.percent(N1Remember, N1Number);
        model.addAttribute("percentn1",percentn1);
        /**
         * N2操作，查出已记住单词数量与所有单词数量，得到百分比
         */

        int N2Remember = wordService.queryRememberNumberByGrade(N2.getGradeId());
        int N2Number = wordService.queryAllWordNumberByGrade(N2.getGradeId());
        model.addAttribute("N2Remember",N2Remember);
        model.addAttribute("N2Number", N2Number );
        //调用工具类得到百分比
        String percentn2 = dataUtils.percent(N2Remember, N2Number);
        model.addAttribute("percentn2",percentn2);


        /**
         * N3操作，查出已记住单词数量与所有单词数量，得到百分比
         */

        int N3Remember = wordService.queryRememberNumberByGrade(N3.getGradeId());
        int N3Number = wordService.queryAllWordNumberByGrade(N3.getGradeId());
        model.addAttribute("N3Remember",N3Remember);
        model.addAttribute("N3Number", N3Number );
        //调用工具类得到百分比
        String percentn3 = dataUtils.percent(N3Remember, N3Number);
        model.addAttribute("percentn3",percentn3);

        /**
         * N4操作，查出已记住单词数量与所有单词数量，得到百分比
         */
        int N4Remember = wordService.queryRememberNumberByGrade(N4.getGradeId());
        int N4Number = wordService.queryAllWordNumberByGrade(N4.getGradeId());
        model.addAttribute("N4Number",N4Number);
        model.addAttribute("N4Remember",N4Remember);
        //调用工具类得到百分比
        String percentn4 = dataUtils.percent(N4Remember, N4Number);
        model.addAttribute("percentn4",percentn4);
        return "user/word/select-word";

    }

    //开始学习单词
    @RequestMapping("/studyWord/{grade}")
    public String StudyWord(@PathVariable("grade") Integer grade,Model model){
        //根据选择不同的grade 背不同的单词
        Word word = wordService.queryWordStudy0ByGrade(grade);
        //如果有就背，没有就提示为空
        if (word!=null){
            model.addAttribute("word",word);

            //progress工具是为了展示js动态页面
            progressUtils.Progress(model,grade);
            //查询是否收藏过单词
            progressUtils.getCollection(model,word);

            return "user/word/study-word";
        }else {
            progressUtils.Progress(model,grade);
            model.addAttribute("grade",grade);
            model.addAttribute("msg","恭喜你已经背完单词!请选择其他单词继续学习，或者点击右方按钮重新学习！");
            return "user/word/empty-word";
        }
    }


//   学习下一个单词
    @RequestMapping("/studyNextWord/{grade}")
    public String StudyNextWord(@PathVariable("grade") Integer grade){


        return "redirect:/studyWord/"+grade;
    }

// 重新学习单词
    @RequestMapping("/ResetWord/{grade}")
    public String ResetWord(@PathVariable("grade") Integer grade,Model model){

        //把study,remember清为0
        wordService.updateWordStudyByGrade(grade) ;

        return "redirect:/studyWord/"+grade;
    }


    //记住单词该单词，数据库remember记为1，显示解释
    @RequestMapping("/rememberWord/{wordId}")
    public String RememberWord(@PathVariable("wordId") Integer wordId, Model model){

        Word word = wordService.queryWordById(wordId);
        wordService.updateWordRemember1(wordId);
        model.addAttribute("word",word);
        progressUtils.Progress(model,word.getGrade());
        //学习记录加1
        wordService.updateWordStudy1(word.getWordId());
        return "user/word/remember-word";
    }


    //第一次忘记，数据库不修改数据，直接显示解释
    @RequestMapping("/unrememberWord/{wordId}")
    public String UnRememberWord(@PathVariable("wordId") int wordId,Model model){
      Word word = wordService.queryWordById(wordId);
        wordService.updateWordRemember0(wordId);
        model.addAttribute("word",word);
        progressUtils.Progress(model,word.getGrade());
        return "user/word/unremembered-word";
    }


    //第一次记住，第二次忘记，数据库修改remember为0，继续下个单词学习
    @RequestMapping("/forgetWord/{wordId}")
    public String ForgetWord(@PathVariable("wordId")int wordId,Model model){
        wordService.updateWordRemember0(wordId);
        Word word = wordService.queryWordById(wordId);
        Integer grade = word.getGrade();
        System.out.println("已经忘记单词！");
        progressUtils.Progress(model,grade);
        return "redirect:/studyWord/"+grade;
    }

//   加入收藏夹
    @RequestMapping("/addCollection/{wordId}")
    public String AddCollection(@PathVariable("wordId")int wordId,Model model){
        //先看看这个单词是不是已经收藏了
        Word word = wordService.queryWordById(wordId);

        //显示单词
        model.addAttribute("word",word);
        progressUtils.Progress(model,word.getGrade());
        //如果没有收藏过，则收藏

        if (word.getCollection()==0 ){
            wordService.updateWordCollection1(wordId);

            System.out.println("加入收藏");
            model.addAttribute("msg","加入成功");
        }else {//收藏过了，则取消收藏
           wordService.updateWordCollection0(wordId);
            model.addAttribute("msg","取消成功");
        }

        return "user/word/study-word";
    }

}
