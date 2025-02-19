package com.java5.demoJV5.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.ProductSizeEntity;

public interface ProductSizeJPA extends JpaRepository<ProductSizeEntity, Integer> {
	
	@Query(value="SELECT * FROM product_sizes WHERE product_id = ?1", nativeQuery = true)
	public List<ProductSizeEntity> findByProductId(int id);
	
}
