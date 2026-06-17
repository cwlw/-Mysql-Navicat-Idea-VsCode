package com.library.entity;
import java.util.Date;

public class Student {
    private Integer id;
    private String sno;
    private String username;
    private Integer type;
    private String collage;
    private String major;
    private Date birth;
    private String originPlace;
    private String password;
    // 左联libcard查询临时字段，无需数据库映射
    private String cardNo;

    // getter & setter
    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }
    public String getCollage() { return collage; }
    public void setCollage(String collage) { this.collage = collage; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public Date getBirth() { return birth; }
    public void setBirth(Date birth) { this.birth = birth; }
    public String getOriginPlace() { return originPlace; }
    public void setOriginPlace(String originPlace) { this.originPlace = originPlace; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}