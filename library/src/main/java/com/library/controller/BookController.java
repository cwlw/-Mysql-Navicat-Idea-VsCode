package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;

    // 查询所有图书
    @GetMapping("/list")
    public Map<String, Object> list(){
        Map<String, Object> result = new HashMap<>();
        List<Book> bookList = bookService.listAll();
        result.put("code", 200);
        result.put("msg", "查询成功");
        result.put("data", bookList);
        return result;
    }

    // 根据ID查询单本图书
    @GetMapping("/get/{id}")
    public Map<String, Object> get(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        Book book = bookService.getById(id);
        if (book != null) {
            result.put("code", 200);
            result.put("msg", "查询成功");
            result.put("data", book);
        } else {
            result.put("code", 500);
            result.put("msg", "未找到该图书");
        }
        return result;
    }

    // 新增图书
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Book book){
        Map<String, Object> result = new HashMap<>();
        boolean flag = bookService.add(book);
        if (flag) {
            result.put("code", 200);
            result.put("msg", "新增成功");
        } else {
            result.put("code", 500);
            result.put("msg", "新增失败");
        }
        return result;
    }

    // 修改图书
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Book book){
        Map<String, Object> result = new HashMap<>();
        boolean flag = bookService.update(book);
        if (flag) {
            result.put("code", 200);
            result.put("msg", "修改成功");
        } else {
            result.put("code", 500);
            result.put("msg", "修改失败");
        }
        return result;
    }

    // 删除图书（改用标准 DeleteMapping）
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Integer id){
        Map<String, Object> result = new HashMap<>();
        boolean flag = bookService.delete(id);
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