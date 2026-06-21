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

    // 修复：调用Mapper的getByCardNo，而非已改名废弃的getById
    @Override
    public Libcard getById(String cardNo) {
        return libcardMapper.getByCardNo(cardNo);
    }

    @Override
    public Result add(Libcard libcard) {
        int rows = libcardMapper.insert(libcard);
        return rows > 0 ? Result.success(null) : Result.fail("新增借阅卡失败");
    }

    @Override
    public Result update(Libcard libcard) {
        int rows = libcardMapper.update(libcard);
        return rows > 0 ? Result.success(null) : Result.fail("修改借阅卡失败");
    }

    @Override
    public Result delete(String cardNo) {
        int rows = libcardMapper.delete(cardNo);
        return rows > 0 ? Result.success(null) : Result.fail("删除借阅卡失败");
    }

    @Override
    public Result getCardBySno(String sno) {
        Libcard libcard = libcardMapper.getBySno(sno);
        if (libcard == null) {
            return Result.fail("未查询到该学生借阅卡");
        }
        return Result.success(libcard);
    }

    @Override
    public String getMaxCardNo() {
        return libcardMapper.selectMaxCardNo();
    }

    // 新增实现：根据学号直接返回Libcard实体
    @Override
    public Libcard getCardEntityBySno(String sno) {
        return libcardMapper.getBySno(sno);
    }
}