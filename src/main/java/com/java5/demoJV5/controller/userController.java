package com.java5.demoJV5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.java5.demoJV5.component.FavoriteComponent;
import com.java5.demoJV5.jpa.ImageJPA;
import com.java5.demoJV5.jpa.ProductSizeJPA;
import com.java5.demoJV5.service.CartService;
import com.java5.demoJV5.service.CategoryService;
import com.java5.demoJV5.service.ProductService;

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
	
    @Autowired
    ProductService productService;
    
    @Autowired
    ProductSizeJPA productSizeJPA;
    
    @Autowired
    ImageJPA imageJPA;
    
    @Autowired
    CartService cartService;
    
    @Autowired
    CategoryService categoryService;
    
    @Autowired
    private FavoriteComponent favoriteComponent;

	@GetMapping("/")
	public String homePage(Model model,  HttpServletRequest request) {
		 model.addAttribute("products", productService.findAll());
		 favoriteComponent.addFavoriteAttributes(model, request);
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
