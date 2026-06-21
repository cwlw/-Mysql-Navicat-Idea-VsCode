package com.library.controller;

import com.library.entity.Admin;
import com.library.entity.Student;
import com.library.mapper.AdminMapper;
import com.library.mapper.StudentMapper;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private StudentMapper studentMapper;

    // 登录接口，新增Session存储登录信息
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> param, HttpSession session) {
        String account = param.get("username");
        String pwd = param.get("password");
        if (account == null || pwd == null || account.isBlank() || pwd.isBlank()) {
            return Result.fail("账号/密码不能为空");
        }

        // 先匹配管理员
        Admin admin = adminMapper.login(account, pwd);
        if (admin != null) {
            // 存入session，拦截器校验用
            session.setAttribute("loginUser", admin);
            session.setAttribute("userType", "admin");
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("userType", "admin");
            resMap.put("userInfo", admin);
            return Result.success(resMap);
        }

        // 管理员无匹配，匹配学生学号
        Student stu = studentMapper.getBySno(account);
        if (stu != null && pwd.equals(stu.getPassword())) {
            // 存入session，拦截器校验用
            session.setAttribute("loginUser", stu);
            session.setAttribute("userType", "student");
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("userType", "student");
            resMap.put("userInfo", stu);
            return Result.success(resMap);
        }

        // 全部匹配失败
        return Result.fail("账号或密码错误");
    }

    // 新增：获取当前登录用户身份，前端页面用来区分管理员/学生
    @GetMapping("/getCurrentUser")
    public Result<Map<String, Object>> getCurrentUser(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        String userType = (String) session.getAttribute("userType");
        if (loginUser == null || userType == null) {
            return Result.unLogin();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userType", userType);
        map.put("userInfo", loginUser);
        return Result.success(map);
    }

    // 新增：退出登录，清空session
    @GetMapping("/logout")
    public Result<String> logout(HttpSession session) {
        session.invalidate();
        return Result.success("退出登录成功");
    }
}