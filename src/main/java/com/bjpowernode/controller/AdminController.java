package com.bjpowernode.controller;

import com.bjpowernode.pojo.Admin;
import com.bjpowernode.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login.action")
    public ModelAndView login(String name, String pwd){
        ModelAndView mv = new ModelAndView();
        Admin admin = adminService.login(name,pwd);
        if(admin == null){
            mv.addObject("errmsg","用户名或密码错误！");
            mv.setViewName("login");
        }else {
            mv.addObject("admin",admin);
            mv.setViewName("main");
        }
        return mv;
    }

}
