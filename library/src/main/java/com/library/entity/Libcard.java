package com.library.entity;
import java.util.Date;

public class Libcard {
    private Integer id;
    private String cardNo;
    private String sno;
    private String sname;
    private Integer type;
    private String collage;
    private String major;
    private Date birth;
    private String originPlace;
    private Integer cardStatus;
    private Integer times;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }
    public String getSname() { return sname; }
    public void setSname(String sname) { this.sname = sname; }
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
    public Integer getCardStatus() { return cardStatus; }
    public void setCardStatus(Integer cardStatus) { this.cardStatus = cardStatus; }
    public Integer getTimes() { return times; }
    public void setTimes(Integer times) { this.times = times; }
}