package com.library.service.impl;

import com.library.entity.Student;
import com.library.mapper.StudentMapper;
import com.library.service.StudentService;
import com.library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> listAll() {
        return studentMapper.listAll();
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public Result add(Student student) {
        int rows = studentMapper.insert(student);
        return rows > 0 ? Result.success(null) : Result.fail("新增学生失败");
    }

    @Override
    public Result update(Student student) {
        int rows = studentMapper.update(student);
        return rows > 0 ? Result.success(null) : Result.fail("修改学生失败");
    }

    @Override
    public Result delete(Integer id) {
        int rows = studentMapper.delete(id);
        return rows > 0 ? Result.success(null) : Result.fail("删除学生失败");
    }

    @Override
    public Result getStudentWithCard(String sno) {
        Student student = studentMapper.getStudentWithCardBySno(sno);
        // 关键：学号不存在直接返回提示，杜绝空指针
        if (student == null) {
            return Result.fail("未查询到该学号学生");
        }
        // 判断有无借阅卡
        boolean hasCard = student.getCardNo() != null && !student.getCardNo().trim().isEmpty();
        Map<String, Object> data = new HashMap<>();
        data.put("sno", student.getSno());
        data.put("username", student.getUsername());
        data.put("collage", student.getCollage());
        data.put("major", student.getMajor());
        data.put("type", student.getType());
        data.put("birth", student.getBirth());
        data.put("originPlace", student.getOriginPlace());
        data.put("hasCard", hasCard);
        return Result.success(data);
    }
}