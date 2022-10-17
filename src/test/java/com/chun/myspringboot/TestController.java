package com.chun.myspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    //测试MarkDown编辑器！
    @RequestMapping("/test")
    public String Test(){
        System.out.println("进入TEST！！");
        return "Test/ToHTMLTest";
    }

    public static void main(String[] args) {
        String testpassword="202cb962ac59075b964b07152d234b70";
        String testpsd = DigestUtils.md5DigestAsHex(testpassword.getBytes());
        System.out.println(testpsd);
    }
}
