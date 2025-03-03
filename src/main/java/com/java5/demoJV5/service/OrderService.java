package com.java5.demoJV5.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.bean.OrderDetailRequest;
import com.java5.demoJV5.bean.OrderRequest;
import com.java5.demoJV5.entity.AddressEntity;
import com.java5.demoJV5.entity.OrderDetailEntity;
import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.AddressJPA;
import com.java5.demoJV5.jpa.CartDetailJPA;
import com.java5.demoJV5.jpa.OrderDetailJPA;
import com.java5.demoJV5.jpa.OrderJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.jpa.UserJPA;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    OrderJPA orderJPA;
    
    @Autowired
    CartDetailJPA cartDetailJPA;
    

    @Autowired
    private OrderDetailJPA orderDetailRepository;

    @Autowired
    private ProductJPA productRepository;

    @Autowired
    private UserJPA userRepository;

    @Autowired
    private AddressJPA addressRepository;

    public List<OrderEntity> getAllOrder() {
        return orderJPA.findAll();
    }

    public OrderEntity getOrderById(int id) {
        return orderJPA.findById(id).orElse(null);
    }
    
    public OrderEntity saveOrder(OrderEntity order) {
        return orderJPA.save(order);
    }
    
    @Transactional
    public void deleteCartItems(List<Integer> cartDetailIds) {
        cartDetailJPA.deleteAllById(cartDetailIds);
    }
    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        // Lấy thông tin user
        UserEntity user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Lấy thông tin địa chỉ
        AddressEntity address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Tạo đơn hàng mới
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(0); // 0: Chờ xác nhận
        order.setDateCreated(LocalDateTime.now());

        List<OrderDetailEntity> orderDetails = new ArrayList<>();
        long totalAmount = 0;

        // Duyệt qua danh sách sản phẩm trong order request
        for (OrderDetailRequest detail : orderRequest.getOrderDetails()) {
            ProductEntity product = productRepository.findById(detail.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Tạo chi tiết đơn hàng
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detail.getQuantity());
            orderDetail.setSize(detail.getSize());
            orderDetail.setUnitPrice(detail.getUnitPrice());

            // Tính tổng tiền
            totalAmount += detail.getUnitPrice() * detail.getQuantity();
            orderDetails.add(orderDetail);
        }

        // Cập nhật tổng tiền cho đơn hàng
        order.setTotalAmount(totalAmount);
        order.setOrderDetails(orderDetails);

        // Lưu đơn hàng và chi tiết đơn hàng
        orderJPA.save(order);
        orderDetailRepository.saveAll(orderDetails);
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

