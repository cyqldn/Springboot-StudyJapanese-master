package com.chun.myspringboot.mapper;


import com.chun.myspringboot.pojo.StuInformation;
import com.chun.myspringboot.pojo.StuResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface StuExamResultMapper {
    //添加学生考试成绩记录
    public int addStuExamResult(StuResult stuResult);

    public StuInformation findExamStuByUsername(String username);
    //返回用户考试所有记录
    public List<Map<String,Object>>findExamByUsername(String username);
    //保存考试信息到Excel
    void export(HttpServletResponse response, Workbook workbook, String fileName)throws Exception;

}
