package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.ChangePasswordBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class ChangePasswordController {

    @Autowired
    private UserJPA userJPA;

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("changePasswordBean", new ChangePasswordBean());
        return "user/ChangePassword"; 
    }



    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordBean changePasswordBean,
                                 BindingResult result, HttpServletRequest request, 
                                 HttpServletResponse response, Model model) {

        // Lấy userId từ cookie
        Integer userId = getUserIdFromCookie(request);
        if (userId == null) {
            return "redirect:/login"; 
        }

        if (result.hasErrors()) {
            model.addAttribute("changePasswordBean", changePasswordBean);
            return "user/ChangePassword";
        }

        // Kiểm tra mật khẩu hiện tại
        Optional<UserEntity> userOpt = userJPA.findById(userId);
        if (userOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Người dùng không tồn tại!");
            model.addAttribute("changePasswordBean", changePasswordBean);
            return "user/ChangePassword";
        }

        UserEntity user = userOpt.get();

        if (!user.getPassword().equals(changePasswordBean.getCurrentPassword())) {
            model.addAttribute("errorMessage", "Mật khẩu hiện tại không đúng!");
            model.addAttribute("changePasswordBean", changePasswordBean);
            return "user/ChangePassword";
        }

        if (!changePasswordBean.getNewPassword().equals(changePasswordBean.getConfirmPassword())) {
            model.addAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("changePasswordBean", changePasswordBean);
            return "user/ChangePassword";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(changePasswordBean.getNewPassword());
        userJPA.save(user);

        model.addAttribute("successMessage", "Mật khẩu đã được thay đổi thành công!");

        return "user/ChangePassword";  // Chuyển hướng về trang đổi mật khẩu
    }


    // Hàm lấy userId từ cookie
    private Integer getUserIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }
}
