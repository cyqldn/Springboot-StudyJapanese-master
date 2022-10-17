package com.chun.myspringboot.controller;

import com.chun.myspringboot.pojo.User;
import com.chun.myspringboot.service.Impl.SendEmailImpl;
import com.chun.myspringboot.service.Impl.UserServiceImpl;
import com.chun.myspringboot.util.IDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SendEmailImpl sendEmail;


    //进行注册
    @RequestMapping("/register")
    public  String register(User user,Model model){

        String s = userService.queryUserEmail(user.getEmail());

        if (StringUtils.isNotEmpty(s)) {
         model.addAttribute("msg", "此邮箱已被注册");
            return "user/register/page-register";

        } else {
            System.out.println(user.getPassword());
            String psd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(psd);
            System.out.println(user.getPassword());
            //激活状态为0
            user.setActiveStatus(0);
            //得到一个UUID
            String activeCode = IDUtils.getUUID();
            user.setActiveCode(activeCode);
            //加入到数据库
            userService.addUser(user);
            System.out.println("增加成功");
            //发送邮件激活
            String email = user.getEmail();
            String subject = "来自StudyJapanese网站的激活邮件";
            String context = "<a href=\"http://localhost:8080/user/checkCode?activeCode="+activeCode+"\">激活请点击:"+activeCode+"</a>";
            sendEmail.SendEmail(email, subject, context);
            //回到主页面给用户提示
            model.addAttribute("msg", "注册成功请去邮箱中激活");
            return "index";
        }

    }

    //验证激活码 登录
    @RequestMapping("/user/checkCode")
    public String active(String activeCode,Model model){
        //查询这个激活码
        User user = userService.queryUserByActiveCode(activeCode);
        if (user != null)
        {   model.addAttribute("msg","恭喜你激活成功，现在可以立即登录");
            //给这个用户激活
            user.setActiveStatus(1);
            System.out.println(user.getActiveStatus());
            //把激活码清除

            userService.updateUser(user);
        }else {
            user.setActiveStatus(0);
        }
        return "index";
    }

    //去忘记密码页面
    @RequestMapping("/toforget")
    public String toForget(){
        return "user/register/page-forget";
    }

    @RequestMapping("/fogetlogin")
    public String login(User user, Model model, HttpSession session, HttpServletRequest request){
       /* if(StringUtils.isNotEmpty(user.getPassword())) {
            String psd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(psd);
        }*/
        User usr = userService.loginByEmailAndUsername(user);
        if (usr!=null){

            return "redirect:/main.html";
        }else {
            model.addAttribute("msg","邮箱或用户名输入错误");
            return "index";
        }
    }
}
