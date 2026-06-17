package com.library.controller;

import com.library.entity.Admin;
import com.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
// 允许前端 5173 跨域访问
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 接口路径：/api/login，和前端匹配
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 接收前端 JSON 中的账号密码
            String username = body.get("username");
            String password = body.get("password");

            Admin admin = adminService.login(username, password);
            if (admin == null) {
                // 账号密码错误，对应前端 code=400
                result.put("code", 400);
                result.put("msg", "账号或密码错误");
            } else {
                // 登录成功，对应前端 code=200
                result.put("code", 200);
                result.put("msg", "登录成功");
                result.put("user", admin);
            }
        } catch (Exception e) {
            // 服务异常，对应前端 code=500
            result.put("code", 500);
            result.put("msg", "服务器错误");
        }
        return result;
    }
}