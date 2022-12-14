package com.chun.myspringboot.service;


import com.chun.myspringboot.pojo.StuInformation;
import org.apache.poi.ss.usermodel.Workbook;
import com.chun.myspringboot.pojo.StuResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StuExamResultService {
    //添加学生考试成绩记录
    public boolean addStuExamResult(StuResult stuResult);

    public StuInformation findExamStuByUsername(String username);
    //返回用户考试所有记录
    public List<Map<String,Object>> findExamByUsername(String username);

    //保存考试信息到Excel
    void export(HttpServletResponse response, Workbook workbook, String fileName)throws Exception;
}
