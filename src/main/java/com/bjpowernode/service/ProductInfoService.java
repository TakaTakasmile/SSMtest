package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> selectAll();

    PageInfo<ProductInfo> splitPage(Integer pageNum,Integer pageSize);

    int addProduct(ProductInfo info);

    ProductInfo selectOne(Integer pid);

    int update(ProductInfo info);

    int delete(Integer pid);

    int deleteBatch(String[] pids);

    List<ProductInfo> selectCondition(ProductInfoVo vo);
}
