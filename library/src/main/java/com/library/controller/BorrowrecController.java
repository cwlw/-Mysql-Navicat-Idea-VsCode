package com.library.controller;
import com.library.entity.Borrowrec;
import com.library.mapper.BorrowrecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api/borrowrec")
public class BorrowrecController {
    @Autowired
    private BorrowrecMapper borrowrecMapper;

    @PostMapping("/batchUpdateFineStatus")
    public Map<String, Object> batchUpdateFineStatus(@RequestBody Map<String, Object> param) {
        Map<String, Object> result = new HashMap<>();
        try {
            String sno = (String) param.get("sno");
            List<Integer> ids = (List<Integer>) param.get("ids");
            String paySerNum = (String) param.get("paySerNum");
            borrowrecMapper.batchUpdateFineStatus(sno, ids, paySerNum);
            result.put("code", 200);
            result.put("msg", "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "更新失败");
        }
        return result;
    }

    @GetMapping("/list")
    public Map<String,Object> list(){
        List<Borrowrec> list = borrowrecMapper.listAll();
        Map<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("msg","查询成功");
        res.put("data",list);
        return res;
    }
}