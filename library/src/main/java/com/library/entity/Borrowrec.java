package com.library.entity;
import java.util.Date;
import java.math.BigDecimal;

public class Borrowrec {
    private Integer id;
    private String serNum;
    private String sno;
    private String barCode;
    private Date borDate;
    private Date retDate;
    private Date realRetDate;
    private Integer retStatus;
    private Integer oddDays;
    private BigDecimal fineMoney;
    private Integer fineStatus;
    private String paySerNum;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSerNum() { return serNum; }
    public void setSerNum(String serNum) { this.serNum = serNum; }
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }
    public String getBarCode() { return barCode; }
    public void setBarCode(String barCode) { this.barCode = barCode; }
    public Date getBorDate() { return borDate; }
    public void setBorDate(Date borDate) { this.borDate = borDate; }
    public Date getRetDate() { return retDate; }
    public void setRetDate(Date retDate) { this.retDate = retDate; }
    public Date getRealRetDate() { return realRetDate; }
    public void setRealRetDate(Date realRetDate) { this.realRetDate = realRetDate; }
    public Integer getRetStatus() { return retStatus; }
    public void setRetStatus(Integer retStatus) { this.retStatus = retStatus; }
    public Integer getOddDays() { return oddDays; }
    public void setOddDays(Integer oddDays) { this.oddDays = oddDays; }
    public BigDecimal getFineMoney() { return fineMoney; }
    public void setFineMoney(BigDecimal fineMoney) { this.fineMoney = fineMoney; }
    public Integer getFineStatus() { return fineStatus; }
    public void setFineStatus(Integer fineStatus) { this.fineStatus = fineStatus; }
    public String getPaySerNum() { return paySerNum; }
    public void setPaySerNum(String paySerNum) { this.paySerNum = paySerNum; }
}