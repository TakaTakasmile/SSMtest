package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/prod")
public class ProductInfoController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private ProductInfoService productInfoService;

    /*@RequestMapping("/split.action")
    public ModelAndView selectAll(){
        ModelAndView mv = new ModelAndView();
        List<ProductInfo> prodList = productInfoService.selectAll();
        mv.addObject("list",prodList);
        mv.setViewName("product");
        return mv;
    }*/

    @RequestMapping("/split.action")
    public ModelAndView splitPage(){
        ModelAndView mv = new ModelAndView();
        PageInfo<ProductInfo> info = productInfoService.splitPage(1,PAGE_SIZE);
        mv.addObject("info",info);
        mv.setViewName("product");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/ajaxsplit.action")
    public void ajaxSplit(Integer page, HttpSession session){
        PageInfo<ProductInfo> info = productInfoService.splitPage(page,PAGE_SIZE);
        session.setAttribute("info",info);
    }

}
