package com.java5.demoJV5.component;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PermissionComponent implements HandlerInterceptor{
	
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
	        Cookie[] cookies = request.getCookies();
	        if (cookies == null) {
	            response.sendRedirect("/login");
	            return false;
	        }

	        int role = -1; // Giá trị mặc định nếu không tìm thấy cookie role

	        // Lấy role từ cookie
	        for (Cookie cookie : cookies) {
	            if ("role".equals(cookie.getName())) {
	                try {
	                    role = Integer.parseInt(cookie.getValue());
	                } catch (NumberFormatException e) {
	                    response.sendRedirect("/login"); // Role không hợp lệ, yêu cầu đăng nhập lại
	                    return false;
	                }
	                break;
	            }
	        }

	        // Nếu không tìm thấy cookie role -> bắt buộc đăng nhập lại
	        if (role == -1) {
	            response.sendRedirect("/login");
	            return false;
	        }

	        String path = request.getServletPath();

	        // Nếu truy cập trang admin nhưng không phải admin, cấm truy cập
	        if (path.startsWith("/admin") && role != 1) {
	            response.sendRedirect("/403"); // Chuyển hướng đến trang lỗi 403
	            return false;
	        }

	        return true;
	    }
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
