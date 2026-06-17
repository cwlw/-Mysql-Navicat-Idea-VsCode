package com.library.controller;
import com.library.entity.Bookcopy;
import com.library.service.BookcopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookcopy")
public class BookcopyController {
    @Autowired
    private BookcopyService bookcopyService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        List<Bookcopy> list = bookcopyService.listAll();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", list);
        return result;
    }

    @GetMapping("/get/{barCode}")
    public Map<String, Object> get(@PathVariable String barCode) {
        Map<String, Object> result = new HashMap<>();
        Bookcopy bookcopy = bookcopyService.getById(barCode);
        if (bookcopy != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", bookcopy);
        } else {
            result.put("code", 500);
            result.put("msg", "未找到该图书副本");
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Bookcopy bookcopy) {
        Map<String, Object> result = new HashMap<>();
        boolean flag = bookcopyService.add(bookcopy);
        if (flag) {
            result.put("code", 200);
            result.put("msg", "新增成功");
        } else {
            result.put("code", 500);
            result.put("msg", "新增失败");
        }
        return result;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Bookcopy bookcopy) {
        Map<String, Object> result = new HashMap<>();
        boolean flag = bookcopyService.update(bookcopy);
        if (flag) {
            result.put("code", 200);
            result.put("msg", "修改成功");
        } else {
            result.put("code", 500);
            result.put("msg", "修改失败");
        }
        return result;
    }

    @DeleteMapping("/delete/{barCode}")
    public Map<String, Object> delete(@PathVariable String barCode) {
        Map<String, Object> result = new HashMap<>();
        boolean flag = bookcopyService.delete(barCode);
        if (flag) {
            result.put("code", 200);
            result.put("msg", "删除成功");
        } else {
            result.put("code", 500);
            result.put("msg", "删除失败");
        }
        return result;
    }
}