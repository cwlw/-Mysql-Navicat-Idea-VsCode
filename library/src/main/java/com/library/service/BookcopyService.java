package com.library.service;

import com.library.dto.BookCopyDTO;
import com.library.entity.Bookcopy;
import com.library.mapper.BookcopyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookcopyService {
    @Autowired
    private BookcopyMapper bookcopyMapper;

    // 原有基础方法
    public List<Bookcopy> listAll() {
        return bookcopyMapper.listAll();
    }

    public Bookcopy getById(String barCode) {
        return bookcopyMapper.getById(barCode);
    }

    public boolean add(Bookcopy c) {
        // 新增副本默认状态0正常，历史状态置空
        if (c.getStatus() == null) {
            c.setStatus(0);
        }
        c.setOldStatus(c.getStatus());
        return bookcopyMapper.insert(c) > 0;
    }

    public boolean update(Bookcopy c) {
        return bookcopyMapper.update(c) > 0;
    }

    public boolean delete(String barCode) {
        return bookcopyMapper.delete(barCode) > 0;
    }

    public List<Bookcopy> listAvailableByIsbn(String isbn) {
        return bookcopyMapper.listAvailableByIsbn(isbn);
    }

    // ===================== 采编新增业务方法 =====================
    /**
     * 注销副本：状态改为2注销，保存原状态到oldStatus
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelCopy(String barCode) {
        return bookcopyMapper.cancelCopy(barCode) > 0;
    }

    /**
     * 根据ISBN查询副本DTO列表（给前端编辑页面）
     */
    public List<BookCopyDTO> listCopyDtoByIsbn(String isbn) {
        return bookcopyMapper.listCopyDtoByIsbn(isbn);
    }
}