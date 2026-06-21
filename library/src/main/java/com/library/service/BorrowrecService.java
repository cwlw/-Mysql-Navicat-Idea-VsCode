package com.library.service;
import com.library.entity.Borrowrec;
import com.library.vo.BorrowVo;
import com.library.vo.FineVo;
import java.math.BigDecimal;
import java.util.List;

public interface BorrowrecService {
    List<Borrowrec> listAll();
    Borrowrec getById(Integer id);
    int insert(Borrowrec b);
    int update(Borrowrec b);
    int delete(Integer id);

    List<BorrowVo> getBorrowListBySno(String sno);
    List<FineVo> getUnpaidFineBySno(String sno);
    BigDecimal getUnpaidFineTotalBySno(String sno);
    List<BorrowVo> getBorrowHistoryBySno(String sno, Integer page, Integer size);
    int getBorrowTotalCount(String sno);

    boolean batchPayFine(String sno, List<Integer> ids, String serNum, BigDecimal payAmount, String payDateStr) throws Exception;

    // 新增：获取当前学生最大支付流水号
    String getMaxPaySerial(String sno);
}