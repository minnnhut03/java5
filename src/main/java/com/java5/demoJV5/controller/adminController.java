package com.java5.demoJV5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java5.demoJV5.jpa.OrderJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.jpa.UserJPA;

import jakarta.persistence.criteria.Order;
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
	
	@Autowired
	ProductJPA prJPA;
	
	@Autowired
	UserJPA userJPA;
	
	@Autowired
	OrderJPA orderJPA;

	@GetMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("pr", prJPA.findAll().size());
		model.addAttribute("user", userJPA.findAll().size()-1);
		model.addAttribute("order", orderJPA.findAll().size());
		return "admin/manage_dashboard.html";
	}
	

	

//	@GetMapping("/admin/order")
//	public String adminOrder() {
//		return "admin/manage_order.html";
//	}


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
