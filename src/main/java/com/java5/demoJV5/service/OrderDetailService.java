package com.java5.demoJV5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.java5.demoJV5.entity.OrderEntity;
import com.java5.demoJV5.jpa.OrderDetailJPA;

public class OrderDetailService {
	@Autowired
	OrderDetailJPA orderDetailJPA;
	
}
