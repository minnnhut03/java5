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

	@GetMapping("/admin/caterory")
	public String adminCategory() {
		return "admin/manage_category.html";
	}

	@GetMapping("/admin/product")
	public String adminProduct() {
		return "admin/manage_product.html";
	}

	@GetMapping("/admin/product/quantity")
	public String adminQuantity() {
		return "admin/Manage_quantityDetail.html";
	}

	@GetMapping("/admin/product/image")
	public String adminImage() {
		return "admin/manage_image.html";
	}

	@GetMapping("/admin/order")
	public String adminOrder() {
		return "admin/manage_order.html";
	}


	@GetMapping("/admin/account")
	public String adminAccount() {
		return "admin/manage_user.html";
	}
	
	@GetMapping("/admin/product/add")
	public String adminProductAdd() {
		return "admin/form/add_product.html";
	}
	@GetMapping("/admin/product/edit")
	public String adminProductEdit() {
		return "admin/form/edit_product.html";
	}
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
