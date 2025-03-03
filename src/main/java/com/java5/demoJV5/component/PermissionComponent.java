package com.java5.demoJV5.component;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PermissionComponent implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
	    Cookie[] cookies = request.getCookies();
//	    if (cookies == null) {
//	       // saveRequestedUrl(request, response); // Lưu trang người dùng muốn vào
//	        response.sendRedirect("/login");
//	        return false;
//	    }

	    int role = -1;
	    boolean isLoggedIn = false;

	    for (Cookie cookie : cookies) {
	        if ("role".equals(cookie.getName())) {
	            try {
	                role = Integer.parseInt(cookie.getValue());
	            } catch (NumberFormatException e) {
	              //  saveRequestedUrl(request, response);
	                response.sendRedirect("/login");
	                return false;
	            }
	        }
	        if ("id".equals(cookie.getName()) || "email".equals(cookie.getName())) {
	            isLoggedIn = true;
	        }
	    }

	    String path = request.getServletPath();

	    // Nếu truy cập trang admin nhưng không phải admin, cấm truy cập
	    if (path.startsWith("/admin") && role != 1) {
	        response.sendRedirect("/login");
	        return false;
	    }

	    // Nếu truy cập trang /user/ nhưng chưa đăng nhập, lưu URL rồi chuyển hướng đến login
	    if (path.startsWith("/user") && !isLoggedIn) {
	      //  saveRequestedUrl(request, response);
	        response.sendRedirect("/login");
	        return false;
	    }

	    return true;
	}

	/**
	 * Lưu URL mà người dùng muốn truy cập trước khi bị chuyển hướng đến trang login
	 */
	private void saveRequestedUrl(HttpServletRequest request, HttpServletResponse response) {
	    String requestedUrl = request.getRequestURI();
	    
	    // Chỉ lưu nếu URL bắt đầu với "/user" hoặc "/admin"
	    if (!requestedUrl.startsWith("/user") && !requestedUrl.startsWith("/admin")) {
	        return;
	    }

	    Cookie cookie = new Cookie("requestedUrl", URLEncoder.encode(requestedUrl, StandardCharsets.UTF_8));
	    cookie.setMaxAge(300); // Lưu trong 5 phút
	    cookie.setPath("/");
	    cookie.setHttpOnly(true);
	    cookie.setSecure(true);
	    response.addCookie(cookie);
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
