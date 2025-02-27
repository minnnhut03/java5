package com.java5.demoJV5.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.CartEntity;

public interface CartJPA extends JpaRepository<CartEntity, Integer> {
	@Query(value="SELECT * FROM cart WHERE user_id=?1", nativeQuery = true) 
	public Optional<CartEntity> findByIdUser(int userId);
}
