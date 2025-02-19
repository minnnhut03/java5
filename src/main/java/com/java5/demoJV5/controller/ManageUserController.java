package com.java5.demoJV5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.jpa.UserJPA;
import com.java5.demoJV5.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class ManageUserController {
	
	@Autowired
	UserJPA userJPA;
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public String adminAccount(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "admin/manage_user.html";
	}
	@PostMapping("/update-status/{id}")
	public String updateUserStatus(@PathVariable("id") int userId, @RequestParam("status") boolean status) {
	    userService.updateUserStatus(userId, status);
	    return "redirect:/admin/user";
	}

}
