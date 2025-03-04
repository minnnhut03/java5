package com.java5.demoJV5.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;

import com.java5.demoJV5.bean.OrderDetailRequest;
import com.java5.demoJV5.bean.OrderRequest;
import com.java5.demoJV5.entity.CartDetailEntity;
import com.java5.demoJV5.jpa.CartDetailJPA;
import com.java5.demoJV5.service.CartService;
import com.java5.demoJV5.service.OrderService;


import jakarta.servlet.http.HttpServletRequest;

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
        
        // Fetch the cart items for the user
        List<CartDetailEntity> cartItems = cartDetailJPA.findByUserId(Integer.valueOf(userId));
        
        // Check if cart is empty
        if (cartItems.isEmpty()) {
            // Return an error response if cart is empty
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Location", "/user/cart")
                    .body(null); // 400 Bad Request - Redirect to cart page
        }
        
        // Proceed with order creation if cart is not empty
        List<OrderDetailRequest> orderDetails = cartItems.stream()
            .map(item -> {
                OrderDetailRequest detail = new OrderDetailRequest();
                detail.setProductId(item.getProduct().getId());
                detail.setQuantity(item.getQuantity());
                detail.setSize(item.getSize());
                detail.setUnitPrice(item.getUnitPrice());
                return detail;
            })
            .collect(Collectors.toList());

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(Integer.valueOf(userId));
        orderRequest.setAddressId(Integer.valueOf(addressId));
        orderRequest.setOrderDetails(orderDetails);

        // Call the order service to create the order
        orderService.createOrder(orderRequest); 
        
        // Clear the user's cart after the order is placed
        cartService.clearCart();

        // Create HTTP headers for redirecting to the home page
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/"); // Redirect to home page

        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found
    }
}
