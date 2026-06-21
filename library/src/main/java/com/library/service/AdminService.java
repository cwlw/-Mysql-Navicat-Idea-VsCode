package com.library.service;

import com.library.entity.Admin;
import com.library.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin login(String username, String password) {
        return adminMapper.login(username, password);
    }

    // 根据id查询管理员
    public Admin getById(Integer id) {
        return adminMapper.selectById(id);
    }

    // 新增：修改管理员密码
    public int updatePwd(Integer id, String newPwd){
        return adminMapper.updatePwd(id, newPwd);
    }
}