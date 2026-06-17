package com.library.controller;
import com.library.entity.Role;
import com.library.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public Map<String, Object> list(){
        Map<String, Object> result = new HashMap<>();
        List<Role> list = roleService.listAll();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", list);
        return result;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();
        Role role = roleService.getById(id);
        if (role != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", role);
        } else {
            result.put("code", 500);
            result.put("msg", "未找到数据");
        }
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Role role){
        Map<String, Object> result = new HashMap<>();
        boolean flag = roleService.add(role);
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
    public Map<String, Object> update(@RequestBody Role role){
        Map<String, Object> result = new HashMap<>();
        boolean flag = roleService.update(role);
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
    public Map<String, Object> delete(@PathVariable String id){
        Map<String, Object> result = new HashMap<>();
        boolean flag = roleService.delete(id);
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