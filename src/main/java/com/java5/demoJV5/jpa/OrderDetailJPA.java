package com.java5.demoJV5.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.OrderDetailEntity;

public interface OrderDetailJPA extends JpaRepository<OrderDetailEntity, Integer>{
	
	@Query(value="SELECT * FROM order_details WHERE order_id = ?1", nativeQuery = true)
	List<OrderDetailEntity> findByOrderId(int order_id);
	
	boolean existsById(int id);
	
}
