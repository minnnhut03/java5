package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.ProfileBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user/profile")
public class ProfileController {
    @Autowired
    UserJPA userJPA;

    @GetMapping("")
    public String profile(HttpServletRequest request, Model model) {
        String userId = getCookieValue(request, "id");
        if (userId != null) {
            Optional<UserEntity> userOpt = userJPA.findById(Integer.parseInt(userId));
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                ProfileBean profileBean = new ProfileBean(user.getName(), user.getEmail(), user.getAddress());
                model.addAttribute("user", profileBean);
            }
        }
        return "user/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("user") ProfileBean userBean,
            Errors errors,
            Model model, 
            HttpServletRequest request) {
    	
        if (errors.hasErrors()) {
            return "user/profile"; 
        }

        String userId = getCookieValue(request, "id");
        if (userId != null) {
            Optional<UserEntity> userOpt = userJPA.findById(Integer.parseInt(userId));
            if (userOpt.isPresent()) {
                UserEntity existingUser = userOpt.get();
                existingUser.setName(userBean.getName());
                existingUser.setAddress(userBean.getAddress());
                userJPA.save(existingUser);
                model.addAttribute("successMessage", "Cập nhật thành công");
            }
        }
        return "redirect:/user/profile";
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
