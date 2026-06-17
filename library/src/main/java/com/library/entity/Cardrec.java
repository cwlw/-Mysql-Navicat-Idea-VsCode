package com.library.entity;

import java.time.LocalDateTime;

public class Cardrec {
    private Integer id;
    private String serNum;
    private String sno;
    private String originCardNo;
    private String newCardNo;
    private Integer opType;
    // 新增操作时间字段，补齐get/set，解决找不到符号报错
    private LocalDateTime opTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSerNum() { return serNum; }
    public void setSerNum(String serNum) { this.serNum = serNum; }

    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }

    public String getOriginCardNo() { return originCardNo; }
    public void setOriginCardNo(String originCardNo) { this.originCardNo = originCardNo; }

    public String getNewCardNo() { return newCardNo; }
    public void setNewCardNo(String newCardNo) { this.newCardNo = newCardNo; }

    public Integer getOpType() { return opType; }
    public void setOpType(Integer opType) { this.opType = opType; }

    // 新增opTime读写
    public LocalDateTime getOpTime() {
        return opTime;
    }
    public void setOpTime(LocalDateTime opTime) {
        this.opTime = opTime;
    }
}