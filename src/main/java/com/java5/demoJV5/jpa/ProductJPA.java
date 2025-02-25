package com.java5.demoJV5.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer> {
	 boolean existsByName(String name);
	 
}
