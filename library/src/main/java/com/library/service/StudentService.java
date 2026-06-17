package com.library.service;

import com.library.entity.Student;
import com.library.util.Result;
import java.util.List;

public interface StudentService {
    List<Student> listAll();
    Student getById(Integer id);
    Result add(Student student);
    Result update(Student student);
    Result delete(Integer id);

    // 新增接口：按学号查学生（带hasCard标识）
    Result getStudentWithCard(String sno);
}