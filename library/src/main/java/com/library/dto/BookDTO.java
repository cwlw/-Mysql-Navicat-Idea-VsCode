package com.library.dto;

import java.util.Date;

/**
 * 图书列表返回DTO，携带副本下架标识isDown
 */
public class BookDTO {
    // 图书主键
    private Integer id;
    // 图书ISBN
    private String ISBN;
    // 图书名称
    private String bname;
    // 作者
    private String author;
    // 出版社
    private String publisher;
    // 图书简介
    private String introduction;
    // 出版日期
    private Date pubDate;
    // 分类号
    private String clcNum;
    // 图书总状态 0正常 1下架
    private Integer bookStatus;
    // 标识该行是否灰色（存在下架副本/图书下架）
    private Boolean isDown;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getClcNum() {
        return clcNum;
    }

    public void setClcNum(String clcNum) {
        this.clcNum = clcNum;
    }

    public Integer getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Boolean getIsDown() {
        return isDown;
    }

    public void setIsDown(Boolean down) {
        isDown = down;
    }
}