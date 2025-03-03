package com.java5.demoJV5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.java5.demoJV5.jpa.OrderJPA;
import com.java5.demoJV5.service.OrderDetailService;
import com.java5.demoJV5.service.OrderService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderHistoryController {

    @Autowired
    private OrderJPA orderJPA;
    
	@Autowired
	OrderService orderService;
    
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/user/order/history")
    public String orderHistory(Model model) {
        Integer userId = getUserIdFromCookie();
        if (userId != null) {
            model.addAttribute("MyOrder", orderJPA.findByUserId(userId));
        } else {
            model.addAttribute("MyOrder", null);
        }
        return "user/order_history";
    }
    
    
    @GetMapping("/user/order/details/{orderId}")
    public String getOrderDetails(@PathVariable("orderId") int orderId, Model model) {
        var order = orderJPA.findById(orderId).orElse(null);
        var orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);

        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("orderDetails", orderDetails);
            return "user/order_history_detail"; // Trả về trang mới
        } else {
            return "redirect:/user/order/history"; // Nếu không tìm thấy đơn hàng, quay về trang lịch sử
        }
    }

    @PostMapping("/user/order/update-status/{id}")
    public String updateOrderStatus(@PathVariable("id") int orderId, 
                                    @RequestParam("status") int newStatus) {
        orderService.updateOrderStatus(orderId, newStatus);
        return "redirect:/user/order/history"; // Chuyển hướng lại trang danh sách đơn hàng của user
    }


    private Integer getUserIdFromCookie() {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("id".equals(cookie.getName())) {
                try {
                    return Integer.valueOf(cookie.getValue());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
