package com.java5.demoJV5.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.entity.AddressEntity;
import com.java5.demoJV5.entity.CartDetailEntity;
import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.AddressJPA;
import com.java5.demoJV5.jpa.CartDetailJPA;
import com.java5.demoJV5.jpa.UserJPA;
import com.java5.demoJV5.service.OrderDetailService;
import com.java5.demoJV5.service.OrderService;

@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private AddressJPA addressJPA;

    @Autowired
    private CartDetailJPA cartDetailJPA;

}