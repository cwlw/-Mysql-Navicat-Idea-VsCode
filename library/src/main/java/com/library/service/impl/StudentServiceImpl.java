package com.library.service.impl;

import com.library.dto.PasswordDTO;
import com.library.dto.StudentUpdateDTO;
import com.library.entity.Student;
import com.library.mapper.StudentMapper;
import com.library.service.StudentService;
import com.library.util.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
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
        if (student == null) {
            return Result.fail("未查询到该学号学生");
        }
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
        data.put("cardNo", student.getCardNo());
        return Result.success(data);
    }

    @Override
    public Student getStudentBySno(String sno) {
        return studentMapper.getBySno(sno);
    }

    // ========== 个人中心新增方法实现 ==========
    @Override
    public Student getBySno(String sno) {
        return studentMapper.getBySno(sno);
    }

    @Override
    public void updateStudent(StudentUpdateDTO dto) {
        // 拆分DTO参数传给Mapper四个参数，解决参数不匹配报错
        studentMapper.updateStudentBySno(
                dto.getSno(),
                dto.getUsername(),
                dto.getBirth(),
                dto.getOriginPlace()
        );
    }

    @Override
    public Result<String> updatePwd(String sno, PasswordDTO dto) {
        // 1. 查询原密码
        String oldDbPwd = studentMapper.getPwdBySno(sno);
        if (!oldDbPwd.equals(dto.getOldPwd())) {
            return Result.fail("原密码输入错误");
        }
        // 2. 更新新密码
        studentMapper.updatePwdBySno(sno, dto.getNewPwd());
        return Result.success("密码修改成功");
    }
}