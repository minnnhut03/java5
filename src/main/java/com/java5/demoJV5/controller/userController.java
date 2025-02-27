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
	public String homePage() {
		return "user/index";
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

	
	@GetMapping("/about")
	public String About() {
		return "user/introduce.html";
	}
}
