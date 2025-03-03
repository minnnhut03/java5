package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;

import com.java5.demoJV5.bean.LoginBean;
import com.java5.demoJV5.bean.OrderDetailRequest;
import com.java5.demoJV5.bean.OrderRequest;
import com.java5.demoJV5.jpa.CartDetailJPA;
import com.java5.demoJV5.jpa.OrderJPA;
import com.java5.demoJV5.service.CartDetailService;
import com.java5.demoJV5.service.CartService;
import com.java5.demoJV5.service.OrderService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartDetailJPA cartDetailJPA;
    
    @Autowired
    private CartService cartService;
  
	@Autowired
	HttpServletRequest request;
	

    @PostMapping
    public ResponseEntity<Void> createOrder(
            @RequestParam("userId") String userId,
            @RequestParam("addressId") String addressId) {
        List<OrderDetailRequest> cartItems = cartDetailJPA.findByUserId(Integer.valueOf(userId))
            .stream()
            .map(item -> {
                OrderDetailRequest detail = new OrderDetailRequest();
                detail.setProductId(item.getProduct().getId());
                detail.setQuantity(item.getQuantity());
                detail.setSize(item.getSize());
                detail.setUnitPrice(item.getUnitPrice());
                return detail;
            })
            .toList();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(Integer.valueOf(userId));
        orderRequest.setAddressId(Integer.valueOf(addressId));
        orderRequest.setOrderDetails(cartItems);

        orderService.createOrder(orderRequest); 
        
        cartService.clearCart();

        // Tạo Header HTTP cho chuyển hướng
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/"); // Chuyển hướng về trang chủ

        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found
    }

}
