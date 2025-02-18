package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.LoginBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private UserJPA userJPA;

    @Autowired
    private HttpSession session;

    @PostMapping("/login/check")
    public String loginCheck(@Valid @ModelAttribute("loginBean") LoginBean loginBean,
                             BindingResult result, Model model, HttpServletResponse response) {
        if (result.hasErrors()) {
            return "user/login";
        }

        Optional<UserEntity> userOpt = userJPA.findByEmail(loginBean.getEmail());
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (user.getPassword().equals(loginBean.getPassword())) {
                session.setAttribute("loggedInUser", user);

                // Lưu cookie với thời gian sống là 2 tiếng (7200 giây)
                saveCookie(response, "name", user.getName(), 7200);
                saveCookie(response, "email", user.getEmail(), 7200);
                saveCookie(response, "role", String.valueOf(user.getRole()), 7200);

                // Kiểm tra role để chuyển hướng
                if (user.getRole() == 1) {
                    return "redirect:/admin"; // Admin chuyển đến trang quản trị
                } else {
                    return "redirect:/"; // Người dùng bình thường vào trang chủ
                }
            }
        }

        model.addAttribute("error", "Email hoặc mật khẩu không đúng");
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        session.invalidate();
        
        // Xóa cookie khi đăng xuất
        deleteCookie(response, "name");
        deleteCookie(response, "email");
        deleteCookie(response, "role");

        return "redirect:/login";
    }

    private void saveCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge); // Đặt thời gian sống của cookie
        cookie.setPath("/"); // Áp dụng cookie cho toàn bộ trang web
        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0); // Xóa cookie bằng cách đặt thời gian sống về 0
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}