package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.UserBean;
import com.java5.demoJV5.jpa.UserJPA;
import com.java5.demoJV5.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;
    
    @Autowired
    UserJPA userJPA;
    
    @GetMapping("/register")
	public String register(Model model) {
    	model.addAttribute("userBean",new UserBean());
		return "user/register.html";
	}
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userBean") UserBean userBean,
    		Errors errors,
			Model model)
    {
    	if (userJPA.existsByEmail(userBean.getEmail())) {
            model.addAttribute("errorEmail", "Email đã tồn tại!");
            return "user/register.html"; 
        }
    	if (errors.hasErrors()) {
    		
        	return "user/register";
        }
        userService.saveUser(userBean);
        return "redirect:/login";
    }
}
