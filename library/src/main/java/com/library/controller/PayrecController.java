package com.library.controller;
import com.library.entity.Payrec;
import com.library.service.PayrecService;
import com.library.util.Result;
import com.library.vo.FineVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pay")
public class PayrecController {
    @Resource
    private PayrecService payrecService;

    // 新增缴费记录 20行修复void赋值
    @PostMapping("/add")
    public Result<String> addPay(@RequestBody Payrec payrec) {
        payrecService.add(payrec);
        return Result.success("新增缴费记录成功");
    }

    // 根据id查询 45行找不到符号修复
    @GetMapping("/{id}")
    public Result<Payrec> getById(@PathVariable Integer id) {
        Payrec payrec = payrecService.getById(id);
        return Result.success(payrec);
    }

    // 修改缴费记录 59行void赋值修复
    @PutMapping("/update")
    public Result<String> updatePay(@RequestBody Payrec payrec) {
        payrecService.update(payrec);
        return Result.success("修改成功");
    }

    // 分页查询缴费记录 73行
    @GetMapping("/list")
    public Result<List<FineVo>> payList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam String sno) {
        List<FineVo> list = payrecService.getPayRecordBySno(sno, page, size);
        return Result.success(list);
    }
}