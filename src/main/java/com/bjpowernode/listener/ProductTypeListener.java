package com.bjpowernode.listener;

import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) applicationContext.getBean("productTypeServiceImpl");
        List<ProductType> productTypeList = productTypeService.getAll();
        sce.getServletContext().setAttribute("typeList",productTypeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
