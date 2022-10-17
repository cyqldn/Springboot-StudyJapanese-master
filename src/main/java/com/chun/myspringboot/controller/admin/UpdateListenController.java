package com.chun.myspringboot.controller.admin;

import com.chun.myspringboot.pojo.Listen;
import com.chun.myspringboot.pojo.Listen;
import com.chun.myspringboot.service.Impl.ListenServiceImpl;
import com.chun.myspringboot.service.Impl.ListenServiceImpl;
import com.chun.myspringboot.service.Impl.NoticeServiceImpl;
import com.chun.myspringboot.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.List;

@Controller
public class UpdateListenController {
    @Autowired
    NoticeServiceImpl noticeService;
    @Autowired
    DataUtils dataUtils;
    @Autowired
    ListenServiceImpl ListenService;

    /**
     *
     * 查看听力
     */
    @RequestMapping("/admin/viewListen")
    public String viewListen(Model model){
        List<Listen> listen=ListenService.queryAllListen();
        model.addAttribute("listen",listen);
        return "admin/Listen-View";
    }


    /**
     *
     *  修改听力
     */
    //去修改页面
    @RequestMapping("/admin/toUpdateListen/{listenId}")
    public String toUpdateListen(@PathVariable("listenId")Integer ListenId,Model model){

        Listen Listen = ListenService.queryListenById(ListenId);
        model.addAttribute("Listen",Listen);
        return "admin/Listen-Update";
    }
    //进行修改提交，回到view页面
    @RequestMapping("/admin/updateListen/{ListenId}")
    public String updateListen(@PathVariable("ListenId")Integer ListenId,Listen Listen){


        ListenService.updateListen(Listen);
        return "redirect:/admin/viewListen";
    }
    /**
     *
     *  删除听力
     */

    @RequestMapping("/admin/deleteListen/{ListenId}")
    public String deleteListen(@PathVariable("ListenId") Integer ListenId){

        ListenService.deleteListen(ListenId);

        return "redirect:/admin/viewListen";
    }

    /**
     *
     *  增加听力
     */

    @RequestMapping("/admin/toAddListen")
    public String toAddListen(){
        return "admin/Listen-Add";
    }
    @PostMapping("/admin/addListen")
    public String addListen(Listen Listen) throws ParseException {


        ListenService.addListen(Listen);
        return "redirect:/admin/viewListen";
    }

}
