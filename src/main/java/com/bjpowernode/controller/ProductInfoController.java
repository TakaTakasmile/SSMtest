package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @ResponseBody
    @RequestMapping("/ajaxImag.action")
    public String ajaxImg(MultipartFile pimage, HttpServletRequest request) throws IOException {
        String saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        String path = request.getServletContext().getRealPath("/image_big");
        pimage.transferTo(new File(path + File.separator + saveFileName));
        return saveFileName;
    }

    @RequestMapping("/save.action")
    public String addProduct(ProductInfo info,HttpServletRequest request) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        info.setpDate(sdf.parse(time));
        int res = productInfoService.addProduct(info);
        if(res == 1){
            request.setAttribute("msg","商品添加成功！");
        }else{
            request.setAttribute("msg","商品添加失败！");
        }
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one.action")
    public ModelAndView selectOne(Integer pid,Integer page){
        ModelAndView mv = new ModelAndView();
        ProductInfo info = productInfoService.selectOne(pid);
        mv.addObject("prod",info);
        mv.addObject("page",page);
        mv.setViewName("update");
        return mv;
    }

    @RequestMapping("/update.action")
    public String update(ProductInfo info,HttpServletRequest request){
        int res = productInfoService.update(info);
        if(res == 1){
            request.setAttribute("msg","商品更新成功！");
        }else{
            request.setAttribute("msg","商品更新失败！");
        }
        return "forward:/prod/split.action";
    }

}
