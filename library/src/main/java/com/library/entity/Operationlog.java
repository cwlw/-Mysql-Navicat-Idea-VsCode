package com.library.entity;
import java.util.Date;

public class Operationlog {
    private Integer id;
    private Integer adminId;
    private Date operateTime;
    private String operateType;
    private String content;
    private String ip;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getAdminId() { return adminId; }
    public void setAdminId(Integer adminId) { this.adminId = adminId; }
    public Date getOperateTime() { return operateTime; }
    public void setOperateTime(Date operateTime) { this.operateTime = operateTime; }
    public String getOperateType() { return operateType; }
    public void setOperateType(String operateType) { this.operateType = operateType; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
}