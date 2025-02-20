package com.java5.demoJV5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.jpa.OrderJPA;

@Service
public class OrderService {
    @Autowired
    OrderJPA orderJPA;

    public List<OrderEntity> getAllOrder() {
        return orderJPA.findAll();
    }

    public OrderEntity getOrderById(int id) {
        return orderJPA.findById(id).orElse(null);
    }

    public String updateOrderStatus(int orderId, int newStatus) {
        try {
            OrderEntity existingOrder = orderJPA.findById(orderId).orElse(null);
            if (existingOrder == null) {
                return "Lỗi: Đơn hàng không tồn tại!";
            }

            existingOrder.setStatus(newStatus);
            orderJPA.save(existingOrder);

            return "Trạng thái đơn hàng được cập nhật thành công!";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }
}

