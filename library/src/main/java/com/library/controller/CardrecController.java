package com.library.controller;

import com.library.entity.Cardrec;
import com.library.service.CardrecService;
import com.library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/cardrec")
public class CardrecController {

    @Autowired
    private CardrecService cardrecService;

    // 查询全部操作流水
    @GetMapping("/list")
    public Result<List<Cardrec>> listAll() {
        List<Cardrec> list = cardrecService.listAll();
        return Result.success(list);
    }

    // 修复类型问题：@PathVariable使用Long接收id，不再用Integer
    @GetMapping("/{id}")
    public Result<Cardrec> getById(@PathVariable Long id) {
        Cardrec rec = cardrecService.getById(id);
        return Result.success(rec);
    }
}