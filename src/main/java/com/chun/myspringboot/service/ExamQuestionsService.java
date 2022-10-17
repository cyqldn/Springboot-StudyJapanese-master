package com.chun.myspringboot.service;



import com.chun.myspringboot.pojo.Listen;
import com.chun.myspringboot.pojo.Questions;

import java.util.List;
import java.util.Map;

public interface ExamQuestionsService {
    public List<Map<String,Object>> findExamRadioQuestions(Integer taotiid);

    public Questions findExamAnswerById(String id);

    //根据ID查询一条听力的信息
    Questions queryQuestionsById(Integer Id);
    //增加一条听力
    int addQuestions(Questions questions);
    //删除一条听力
    int deleteQuestions(Integer Id);
    //修改一条听力
    int updateQuestions(Questions questions);
    //查看所有听力所有信息
    List<Questions> queryAllQuestions();

}
