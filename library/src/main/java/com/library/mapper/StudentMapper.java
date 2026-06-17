package com.library.mapper;

import com.library.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface StudentMapper {

    @Select("select * from student")
    List<Student> listAll();

    @Select("select * from student where id = #{id}")
    Student getById(@Param("id") Integer id);

    int insert(Student student);

    int update(Student student);

    int delete(@Param("id") Integer id);

    // 修复：l.card_no → l.cardNo，匹配数据库libcard真实字段
    @Select("SELECT s.*, l.cardNo FROM student s LEFT JOIN libcard l ON s.sno = l.sno WHERE s.sno = #{sno}")
    Student getStudentWithCardBySno(@Param("sno") String sno);
}