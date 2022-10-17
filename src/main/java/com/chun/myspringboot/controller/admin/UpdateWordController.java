package com.chun.myspringboot.controller.admin;


import com.chun.myspringboot.pojo.Word;
import com.chun.myspringboot.service.Impl.WordServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class UpdateWordController {
    @Autowired
    WordServiceImpl wordService;

    /**
     * 查看单词
     */
    @RequestMapping("/admin/viewWord")
    public String viewWord(@RequestParam(required = false)String wordName,Model model ,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) {

        if(wordName!=null && wordName !="") {
            model.addAttribute("wordName",wordName);
            PageHelper.startPage(pageNum, 6);
            List<Word> search=  wordService.findByNameLike(wordName);
            PageInfo<Word> pageInfo = new PageInfo<Word>(search);
            model.addAttribute("pageInfo", pageInfo);
            if(search.size()!=0){
                model.addAttribute("word", search);
            }else
                model.addAttribute("msg","您查询的单词不存在！");

        } else {

            //默认第一页开始、一行显示5个
            PageHelper.startPage(pageNum,6);
            List<Word> word = wordService.queryAllWord();
            //pagehelper默认写法、传递session
            PageInfo<Word> pageInfo = new PageInfo<Word>(word);
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("word", word);

        }
        return "admin/Word-View";
    }


    /**
     * 修改单词
     */
    //去修改页面
    @RequestMapping("/admin/toUpdateWord/{wordId}")
    public String toUpdateWord(@PathVariable("wordId") Integer wordId, Model model) {


        Word word = wordService.queryWordById(wordId);
        model.addAttribute("word", word);
        return "admin/Word-Update";
    }

    //进行修改提交，回到view页面
    @RequestMapping("/admin/updateWord/{wordId}")
    public String updateNotice(@PathVariable("wordId")Integer wordId,Word word){

       wordService.updateWord(word);
        return "redirect:/admin/viewWord";
    }
    /**
     *
     *  删除单词
     */

    @RequestMapping("/admin/deleteWord/{wordId}")
    public String deleteNotice(@PathVariable("wordId") Integer wordId){

        wordService.deleteWord(wordId);
        return "redirect:/admin/viewWord";
    }

    /**
     *
     *  增加单词
     */
//
    @RequestMapping("/admin/toAddWord")
    public String toAddWord(){
        return "admin/Word-Add";
    }
    @PostMapping("/admin/addWord")
    public String addWord(Word word) {
        wordService.addWord(word);
        return "redirect:/admin/viewWord";
    }
    /**
     *
     *  批量添加单词
     */
    @RequestMapping("/admin/import")
    public void excelImport(@RequestParam(value="myfile") MultipartFile file, Model model, HttpSession session , HttpServletResponse response) throws IOException {

//		String fileName = file.getOriginalFilename();


    try {
        wordService.addWordFile(file);
    } catch (Exception e) {

        e.printStackTrace();
    }


    }

}
