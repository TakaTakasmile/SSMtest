package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
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
import java.util.List;

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
    public ModelAndView splitPage(HttpSession session){
        ModelAndView mv = new ModelAndView();
        PageInfo<ProductInfo> info = null;
        ProductInfoVo vo = (ProductInfoVo) session.getAttribute("prodvo");
        if(vo != null){
            info = productInfoService.selectSplit(vo,PAGE_SIZE);
            session.removeAttribute("prodvo");
        }else{
            info = productInfoService.splitPage(1,PAGE_SIZE);
        }
        mv.addObject("info",info);
        mv.setViewName("product");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/ajaxsplit.action")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        PageInfo<ProductInfo> info = productInfoService.selectSplit(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }

    /*@ResponseBody
    @RequestMapping("/ajaxCondition.action")
    public void selectCondition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        PageInfo<ProductInfo> info = new PageInfo<>(list);
        session.setAttribute("info",info);
    }*/

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
    public ModelAndView selectOne(Integer pid,ProductInfoVo vo,HttpSession session){
        ModelAndView mv = new ModelAndView();
        ProductInfo info = productInfoService.selectOne(pid);
        session.setAttribute("prodvo",vo);
        mv.addObject("prod",info);
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

    @RequestMapping("/delete.action")
    public String delete(Integer pid,ProductInfoVo vo,HttpServletRequest request){
        int res = productInfoService.delete(pid);
        if(res == 1){
            request.setAttribute("msg","删除成功！");
            request.getSession().setAttribute("deleteprod",vo);
        }else {
            request.setAttribute("msg","删除失败！");
        }
        return "forward:/prod/deleteAjax.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjax.action",produces = "text/html;charset=utf-8")
    public Object deleteSplit(HttpServletRequest request){
        PageInfo<ProductInfo> info = null;
        ProductInfoVo vo = (ProductInfoVo) request.getSession().getAttribute("deleteprod");
        if(vo != null){
            info = productInfoService.selectSplit(vo,PAGE_SIZE);
            if(info.getList().size() == 0 && vo.getPage() >1){
                vo.setPage(vo.getPage()-1);
                info = productInfoService.selectSplit(vo,PAGE_SIZE);
            }
            request.getSession().removeAttribute("deleteprod");
        }else {
            info = productInfoService.splitPage(1,PAGE_SIZE);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }

    @RequestMapping("/deleteBatch.action")
    public String deleteBatch(String pids,ProductInfoVo vo,HttpServletRequest request){
        String[] ids= pids.split(",");
        int res = productInfoService.deleteBatch(ids);
        if(res > 0){
            request.setAttribute("msg","批量删除成功！");
            request.getSession().setAttribute("deleteprod",vo);
        }else{
            request.setAttribute("msg","批量删除失败！");
        }
        return "forward:/prod/deleteAjax.action";
    }

}
