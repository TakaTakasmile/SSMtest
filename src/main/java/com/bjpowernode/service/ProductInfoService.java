package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfo> selectAll();

    PageInfo<ProductInfo> splitPage(Integer pageNum,Integer pageSize);

}
