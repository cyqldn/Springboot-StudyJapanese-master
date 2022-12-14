package com.chun.myspringboot.service.Impl;


import com.chun.myspringboot.mapper.StuExamResultMapper;
import com.chun.myspringboot.pojo.StuInformation;
import com.chun.myspringboot.pojo.StuResult;
import com.chun.myspringboot.service.StuExamResultService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Service
public class StuExamResultServiceImpl implements StuExamResultService {
    //属性注入
    @Autowired
    private StuExamResultMapper stuExamResultMapper;

    @Override
    public boolean addStuExamResult(StuResult stuResult) {
        int a=0;
        a= stuExamResultMapper.addStuExamResult(stuResult);
        if(a==0){
            return  false;
        }
        else {
            return true;
        }
    }


    @Override
    public StuInformation findExamStuByUsername(String username) {
        return stuExamResultMapper.findExamStuByUsername(username);
    }

    @Override
    public List<Map<String, Object>> findExamByUsername(String username) {
        return stuExamResultMapper.findExamByUsername(username);
    }

    @Override
    public void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception{
        response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out=response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }





}
