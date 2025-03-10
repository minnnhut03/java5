package com.java5.demoJV5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.entity.CategoryEntity;
import com.java5.demoJV5.jpa.CategoryJPA;

@Service
public class CategoryService {
    @Autowired
    private CategoryJPA categoryJPA;

    public List<CategoryEntity> getAllCategories() {
        return categoryJPA.findAll();
    }

    public CategoryEntity getCategoryById(int id) {
        return categoryJPA.findById(id).orElse(null);
    }

    public boolean existsByName(String name) {
        return categoryJPA.findByName(name).isPresent();
    }

    public boolean saveCategory(CategoryEntity category) {
        Optional<CategoryEntity> existingCategory = categoryJPA.findByName(category.getName());
        
        // Nếu danh mục đã tồn tại và không phải là danh mục đang cập nhật -> báo lỗi
        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(category.getId())) {
            return false;
        }

        categoryJPA.save(category);
        return true;
    }


    public void deleteCategory(int id) {
        categoryJPA.deleteById(id);
    }
}

