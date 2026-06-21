package com.library.service;
import com.library.entity.Payrec;
import com.library.vo.FineVo;
import java.math.BigDecimal;
import java.util.List;

public interface PayrecService {
    // CRUD
    Payrec getById(Integer id);
    void add(Payrec payrec);
    void update(Payrec payrec);

    // 批量缴费
    boolean batchPayFine(String sno, List<Integer> ids, String serNum, BigDecimal payAmount, String payDateStr) throws Exception;
    // 分页缴费记录
    List<FineVo> getPayRecordBySno(String sno, Integer page, Integer size);
    int getPayTotalCount(String sno);
}