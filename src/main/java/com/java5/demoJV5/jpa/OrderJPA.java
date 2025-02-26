package com.java5.demoJV5.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java5.demoJV5.entity.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity, Integer>{
	
	boolean existsById(int id);

}
