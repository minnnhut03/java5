package com.java5.demoJV5.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.entity.CartDetail;
import com.java5.demoJV5.service.CartService;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @GetMapping("/order_history")
    public String orders(@RequestParam(name = "selectedItems", required = false) String selectedItems, Model model) {
        if (selectedItems != null && !selectedItems.isEmpty()) {
            List<Integer> selectedIds = Arrays.stream(selectedItems.split(","))
                                              .map(Integer::parseInt)
                                              .collect(Collectors.toList());

            List<CartDetail> selectedCartItems = cartService.getCartItemsByIds(selectedIds); 
            model.addAttribute("selectedCartItems", selectedCartItems);
        }
        return "user/order";

    }



    @PostMapping("/order")
    public String order(@RequestParam(name = "selectedItems") String selectedItems, Model model) {
        // Xử lý đơn hàng ở đây, ví dụ: lưu vào database
        return "redirect:/order-success";
    }
}