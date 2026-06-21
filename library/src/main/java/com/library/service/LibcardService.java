package com.library.service;

import com.library.entity.Libcard;
import com.library.util.Result;
import java.util.List;

public interface LibcardService {
    List<Libcard> listAll();
    Libcard getById(String cardNo);
    Result add(Libcard libcard);
    Result update(Libcard libcard);
    Result delete(String cardNo);
    Result getCardBySno(String sno);
    String getMaxCardNo();

    // 新增：根据学号直接返回Libcard实体（个人中心用）
    Libcard getCardEntityBySno(String sno);
}