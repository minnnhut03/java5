package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class ManageOrderController {
	@Autowired
	OrderService orderService;
	
	 @GetMapping("")
	    public String listOrder(Model model) {
	        List<OrderEntity> orders = orderService.getAllOrder();
	        model.addAttribute("orders", orders);
	        return "admin/manage_order";
	    }
}
