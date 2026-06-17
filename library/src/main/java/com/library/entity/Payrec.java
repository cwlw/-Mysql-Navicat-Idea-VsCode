package com.library.entity;
import java.util.Date;
import java.math.BigDecimal;

public class Payrec {
    private Integer id;
    private String serNum;
    private String sno;
    private BigDecimal payAmount;
    private Date payDate;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSerNum() { return serNum; }
    public void setSerNum(String serNum) { this.serNum = serNum; }
    public String getSno() { return sno; }
    public void setSno(String sno) { this.sno = sno; }
    public BigDecimal getPayAmount() { return payAmount; }
    public void setPayAmount(BigDecimal payAmount) { this.payAmount = payAmount; }
    public Date getPayDate() { return payDate; }
    public void setPayDate(Date payDate) { this.payDate = payDate; }
}