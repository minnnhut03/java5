package com.java5.demoJV5.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java5.demoJV5.entity.OrderDetailEntity;
import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.jpa.OrderDetailJPA;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailJPA orderDetailJPA;

    // Get all order details
    public List<OrderDetailEntity> getAllOrderDetails() {
        return orderDetailJPA.findAll();
    }

    // Get order details by order ID
    public List<OrderDetailEntity> getOrderDetailsByOrderId(int orderId) {
        return orderDetailJPA.findByOrderId(orderId);
    }

    // Get order detail by ID
    public OrderDetailEntity getOrderDetailById(int id) {
        return orderDetailJPA.findById(id).orElse(null);
    }

    // Add order detail
    @Transactional
    public String addOrderDetail(OrderEntity order, ProductEntity product, int quantity, String size, long unitPrice) {
        try {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setSize(size);
            orderDetail.setUnitPrice(unitPrice);
            orderDetailJPA.save(orderDetail);
            return "Chi tiết đơn hàng đã được thêm thành công!";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    // Update order detail
    @Transactional
    public String updateOrderDetail(int orderDetailId, int quantity, String size) {
        try {
            OrderDetailEntity existingDetail = orderDetailJPA.findById(orderDetailId).orElse(null);
            if (existingDetail == null) {
                return "Lỗi: Chi tiết đơn hàng không tồn tại!";
            }
            existingDetail.setQuantity(quantity);
            existingDetail.setSize(size);
            orderDetailJPA.save(existingDetail);
            return "Chi tiết đơn hàng đã được cập nhật thành công!";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }

    // Delete order detail
    @Transactional
    public String deleteOrderDetail(int orderDetailId) {
        try {
            if (!orderDetailJPA.existsById(orderDetailId)) {
                return "Lỗi: Chi tiết đơn hàng không tồn tại!";
            }
            orderDetailJPA.deleteById(orderDetailId);
            return "Chi tiết đơn hàng đã được xóa thành công!";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }
}