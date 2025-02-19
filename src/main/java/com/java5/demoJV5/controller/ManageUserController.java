package com.java5.demoJV5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java5.demoJV5.jpa.UserJPA;
import com.java5.demoJV5.service.UserService;

@Controller
public class ManageUserController {
	
	@Autowired
	UserJPA userJPA;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/admin/account")
	public String adminAccount(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin/manage_user.html";
	}
}
