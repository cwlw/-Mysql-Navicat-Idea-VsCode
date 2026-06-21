package com.library.mapper;

import com.library.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin login(@Param("username") String username, @Param("password") String password);

    Admin selectById(@Param("id") Integer id);

    // 新增更新密码
    int updatePwd(@Param("id") Integer id, @Param("newPwd") String newPwd);
}