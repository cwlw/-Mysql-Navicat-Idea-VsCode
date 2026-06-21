package com.library.entity;
import java.util.Date;

public class Book {
    private Integer id;
    private String ISBN;
    private String bname;
    private String author;
    private String publisher;
    private String introduction;
    private Date pubDate;
    private String clcNum;
    private Integer bookStatus;

    // 新增：前端搜索关键字（非数据库字段，临时参数）
    private String keyword;

    // 无参构造
    public Book() {
    }

    // 全参构造
    public Book(Integer id, String ISBN, String bname, String author, String publisher, String introduction, Date pubDate, String clcNum, Integer bookStatus, String keyword) {
        this.id = id;
        this.ISBN = ISBN;
        this.bname = bname;
        this.author = author;
        this.publisher = publisher;
        this.introduction = introduction;
        this.pubDate = pubDate;
        this.clcNum = clcNum;
        this.bookStatus = bookStatus;
        this.keyword = keyword;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getISBN() { return ISBN; }
    public void setISBN(String ISBN) { this.ISBN = ISBN; }
    public String getBname() { return bname; }
    public void setBname(String bname) { this.bname = bname; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public Date getPubDate() { return pubDate; }
    public void setPubDate(Date pubDate) { this.pubDate = pubDate; }
    public String getClcNum() { return clcNum; }
    public void setClcNum(String clcNum) { this.clcNum = clcNum; }
    public Integer getBookStatus() { return bookStatus; }
    public void setBookStatus(Integer bookStatus) { this.bookStatus = bookStatus; }

    // 新增 keyword get/set
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // 新增toString
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ISBN='" + ISBN + '\'' +
                ", bname='" + bname + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", introduction='" + introduction + '\'' +
                ", pubDate=" + pubDate +
                ", clcNum='" + clcNum + '\'' +
                ", bookStatus=" + bookStatus +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}