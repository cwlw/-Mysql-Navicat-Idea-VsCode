package com.library.mapper;

import com.library.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

public interface StudentMapper {

    @Select("select * from student")
    List<Student> listAll();

    @Select("select * from student where id = #{id}")
    Student getById(@Param("id") Integer id);

    int insert(Student student);

    int update(Student student);

    int delete(@Param("id") Integer id);

    // 登录专用：只按学号查学生
    @Select("select * from student where sno = #{sno}")
    Student getBySno(@Param("sno") String sno);

    // 联表查询学生+借阅卡（原有业务方法保留）
    @Select("SELECT s.*, l.cardNo FROM student s LEFT JOIN libcard l ON s.sno = l.sno WHERE s.sno = #{sno}")
    Student getStudentWithCardBySno(@Param("sno") String sno);

    // ===================== 个人中心新增 =====================
    // 根据学号修改学生可编辑信息
    @Update("UPDATE student SET username=#{username},birth=#{birth},origin_place=#{originPlace} WHERE sno=#{sno}")
    void updateStudentBySno(@Param("sno") String sno,
                            @Param("username") String username,
                            @Param("birth") java.util.Date birth,
                            @Param("originPlace") String originPlace);

    // 根据学号查询密码
    @Select("SELECT password FROM student WHERE sno = #{sno}")
    String getPwdBySno(@Param("sno") String sno);

    // 修改密码
    @Update("UPDATE student SET password = #{newPwd} WHERE sno = #{sno}")
    void updatePwdBySno(@Param("sno") String sno, @Param("newPwd") String newPwd);
}