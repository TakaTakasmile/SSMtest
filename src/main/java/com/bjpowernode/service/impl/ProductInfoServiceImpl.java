package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductInfoExample;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> selectAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo<ProductInfo> splitPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        ProductInfoExample example = new ProductInfoExample();
        example.setOrderByClause("p_id desc");
        List<ProductInfo> productInfoList = productInfoMapper.selectByExample(example);
        return new PageInfo<>(productInfoList);
    }

    @Override
    public int addProduct(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo selectOne(Integer pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

}
