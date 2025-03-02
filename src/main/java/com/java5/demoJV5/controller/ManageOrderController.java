package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.entity.OrderDetailEntity;
import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.entity.ProductSizeEntity;
import com.java5.demoJV5.jpa.OrderDetailJPA;
import com.java5.demoJV5.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class ManageOrderController {
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailJPA orderDetailJPA;
	
	
	
	 @GetMapping("")
	    public String listOrder(Model model) {
	        List<OrderEntity> orders = orderService.getAllOrder();
	        model.addAttribute("orders", orders);
	        return "admin/manage_order";
	    }
	 @GetMapping("/detail")
	 public String adminOrderDetail(@RequestParam("orderId") int orderId, Model model) {
	     List<OrderDetailEntity> orderDetails = orderDetailJPA.findByOrderId(orderId);
	     OrderEntity order = orderService.getOrderById(orderId); // Thêm dòng này để lấy đơn hàng
	     
	     model.addAttribute("orderDetails", orderDetails);
	     model.addAttribute("orders", order); // Thêm biến orders vào model
	     
	     return "admin/manage_orderDetail";
	 }
	 @PostMapping("/update-status/{id}")
	 public String updateOrderStatus(@PathVariable("id") int orderId, 
	                                 @RequestParam("status") int newStatus) {
	     orderService.updateOrderStatus(orderId, newStatus);
	     return "redirect:/admin/order"; // Chuyển hướng lại trang danh sách đơn hàng
	 }


}
