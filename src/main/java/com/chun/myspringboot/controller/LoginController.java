package com.chun.myspringboot.controller;

import com.chun.myspringboot.pojo.Notice;
import com.chun.myspringboot.pojo.User;
import com.chun.myspringboot.service.Impl.NoticeServiceImpl;
import com.chun.myspringboot.service.Impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {


    @Autowired
    private UserServiceImpl userService;

    /**普通用户登录
     *登录成功，添加session
     */
    @RequestMapping("/login")
    public String login(User user, Model model, HttpSession session, HttpServletRequest request){
        System.out.println(user.getPassword());
        if(StringUtils.isNotEmpty(user.getPassword())) {
            String psd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(psd);
            System.out.println(psd);
        }

        User usr = userService.loginByEmailAndPasswordAndActiveStatus(user);
        System.out.println(usr);
        if (usr!=null){
            //设置session
            session.setAttribute("loginUser",usr);
            session.setAttribute("username",usr.getUserName());
            session.setAttribute("email",user.getEmail());
            System.out.println("成功登录");
            return "redirect:/main.html";
        }else {
            model.addAttribute("msg","密码或账号输入错误");
            return "index";
        }
    }

    /**
     * 管理员用户登录
     *
     */
    @RequestMapping("/toAdmin")
    public String toAdmin(){
        return "admin/index";
    }
    @RequestMapping("/admin")
    public String AdminLogin(User user, Model model,HttpSession session){
        if(StringUtils.isNotEmpty(user.getPassword())) {
            String psd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(psd);
        }
        User usr = userService.AdminLogin(user);
        if (usr!=null){

            session.setAttribute("loginUser",usr);
            session.setAttribute("username",usr.getUserName());
            System.out.println("管理员成功登录");
            return "redirect:/admin/main";
        }else {
            model.addAttribute("msg","密码或账号输入错误");
            return "admin/index";

}
    }
    /**
     * 注销
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

}
