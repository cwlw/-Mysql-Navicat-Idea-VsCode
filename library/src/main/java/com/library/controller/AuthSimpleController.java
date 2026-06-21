package com.library.controller;

import com.library.entity.Student;
import com.library.service.StudentService;
import com.library.util.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthSimpleController {
    @Resource
    private StudentService studentService;

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody Student loginForm){
        Student student = studentService.getStudentBySno(loginForm.getSno());
        if(student == null || !student.getPassword().equals(loginForm.getPassword())){
            return Result.fail("学号或密码错误");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("sno",student.getSno());
        return Result.success(data);
    }
}