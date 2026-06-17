package com.library.service;

import com.library.entity.Cardrec;
import com.library.mapper.CardrecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardrecService {

    @Autowired
    private CardrecMapper cardrecMapper;

    // 分页/查询全部流水记录
    public List<Cardrec> listAll() {
        return cardrecMapper.listAll();
    }

    // 修复类型问题：参数改为Long接收id
    public Cardrec getById(Long id) {
        return cardrecMapper.getById(id);
    }
}