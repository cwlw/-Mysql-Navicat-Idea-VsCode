package com.library.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    // ===================== 新增静态工具方法 =====================
    /**
     * 获取登录用户类型：admin / student
     */
    public static String getLoginUserType(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("userType");
    }

    /**
     * 获取当前登录学生学号（仅student类型可用）
     */
    public static String getCurrentSno(HttpServletRequest request) {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser instanceof com.library.entity.Student) {
            return ((com.library.entity.Student) loginUser).getSno();
        }
        return null;
    }

    /**
     * 获取当前登录管理员账号（仅admin类型可用）
     */
    public static String getCurrentAdminName(HttpServletRequest request) {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser instanceof com.library.entity.Admin) {
            return ((com.library.entity.Admin) loginUser).getUsername();
        }
        return null;
    }

    // ===================== 原有拦截逻辑不变 =====================
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        // 免登录放行接口：登录、获取用户、退出、图书公开查询、图书详情
        if (uri.contains("/api/login")
                || uri.contains("/api/getCurrentUser")
                || uri.contains("/api/logout")
                || uri.contains("/api/book/list")
                || uri.contains("/api/bookcopy/list")
                || uri.contains("/api/borrowrec/list")
                // 新增图书详情接口放行
                || uri.contains("/api/book/detail")) {
            return true;
        }
        Object loginUser = request.getSession().getAttribute("loginUser");
        String userType = (String) request.getSession().getAttribute("userType");
        // 未登录拦截返回401
        if (loginUser == null || userType == null) {
            response.setContentType("application/json;charset=utf-8");
            // 原生拼接JSON，不需要fastjson
            String json = "{\"code\":401,\"msg\":\"请先登录账号\",\"data\":null}";
            response.getWriter().write(json);
            return false;
        }
        return true;
    }
}