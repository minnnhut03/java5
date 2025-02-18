package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.UserBean;
import com.java5.demoJV5.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/register")
	public String register() {
		return "user/register.html";
	}
    
    @PostMapping("/register")
    public String registerUser(@Valid UserBean userBean, BindingResult result) {
        System.out.println("Dữ liệu nhận được: " + userBean);

        // Kiểm tra lỗi trùng email và các lỗi khác
        String response = userService.saveUser(userBean, result);
        System.out.print("kfjrsdirhgfkjkrshguirfgrgdg");
        if (result.hasErrors()) {
            System.out.println("Lỗi validate: " + result.getAllErrors());
            return "user/register";
        }

        // Nếu không có lỗi, chuyển hướng đến trang đăng nhập
        return "redirect:/login";
    }
}
