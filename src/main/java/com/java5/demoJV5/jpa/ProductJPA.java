package com.java5.demoJV5.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java5.demoJV5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer> {

}
