package com.java5.demoJV5.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.ImageEntity;


public interface ImageJPA extends JpaRepository<ImageEntity, Integer> {
	@Query(value="SELECT * FROM images WHERE product_id = ?1", nativeQuery = true)
	public List<ImageEntity> findAllImageByProductId(int id);
	
	@Query(value="DELETE FROM images WHERE product_id = ?1", nativeQuery = true)
	public boolean deleteByProductID(int id);
}
