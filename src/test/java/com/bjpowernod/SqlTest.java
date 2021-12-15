package com.bjpowernod;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/applicationcontext_dao.xml","classpath:conf/applicationcontext_service.xml"})
public class SqlTest {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        for(ProductInfo info:list){
            System.out.println(info);
        }
    }

}
