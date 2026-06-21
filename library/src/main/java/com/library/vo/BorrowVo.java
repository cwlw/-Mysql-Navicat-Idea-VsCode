package com.library.vo;
import lombok.Data;

@Data
public class BorrowVo {
    private Integer id;
    private String barcode;
    private String name;
    private String borrowDate;
    private String dueDate;
    private String realRetDate;
    private String status;
}