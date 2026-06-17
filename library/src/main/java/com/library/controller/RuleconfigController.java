package com.library.controller;
import com.library.entity.Ruleconfig;
import com.library.service.RuleconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ruleconfig")
public class RuleconfigController {
    @Autowired
    private RuleconfigService ruleconfigService;

    @GetMapping("/list")
    public Map<String, Object> list(){
        Map<String, Object> result = new HashMap<>();
        List<Ruleconfig> list = ruleconfigService.listAll();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", list);
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        Ruleconfig ruleconfig = ruleconfigService.getById(id);
        if (ruleconfig != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", ruleconfig);
        } else {
            result.put("code", 500);
            result.put("msg", "未找到数据");
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Ruleconfig ruleconfig){
        Map<String, Object> result = new HashMap<>();
        boolean flag = ruleconfigService.add(ruleconfig);
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
    public Map<String, Object> update(@RequestBody Ruleconfig ruleconfig){
        Map<String, Object> result = new HashMap<>();
        boolean flag = ruleconfigService.update(ruleconfig);
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
        boolean flag = ruleconfigService.delete(id);
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