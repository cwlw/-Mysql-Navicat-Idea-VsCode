package com.library.controller;

import com.library.entity.Libcard;
import com.library.service.LibcardService;
import com.library.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/libcard")
public class LibcardController {

    @Autowired
    private LibcardService libcardService;

    @GetMapping("/list")
    public List<Libcard> list() {
        return libcardService.listAll();
    }

    @GetMapping("/get/{id}")
    public Result getById(@PathVariable String id) {
        Libcard card = libcardService.getById(id);
        return Result.success(card);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Libcard libcard) {
        return libcardService.add(libcard);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Libcard libcard) {
        return libcardService.update(libcard);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        return libcardService.delete(id);
    }

    @GetMapping("/getBySno")
    public Result getBySno(@RequestParam String sno) {
        return libcardService.getCardBySno(sno);
    }

    // 修改：接收学号，生成规则 C+学号+01
    @GetMapping("/maxCardNo")
    public Result<String> maxCardNo(@RequestParam String sno) {
        String newCardNo = "C" + sno + "01";
        return Result.success(newCardNo);
    }
}