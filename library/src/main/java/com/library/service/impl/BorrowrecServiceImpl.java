package com.library.service.impl;
import com.library.entity.Borrowrec;
import com.library.mapper.BorrowrecMapper;
import com.library.service.BorrowrecService;
import com.library.vo.BorrowVo;
import com.library.vo.FineVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BorrowrecServiceImpl implements BorrowrecService {
    @Resource
    private BorrowrecMapper borrowrecMapper;

    @Override
    public List<Borrowrec> listAll() {
        return borrowrecMapper.listAll();
    }

    @Override
    public Borrowrec getById(Integer id) {
        return borrowrecMapper.getById(id);
    }

    @Override
    public int insert(Borrowrec b) {
        return borrowrecMapper.insert(b);
    }

    @Override
    public int update(Borrowrec b) {
        return borrowrecMapper.update(b);
    }

    // 补齐缺失的delete实现，解决当前报错
    @Override
    public int delete(Integer id) {
        return borrowrecMapper.delete(id);
    }

    @Override
    public List<BorrowVo> getBorrowListBySno(String sno) {
        return borrowrecMapper.selectBorrowListBySno(sno);
    }

    @Override
    public List<FineVo> getUnpaidFineBySno(String sno) {
        return borrowrecMapper.selectUnpaidFineBySno(sno);
    }

    @Override
    public BigDecimal getUnpaidFineTotalBySno(String sno) {
        return borrowrecMapper.sumUnpaidFineBySno(sno);
    }

    @Override
    public List<BorrowVo> getBorrowHistoryBySno(String sno, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return borrowrecMapper.selectBorrowHistoryPageBySno(sno, offset, size);
    }

    @Override
    public int getBorrowTotalCount(String sno) {
        return borrowrecMapper.countAllBorrowBySno(sno);
    }

    @Override
    public boolean batchPayFine(String sno, List<Integer> ids, String serNum, BigDecimal payAmount, String payDateStr) throws Exception {
        borrowrecMapper.batchUpdateFineStatus(sno, ids, serNum);
        return true;
    }

    @Override
    public String getMaxPaySerial(String sno) {
        return borrowrecMapper.selectMaxPaySerNum(sno);
    }
}