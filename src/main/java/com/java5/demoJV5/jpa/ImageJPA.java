package com.java5.demoJV5.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.ImageEntity;

public interface ImageJPA extends JpaRepository<ImageEntity, Integer> {
	
}
