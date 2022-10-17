package com.chun.myspringboot.controller;

import com.chun.myspringboot.pojo.Word;
import com.chun.myspringboot.service.Impl.WordServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CollectionController {
    @Autowired
    WordServiceImpl wordService;

    //去收藏页面
    @RequestMapping("/toCollection")
    public String toCollection(){
        return "user/collection";
    }

    //去单词收藏
    @RequestMapping("/wordCollection")
    public String wordCollection( @RequestParam(required = false)String wordName,Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        if(wordName!=null && wordName !="") {
            model.addAttribute("wordName",wordName);
            PageHelper.startPage(pageNum, 5);
            List<Word> search=  wordService.findByNameLikeC(wordName);
            PageInfo<Word> pageInfo = new PageInfo<Word>(search);
            model.addAttribute("pageInfo", pageInfo);
            if(search.size()!=0){
                model.addAttribute("word", search);
            }else
                model.addAttribute("msg","您查询的单词不存在！");

        } else {

            PageHelper.startPage(pageNum, 5);
            List<Word> word = wordService.queryAllWordCollection();

            PageInfo<Word> pageInfo = new PageInfo<Word>(word);
            model.addAttribute("pageInfo", pageInfo);

            model.addAttribute("word", word);
        }
        return "user/word/collection-word";
    }

    //取消单词收藏
    @RequestMapping("/deleteWordCollection/{wordId}")
    public String deleteWordCollection(@PathVariable("wordId") Integer wordId, Model model){

        wordService.updateWordCollection0(wordId);
        return "redirect:/wordCollection";
    }

    //去生词本
    @RequestMapping("/wordRaw")
    public String wordRaw(@RequestParam(required = false)String wordName,Model model ,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        if(wordName!=null && wordName !="") {
            model.addAttribute("wordName",wordName);
            PageHelper.startPage(pageNum, 5);
            List<Word> search=  wordService.findByNameLike(wordName);
            PageInfo<Word> pageInfo = new PageInfo<Word>(search);
            model.addAttribute("pageInfo", pageInfo);
            if(search.size()!=0){
                model.addAttribute("word", search);
            }else
                model.addAttribute("msg","您查询的单词不存在！");

        } else {

            PageHelper.startPage(pageNum,6);
            List<Word> word = wordService.queryAllUnremembered();

            PageInfo<Word> pageInfo = new PageInfo<Word>(word);
            model.addAttribute("pageInfo",pageInfo);

            model.addAttribute("word",word);

        }

        return "user/word/raw-word";
    }

    //取消生词
    @RequestMapping("/deleteWordRaw/{wordId}")
    public String deleteWordRaw(@PathVariable("wordId") Integer wordId, Model model){
        wordService.updateWordRemember1(wordId);


        return "redirect:/wordRaw";
    }



}
