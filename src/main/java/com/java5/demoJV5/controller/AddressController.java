package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.AddressBean;
import com.java5.demoJV5.entity.AddressEntity;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.service.AddressService;
import com.java5.demoJV5.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    UserService userService;

    // ✅ Hàm lấy userId từ cookie
    private Integer getUserIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        return null;
    }

    // ✅ 1. Xem danh sách địa chỉ của mình
    @GetMapping("")
    public String listAddresses(HttpServletRequest request, Model model) {
        Integer userId = getUserIdFromCookie(request);
        if (userId == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        }
        List<AddressEntity> addresses = addressService.getAddressesByUserId(userId);
        model.addAttribute("addresses", addresses);
        return "user/address_list"; // Trang hiển thị danh sách địa chỉ
    }

    // ✅ 2. Hiển thị form thêm địa chỉ
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("address", new AddressBean());
        return "user/address_form";
    }

    // ✅ 3. Xử lý thêm địa chỉ mới
    @PostMapping("/add")
    public String addAddress(@ModelAttribute("address") @Valid AddressBean addressBean, 
                             BindingResult result, 
                             HttpServletRequest request, 
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("address", addressBean);
            return "user/address_form"; 
        }

        Integer userId = getUserIdFromCookie(request);
        if (userId == null) {
            return "redirect:/login";
        }

        Optional<UserEntity> user = userService.getUserEntityById(userId);
        if (user.isPresent()) {
            AddressEntity address = new AddressEntity();
            address.setCustomerName(addressBean.getCustomerName());
            address.setPhoneNumber(addressBean.getPhoneNumber());
            address.setAddress(addressBean.getAddress());
            address.setUser(user.get());
            addressService.saveOrUpdateAddress(address);
        }
        return "redirect:/user/address";
    }




    // ✅ 4. Hiển thị form chỉnh sửa địa chỉ
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, HttpServletRequest request) {
        Integer userId = getUserIdFromCookie(request);
        Optional<AddressEntity> address = addressService.getAddressById(id);
        if (address.isPresent() && address.get().getUser().getId().equals(userId)) {
            model.addAttribute("address", address.get());
            return "user/address_form";
        }
        return "redirect:/user/address";
    }

    @PostMapping("/update/{id}")
    public String updateAddress(@PathVariable Integer id, 
                                @ModelAttribute("address") @Valid AddressBean addressBean,
                                BindingResult result, 
                                HttpServletRequest request, 
                                Model model) {
        Integer userId = getUserIdFromCookie(request);
        if (userId == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        }

        Optional<AddressEntity> existingAddress = addressService.getAddressById(id);
        if (existingAddress.isEmpty() || !existingAddress.get().getUser().getId().equals(userId)) {
            return "redirect:/user/address"; // Nếu không tìm thấy địa chỉ hoặc không thuộc về user, chuyển hướng
        }

        if (result.hasErrors()) {
            model.addAttribute("address", addressBean);
            return "user/address_form"; // Trả lại form nếu có lỗi
        }

        AddressEntity address = existingAddress.get();
        address.setCustomerName(addressBean.getCustomerName());
        address.setPhoneNumber(addressBean.getPhoneNumber());
        address.setAddress(addressBean.getAddress());
        addressService.saveOrUpdateAddress(address);

        return "redirect:/user/address";
    }


    // ✅ 6. Xử lý xóa địa chỉ
    @PostMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Integer id, HttpServletRequest request) {
        Integer userId = getUserIdFromCookie(request);
        Optional<AddressEntity> address = addressService.getAddressById(id);
        if (address.isPresent() && address.get().getUser().getId().equals(userId)) {
            addressService.deleteAddress(id);
        }
        return "redirect:/user/address";
    }
}
