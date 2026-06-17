package com.library.controller;
import com.library.entity.Booktype;
import com.library.service.BooktypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booktype")
public class BooktypeController {
    @Autowired
    private BooktypeService booktypeService;

    @GetMapping("/list")
    public Map<String, Object> list(){
        Map<String, Object> result = new HashMap<>();
        List<Booktype> list = booktypeService.listAll();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", list);
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        Booktype booktype = booktypeService.getById(id);
        if (booktype != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", booktype);
        } else {
            result.put("code", 500);
            result.put("msg", "未找到数据");
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Booktype booktype){
        Map<String, Object> result = new HashMap<>();
        boolean flag = booktypeService.add(booktype);
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
    public Map<String, Object> update(@RequestBody Booktype booktype){
        Map<String, Object> result = new HashMap<>();
        boolean flag = booktypeService.update(booktype);
        if (flag) {
            result.put("code", 200);
            result.put("msg", "修改成功");
        } else {
            result.put("code", 500);
            result.put("msg", "修改失败");
        }
        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        boolean flag = booktypeService.delete(id);
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