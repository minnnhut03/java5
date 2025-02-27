package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.entity.CartDetail;
import com.java5.demoJV5.service.CartService;


@Controller
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@GetMapping("/user/cart")
	public String cart(Model model) {
		List<CartDetail> cartItems = cartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        return "user/cart";
	}
	@PostMapping("/user/cart")
	public String AddCart() {
		
		return null;
	}
	@PostMapping("/deleteCart")
	public String deleteCart(@RequestParam(name="cartDetailId") int id) {
		cartService.deleteCartItem(id);
		return "redirect:/user/cart";
	}
	
}
