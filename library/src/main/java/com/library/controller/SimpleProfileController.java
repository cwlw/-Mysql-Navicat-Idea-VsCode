package com.library.controller;

import com.library.dto.PasswordDTO;
import com.library.dto.StudentUpdateDTO;
import com.library.service.BorrowrecService;
import com.library.service.LibcardService;
import com.library.service.SimpleProfileService;
import com.library.service.StudentService;
import com.library.util.LoginInterceptor;
import com.library.util.Result;
import com.library.vo.BorrowVo;
import com.library.vo.FineVo;
import com.library.vo.StudentProfileVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class SimpleProfileController {
    @Resource
    private StudentService studentService;
    @Resource
    private LibcardService libcardService;
    @Resource
    private BorrowrecService borrowService;
    @Resource
    private SimpleProfileService simpleProfileService;
    @Resource
    private HttpServletRequest request;

    /**
     * 获取学生个人中心综合信息
     */
    @GetMapping("/profile")
    public Result<StudentProfileVO> getProfile(){
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("管理员无学生个人中心访问权限");
        }
        String sno = LoginInterceptor.getCurrentSno(request);
        StudentProfileVO profileVO = simpleProfileService.getStudentProfile(sno);
        return Result.success(profileVO);
    }

    /**
     * 查询当前在借图书
     */
    @GetMapping("/borrow")
    public Result<List<BorrowVo>> getBorrow(){
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String sno = LoginInterceptor.getCurrentSno(request);
        return Result.success(borrowService.getBorrowListBySno(sno));
    }

    /**
     * 查询未结清罚款
     */
    @GetMapping("/fine/unpaid")
    public Result<List<FineVo>> getFine(){
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String sno = LoginInterceptor.getCurrentSno(request);
        return Result.success(borrowService.getUnpaidFineBySno(sno));
    }

    /**
     * 批量缴纳罚款【简化版：不操作数据库，直接返回支付成功】
     */
    @PostMapping("/fine/pay")
    public Result<String> payFine(@RequestBody Map<String,Object> param) {
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String loginSno = LoginInterceptor.getCurrentSno(request);
        String paramSno = (String) param.get("sno");
        if (!loginSno.equals(paramSno)) {
            return Result.fail("禁止操作他人罚款订单");
        }
        // 直接跳过数据库更新逻辑，弹窗支付成功
        return Result.success("缴费成功");
    }

    // 修改个人基础信息
    @PutMapping("/profile/update")
    public Result<String> updateStudentInfo(@RequestBody StudentUpdateDTO dto) {
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String loginSno = LoginInterceptor.getCurrentSno(request);
        if (!loginSno.equals(dto.getSno())) {
            return Result.fail("禁止修改他人信息");
        }
        simpleProfileService.updateStudentInfo(dto);
        return Result.success("个人信息修改成功");
    }

    // 修改登录密码【方法调用和接口名统一 updatePassword】
    @PutMapping("/profile/pwd")
    public Result<String> updatePassword(@RequestBody PasswordDTO dto) {
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String loginSno = LoginInterceptor.getCurrentSno(request);
        if (!loginSno.equals(dto.getAccountId())) {
            return Result.fail("禁止修改他人账号密码");
        }
        return simpleProfileService.updatePassword(dto);
    }

    // 分页查询全部借阅历史
    @GetMapping("/borrow/history")
    public Result<Map<String,Object>> borrowHistory(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String sno = LoginInterceptor.getCurrentSno(request);
        List<BorrowVo> list = simpleProfileService.getBorrowHistory(sno, page, size);
        int total = simpleProfileService.getBorrowTotal(sno);
        Map<String,Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", total);
        data.put("page", page);
        data.put("size", size);
        return Result.success(data);
    }

    // 分页查询缴费记录
    @GetMapping("/fine/record")
    public Result<Map<String,Object>> fineRecord(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        String loginType = LoginInterceptor.getLoginUserType(request);
        if ("admin".equals(loginType)) {
            return Result.fail("权限不足");
        }
        String sno = LoginInterceptor.getCurrentSno(request);
        List<FineVo> list = simpleProfileService.getFineRecord(sno, page, size);
        int total = simpleProfileService.getFineTotal(sno);
        Map<String,Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", total);
        data.put("page", page);
        data.put("size", size);
        return Result.success(data);
    }
}