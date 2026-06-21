package com.library.vo;

/**
 * 图书分页查询入参VO
 */
public class BookQueryVO {
    // 搜索关键字：书名/作者/副本条码
    private String keyword;
    // 当前页码
    private Integer pageNum;
    // 每页条数
    private Integer pageSize;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}