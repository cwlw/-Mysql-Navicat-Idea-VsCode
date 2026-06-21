package com.library.vo;
import lombok.Data;

@Data
public class BookBorrowVo {
    private String ISBN;
    private String bname;
    private String author;
    private String publisher;
    private String place;
    private Integer availableNum;
    // 图书副本条码
    private String barCode;
}