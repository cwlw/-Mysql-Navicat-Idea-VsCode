package com.library.controller;

import com.library.entity.Admin;
import com.library.service.AdminService;
import com.library.util.Result;
import com.library.vo.AdminProfileVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 获取当前登录管理员个人信息接口
    @GetMapping("/admin/profile")
    public Result<AdminProfileVO> getAdminProfile(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        String userType = (String) session.getAttribute("userType");
        if (loginUser == null) {
            return Result.unLogin();
        }
        if (!"admin".equals(userType)) {
            return Result.fail("仅管理员可访问管理员个人中心");
        }
        Admin loginAdmin = (Admin) loginUser;
        Admin admin = adminService.getById(loginAdmin.getId());
        if (admin == null) {
            return Result.fail("管理员信息不存在");
        }
        AdminProfileVO vo = new AdminProfileVO();
        vo.setId(admin.getId());
        vo.setUsername(admin.getUsername());
        // 数据库无此字段，默认null，前端页面自动展示空
        vo.setName(null);
        vo.setTel(null);
        vo.setEmail(null);
        return Result.success(vo);
    }

    // 新增：管理员修改密码接口，更新数据库
    @PutMapping("/admin/updatePwd")
    public Result<String> updateAdminPwd(@RequestBody Map<String,String> param, HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        String userType = (String) session.getAttribute("userType");
        if (loginUser == null || !"admin".equals(userType)) {
            return Result.unLogin();
        }
        Integer adminId = ((Admin) loginUser).getId();
        String oldPwd = param.get("oldPwd");
        String newPwd = param.get("newPwd");
        // 参数校验
        if(oldPwd == null || newPwd == null || oldPwd.isBlank() || newPwd.isBlank()){
            return Result.fail("密码不能为空");
        }
        // 校验原密码是否正确
        Admin dbAdmin = adminService.getById(adminId);
        if(!dbAdmin.getPassword().equals(oldPwd)){
            return Result.fail("原密码输入错误");
        }
        // 更新数据库密码
        int row = adminService.updatePwd(adminId, newPwd);
        if(row > 0){
            // 修改成功清空session，强制重登
            session.invalidate();
            return Result.success("密码修改成功");
        }else{
            return Result.fail("密码修改失败");
        }
    }

    // 原有注释保留
    // 此处放管理员相关CRUD接口，登录功能统一交给LoginController，不要再写/login接口
}