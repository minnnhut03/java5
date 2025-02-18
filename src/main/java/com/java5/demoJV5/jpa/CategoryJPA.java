package com.java5.demoJV5.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java5.demoJV5.entity.CategoryEntity;

public interface CategoryJPA extends JpaRepository<CategoryEntity, Integer> {

}
