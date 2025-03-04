package com.java5.demoJV5.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.java5.demoJV5.entity.AddressEntity;
import com.java5.demoJV5.entity.CartDetail;
import com.java5.demoJV5.service.AddressService;
import com.java5.demoJV5.service.CartService;

@Controller
@RequestMapping("/user/order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;

    @GetMapping("")
    public String orders(@RequestParam(name = "selectedItems", required = false) String selectedItems, 
                         Model model, HttpServletRequest request) {
        // ✅ Lấy userId từ cookie
        Integer userId = getUserIdFromCookies(request);

        if (userId != null) {
            // ✅ Lấy danh sách địa chỉ từ database dựa vào userId
            List<AddressEntity> addressList = addressService.getAddressesByUserId(userId);
            model.addAttribute("addressList", addressList);
        }

        return "user/order"; // Đảm bảo return hợp lệ
    }

    @GetMapping("/order_history")
    public String orderHistory(@RequestParam(name = "selectedItems", required = false) String selectedItems, Model model) {
        List<CartDetail> selectedCartItems = new ArrayList<>(); // Khởi tạo danh sách rỗng để tránh lỗi

        if (selectedItems != null && !selectedItems.isEmpty()) {
            try {
                List<Integer> selectedIds = Arrays.stream(selectedItems.split(","))
                                                  .map(Integer::parseInt)
                                                  .collect(Collectors.toList());

                List<CartDetail> cartItems = cartService.getCartItemsByIds(selectedIds);
                selectedCartItems = cartItems != null ? cartItems : new ArrayList<>();

                // ✅ Tính tổng tiền
                double totalPrice = selectedCartItems.stream()
                    .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                    .sum();

                // ✅ Đưa vào model để Thymeleaf sử dụng
                model.addAttribute("selectedCartItems", selectedCartItems);
                model.addAttribute("totalPrice", totalPrice);

            } catch (NumberFormatException e) {
                System.err.println("Lỗi khi chuyển đổi selectedItems: " + e.getMessage());
            }
        }

        return "user/order_history"; // Đổi tên view cho đúng nếu cần
    }

    // ✅ Hàm lấy userId từ cookie (có xử lý lỗi)
    private Integer getUserIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    try {
                        return Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi khi chuyển đổi cookie id: " + e.getMessage());
                        return null;
                    }
                }
            }
        }
        return null;
    }

    @PostMapping("/order")
    public String order(@RequestParam(name = "selectedItems", required = false) String selectedItems, Model model) {
        // Xử lý đơn hàng ở đây, ví dụ: lưu vào database
        return "redirect:/user/order-success"; // Đổi đường dẫn cho đúng
    }

    // ✅ Route xử lý trang thành công
    @GetMapping("/order-success")
    public String orderSuccess() {
        return "user/order_success";
    }
}
