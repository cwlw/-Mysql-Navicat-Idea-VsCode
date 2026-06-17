package com.library.entity;

public class Bookcopy {
    // 图书条码（主键）
    private String barCode;
    // 关联图书ISBN
    private String ISBN;
    // 馆藏存放位置
    private String place;
    // 当前副本状态
    private Integer status;
    // 副本历史状态
    private Integer oldStatus;
    // 馆藏地
    private String location;
    // 当前所在地
    private String nowLocation;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNowLocation() {
        return nowLocation;
    }

    public void setNowLocation(String nowLocation) {
        this.nowLocation = nowLocation;
    }
}