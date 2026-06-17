package com.library.controller;
import com.library.entity.Notice;
import com.library.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Map<String, Object> list(){
        Map<String, Object> result = new HashMap<>();
        List<Notice> list = noticeService.listAll();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", list);
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        Notice notice = noticeService.getById(id);
        if (notice != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", notice);
        } else {
            result.put("code", 500);
            result.put("msg", "未找到数据");
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Notice notice){
        Map<String, Object> result = new HashMap<>();
        boolean flag = noticeService.add(notice);
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
    public Map<String, Object> update(@RequestBody Notice notice){
        Map<String, Object> result = new HashMap<>();
        boolean flag = noticeService.update(notice);
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
        boolean flag = noticeService.delete(id);
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