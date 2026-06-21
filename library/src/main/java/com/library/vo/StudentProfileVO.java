package com.library.vo;

import com.library.entity.Libcard;
import com.library.entity.Student;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class StudentProfileVO {
    private Student student;
    private Libcard libcard;
    private Integer currentBorrowCount;
    private BigDecimal totalUnpaidFine;
    private List<BorrowVo> currentBorrowList;
}