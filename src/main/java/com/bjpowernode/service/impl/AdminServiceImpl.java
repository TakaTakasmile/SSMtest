package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.AdminExample;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        Admin admin = null;
        String password = MD5Util.getMD5(pwd);
        AdminExample example = new AdminExample();
        example.createCriteria().andANameEqualTo(name).andAPassEqualTo(password);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if(adminList.size()>0){
            admin = adminList.get(0);
        }
        return admin;
    }

}
