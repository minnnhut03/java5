package com.java5.demoJV5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class adminController {
	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@Autowired
	HttpSession session;

	@Autowired
	ServletContext context;

	@GetMapping("/admin")
	public String admin() {
		return "admin/manage_dashboard.html";
	}
	

	

	@GetMapping("/admin/order")
	public String adminOrder() {
		return "admin/manage_order.html";
	}


//	@GetMapping("/admin/account")
//	public String adminAccount() {
//		return "admin/manage_user.html";
//	}
	
	@GetMapping("/admin/order/add")
	public String adminOrderAdd() {
		return "admin/form/add_order.html";
	}
	@GetMapping("/admin/order/edit")
	public String adminOrderEdit() {
		return "admin/form/edit_order.html";
	}
	
	@GetMapping("/admin/account/add")
	public String adminAccountAdd() {
		return "admin/form/add_user.html";
	}
	@GetMapping("/admin/account/edit")
	public String adminAccountEdit() {
		return "admin/form/edit_user.html";
	}
	
	

}
