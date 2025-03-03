package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.LoginBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    UserJPA userJPA;

    @Autowired
    HttpSession session;
    
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model) {
//        String referer = request.getHeader("Referer"); // Lấy URL trước đó
  //      if (referer != null && !referer.contains("/login")) {
//            String encodedUrl = URLEncoder.encode(referer, StandardCharsets.UTF_8);
//            saveCookie(response, "requestedUrl", encodedUrl, 300); // Lưu trong 5 phút
//        }
        model.addAttribute("loginBean", new LoginBean());
        return "user/login";
    }

    @PostMapping("/login/check")
    public String loginCheck(@Valid @ModelAttribute("loginBean") LoginBean loginBean,
                             BindingResult result, Model model, HttpServletRequest request,
                             HttpServletResponse response) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Thông tin nhập không hợp lệ");
            return "user/login";
        }

        Optional<UserEntity> userOpt = userJPA.findByEmail(loginBean.getEmail());
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            if (!user.getPassword().equals(loginBean.getPassword())) {
                model.addAttribute("error", "Email hoặc mật khẩu không đúng");
                return "user/login";
            }
            if (!user.getStatus()) {
                model.addAttribute("error", "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.");
                return "user/login";
            }

            // Lưu thông tin user vào session
            session.setAttribute("loggedInUser", user);
            saveCookie(response, "id", String.valueOf(user.getId()), 60 * 60 * 2);
            saveCookie(response, "email", user.getEmail(), 60 * 60 * 2);
            saveCookie(response, "role", String.valueOf(user.getRole()), 60 * 60 * 2);

//            // Đọc URL từ cookie requestedUrl
//            String requestedUrl = getCookieValue(request, "requestedUrl");
//
//            // Xóa cookie requestedUrl sau khi sử dụng
//            deleteCookie(response, "requestedUrl");
//
//            // Kiểm tra requestedUrl có hợp lệ không (chỉ cho phép URL nội bộ)
//            if (requestedUrl != null && !requestedUrl.isEmpty() && requestedUrl.startsWith("/")) {
//                return "redirect:" + requestedUrl;
//            }

            // Chuyển hướng theo quyền hạn
            return user.getRole() == 1 ? "redirect:/admin" : "redirect:/";
        }

        model.addAttribute("error", "Email hoặc mật khẩu không đúng");
        return "user/login";
    }



    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        session.invalidate();
        deleteCookie(response, "id");
        deleteCookie(response, "email");
        deleteCookie(response, "role");
        return "redirect:/login";
    }

    private void saveCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
    private String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
