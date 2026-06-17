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
}