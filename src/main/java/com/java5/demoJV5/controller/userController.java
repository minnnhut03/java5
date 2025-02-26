package com.java5.demoJV5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class userController {

	@Autowired
	HttpServletRequest req;

	@Autowired
	HttpServletResponse resp;

	@Autowired
	HttpSession session;

	@Autowired
	ServletContext context;

	@GetMapping("/")
	public String homePage(HttpServletRequest request, Model model) {
	    model.addAttribute("request", request);
	    return "user/index";
	}

	@GetMapping("/user/cart")
	public String cart() {
		return "user/cart.html";
	}

	@GetMapping("/contact")
	public String contact() {
		return "user/contact.html";
	}

	@GetMapping("/user/favourite")
	public String favourite() {
		return "user/favourite.html";
	}
	
	@GetMapping("/user/checkout")
	public String checkOut() {
		return "user/checkout.html";
	}
	@GetMapping("/user/oder_history")
	public String orderHistory() {
		return "user/oder_history.html";
	}

	@GetMapping("/present")
	public String present() {
		return "user/present.html";
	}

	@GetMapping("/product")
	public String product() {
		return "user/product.html";
	}

	@GetMapping("/product/detail")
	public String productDetail() {
		return "user/product-detail.html";
	}
	@GetMapping("/about")
	public String About() {
		return "user/introduce.html";
	}
}
