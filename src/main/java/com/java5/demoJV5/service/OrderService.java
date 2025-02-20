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
	
	public List<OrderEntity> getAllOrder(){
		return orderJPA.findAll();
	}
}
