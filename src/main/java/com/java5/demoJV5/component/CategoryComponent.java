package com.java5.demoJV5.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java5.demoJV5.entity.CategoryEntity;
import com.java5.demoJV5.service.CategoryService;


@ControllerAdvice
public class CategoryComponent {

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories") // Tự động thêm vào model cho mọi view
    public List<CategoryEntity> getCategories() {
        return categoryService.getAllCategories();
    }
}
