package com.library.controller;

import com.github.pagehelper.Page;
import com.library.dto.BookCopyDTO;
import com.library.dto.BookDTO;
import com.library.entity.Book;
import com.library.entity.Bookcopy;
import com.library.service.BookService;
import com.library.service.BookcopyService;
import com.library.vo.BookQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采编部-新书入库控制器
 */
@RestController
@RequestMapping("/api/catalogue")
public class CatalogueController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookcopyService bookcopyService;

    // 统一分页返回封装
    private Map<String, Object> pageResult(Page<BookDTO> page) {
        Map<String, Object> map = new HashMap<>();
        map.put("records", page.getResult());
        map.put("total", page.getTotal());
        map.put("pageNum", page.getPageNum());
        map.put("pageSize", page.getPageSize());
        return map;
    }

    // 1. 图书分页查询（顶部搜索查询按钮）
    @GetMapping("/book/page")
    public Map<String, Object> searchBook(BookQueryVO vo) {
        Page<BookDTO> page = bookService.pageSearch(vo);
        return pageResult(page);
    }

    // 2. 新增图书（顶部新增图书按钮）
    @PostMapping("/book/add")
    public Map<String, Object> addBook(@RequestBody Book book) {
        Map<String, Object> res = new HashMap<>();
        boolean flag = bookService.add(book);
        if (flag) {
            res.put("code", 200);
            res.put("msg", "新增图书成功");
        } else {
            res.put("code", 500);
            res.put("msg", "新增图书失败");
        }
        return res;
    }

    // 3. 编辑图书信息（图书列表编辑按钮）
    @PutMapping("/book/update")
    public Map<String, Object> updateBook(@RequestBody Book book) {
        Map<String, Object> res = new HashMap<>();
        boolean flag = bookService.update(book);
        if (flag) {
            res.put("code", 200);
            res.put("msg", "编辑图书成功");
        } else {
            res.put("code", 500);
            res.put("msg", "编辑图书失败");
        }
        return res;
    }

    // 4. 图书下架（图书列表下架按钮）
    @PutMapping("/book/down/{bookId}")
    public Map<String, Object> downBook(@PathVariable Integer bookId) {
        Map<String, Object> res = new HashMap<>();
        boolean flag = bookService.downBook(bookId);
        if (flag) {
            res.put("code", 200);
            res.put("msg", "图书下架成功");
        } else {
            res.put("code", 500);
            res.put("msg", "图书下架失败，图书不存在");
        }
        return res;
    }

    // 5. 根据ISBN查询该书全部副本（点击编辑后加载副本列表）
    @GetMapping("/copy/list/{isbn}")
    public Map<String, Object> getCopyList(@PathVariable String isbn) {
        Map<String, Object> res = new HashMap<>();
        List<BookCopyDTO> copyList = bookService.getCopyListByIsbn(isbn);
        res.put("code", 200);
        res.put("data", copyList);
        return res;
    }

    // 6. 新增图书副本（副本栏新增副本按钮）
    @PostMapping("/copy/add")
    public Map<String, Object> addCopy(@RequestBody Bookcopy bookcopy) {
        Map<String, Object> res = new HashMap<>();
        boolean flag = bookcopyService.add(bookcopy);
        if (flag) {
            res.put("code", 200);
            res.put("msg", "新增副本成功");
        } else {
            res.put("code", 500);
            res.put("msg", "新增副本失败，条码重复");
        }
        return res;
    }

    // 7. 注销副本（副本列表注销按钮）
    @PutMapping("/copy/cancel/{barCode}")
    public Map<String, Object> cancelCopy(@PathVariable String barCode) {
        Map<String, Object> res = new HashMap<>();
        boolean flag = bookcopyService.cancelCopy(barCode);
        if (flag) {
            res.put("code", 200);
            res.put("msg", "副本注销成功");
        } else {
            res.put("code", 500);
            res.put("msg", "副本注销失败，条码不存在");
        }
        return res;
    }

    // 8. 模拟扫码按钮接口（仅占位，无硬件逻辑）
    @GetMapping("/scan/tip")
    public Map<String, Object> scanTip() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "扫码功能暂未对接硬件设备，请手动输入条码搜索");
        return res;
    }
}