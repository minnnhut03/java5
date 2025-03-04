package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.entity.CartDetailEntity;
import com.java5.demoJV5.jpa.AddressJPA;
import com.java5.demoJV5.jpa.CartDetailJPA;
import com.java5.demoJV5.service.CartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	CartDetailJPA cartDetailJPA;
	
	@Autowired
	AddressJPA addressJPA;
	
	@Autowired
	HttpServletRequest request;
	
	@GetMapping("/user/cart")
	public String cart(Model model) {
		String id = null;
		
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("id")) {
				id = cookie.getValue();
				break;
			}
			
		}
		
		List<CartDetailEntity> cartItems = cartDetailJPA.findByUserId(Integer.parseInt(id));
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("id", id);
        model.addAttribute("myAddress", addressJPA.findByUser_Id(Integer.valueOf(id)));
       
        return "user/cart";
	}
	@PostMapping("/updateCart")
	public String UpdateCart(@RequestParam(name="quantity") int quantity,
			@RequestParam(name="productId") int productId,
			@RequestParam(name="cartDetailId") int cartDetailId
			, Model model) {
		cartService.updateQuantityCartItem(cartDetailId, quantity, productId);
		return "redirect:/user/cart";
	}
	@PostMapping("/deleteCart")
	public String deleteCart(@RequestParam(name="cartDetailId") int id) {
		cartService.deleteCartItem(id);
		return "redirect:/user/cart";
	}
	
	
}
