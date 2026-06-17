package com.library.service.impl;

import com.library.entity.Libcard;
import com.library.mapper.LibcardMapper;
import com.library.service.LibcardService;
import com.library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibcardServiceImpl implements LibcardService {

    @Autowired
    private LibcardMapper libcardMapper;

    @Override
    public List<Libcard> listAll() {
        return libcardMapper.listAll();
    }

    @Override
    public Libcard getById(String cardNo) {
        return libcardMapper.getById(cardNo);
    }

    @Override
    public Result add(Libcard libcard) {
        int row = libcardMapper.insert(libcard);
        return row > 0 ? Result.success("新增成功") : Result.fail("新增失败");
    }

    @Override
    public Result update(Libcard libcard) {
        int row = libcardMapper.update(libcard);
        return row > 0 ? Result.success("修改成功") : Result.fail("修改失败");
    }

    @Override
    public Result delete(String cardNo) {
        int row = libcardMapper.delete(cardNo);
        return row > 0 ? Result.success("删除成功") : Result.fail("删除失败");
    }

    @Override
    public Result getCardBySno(String sno) {
        Libcard card = libcardMapper.getBySno(sno);
        if (card == null) {
            return Result.fail("未查询到借阅卡");
        }
        return Result.success(card);
    }

    @Override
    public String getMaxCardNo() {
        return libcardMapper.selectMaxCardNo();
    }
}