package com.library.vo;

import com.library.entity.Libcard;
import com.library.entity.Student;
import lombok.Data;

@Data
public class BorrowCheckVo {
    // 是否允许借书 true=可借 false=不可借
    private Boolean canBorrow;
    // 不可借书原因（canBorrow=false时赋值）
    private String msg;
    // 借阅卡信息
    private Libcard libcard;
    // 学生信息
    private Student student;
}