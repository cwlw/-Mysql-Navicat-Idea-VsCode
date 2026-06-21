package com.library.service.impl;
import com.library.entity.Payrec;
import com.library.mapper.BorrowrecMapper;
import com.library.mapper.PayrecMapper;
import com.library.service.PayrecService;
import com.library.vo.FineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PayrecServiceImpl implements PayrecService {
    @Autowired
    private BorrowrecMapper borrowrecMapper;
    @Autowired
    private PayrecMapper payrecMapper;

    @Override
    public Payrec getById(Integer id) {
        return payrecMapper.selectById(id);
    }

    @Override
    public void add(Payrec payrec) {
        payrecMapper.insert(payrec);
    }

    @Override
    public void update(Payrec payrec) {
        payrecMapper.update(payrec);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchPayFine(String sno, List<Integer> ids, String serNum, BigDecimal payAmount, String payDateStr) throws Exception {
        borrowrecMapper.batchUpdateFineStatus(sno, ids, serNum);
        Payrec payrec = new Payrec();
        payrec.setSerNum(serNum);
        payrec.setSno(sno);
        payrec.setPayAmount(payAmount);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date payDate = sdf.parse(payDateStr);
        payrec.setPayDate(payDate);
        payrecMapper.insert(payrec);
        return true;
    }

    @Override
    public List<FineVo> getPayRecordBySno(String sno, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return payrecMapper.selectPayPage(sno, offset, size);
    }

    @Override
    public int getPayTotalCount(String sno) {
        return payrecMapper.countPayBySno(sno);
    }
}