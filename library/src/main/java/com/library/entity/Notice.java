package com.library.entity;
import java.util.Date;

public class Notice {
    private Integer id;
    private String topic;
    private String content;
    private String author;
    private Date createDate;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }
}