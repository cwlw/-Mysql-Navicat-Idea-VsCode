package com.library.controller;

import com.library.entity.Student;
import com.library.service.StudentService;
import com.library.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 原有接口
    @GetMapping("/list")
    public List<Student> list() {
        return studentService.listAll();
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable Integer id) {
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        return studentService.delete(id);
    }

    // 新增前端查询学生接口
    @GetMapping("/getBySno")
    public Result getBySno(@RequestParam String sno) {
        return studentService.getStudentWithCard(sno);
    }
}