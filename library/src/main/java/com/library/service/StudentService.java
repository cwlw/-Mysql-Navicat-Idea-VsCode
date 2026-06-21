package com.library.service;

import com.library.dto.PasswordDTO;
import com.library.dto.StudentUpdateDTO;
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

    // 登录专用：根据学号查询学生实体
    Student getStudentBySno(String sno);

    // ========== 新增个人中心需要的方法 ==========
    // 根据学号获取学生实体
    Student getBySno(String sno);
    // 更新学生个人基础信息
    void updateStudent(StudentUpdateDTO dto);
    // 修改学生密码
    Result<String> updatePwd(String sno, PasswordDTO dto);
}