package com.chun.myspringboot.service.Impl;


import com.chun.myspringboot.mapper.ExamQuestionsMapper;
import com.chun.myspringboot.pojo.Questions;
import com.chun.myspringboot.service.ExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamQuestionsServiceImpl implements ExamQuestionsService {
    //属性注入
    @Autowired
    private ExamQuestionsMapper examQuestionsMapper;

    @Override
    public List<Map<String,Object>> findExamRadioQuestions(Integer taotiid) {

        return examQuestionsMapper.findExamRadioQuestions(taotiid);
    }


    @Override
    public Questions findExamAnswerById(String id) {

        return examQuestionsMapper.findExamAnswerById(id);
    }

    @Override
    public Questions queryQuestionsById(Integer Id) {
        return examQuestionsMapper.queryQuestionsById(Id);
    }

    @Override
    public int addQuestions(Questions questions) {
        return examQuestionsMapper.addQuestions(questions);
    }

    @Override
    public int deleteQuestions(Integer Id) {
        return examQuestionsMapper.deleteQuestions(Id);
    }

    @Override
    public int updateQuestions(Questions questions) {
        return examQuestionsMapper.updateQuestions(questions);
    }

    @Override
    public List<Questions> queryAllQuestions() {
        return examQuestionsMapper.queryAllQuestions();
    }
}