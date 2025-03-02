package com.java5.demoJV5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {
	@GetMapping("/order") 
	public String orders() {
		
		return "";
	}
	@PostMapping("/order")
	public String order() {
		return "";
	}
}
