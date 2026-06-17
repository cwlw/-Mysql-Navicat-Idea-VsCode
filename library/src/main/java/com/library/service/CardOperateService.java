package com.library.service;

import com.library.util.Result;

public interface CardOperateService {
    // 新办
    Result applyCard(String sno, String cardNo);
    // 挂失
    Result lossCard(String sno, String cardNo);
    // 补办
    Result reissueCard(String sno, String originCardNo, String newCardNo);
    // 注销
    Result cancelCard(String sno, String cardNo);
}