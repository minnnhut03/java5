package com.java5.demoJV5.controller;

import com.java5.demoJV5.entity.CategoryEntity;
import com.java5.demoJV5.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class ManageCategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String listCategories(Model model) {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/manage_category";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        CategoryEntity category = categoryService.getCategoryById(id);
        if (category == null) {
            return "redirect:/admin/category";
        }
        model.addAttribute("category", category);
        model.addAttribute("isEditing", true);
        return "admin/manage_category"; 
    }


    @PostMapping("/insert")
    public String insertCategory(@ModelAttribute("category") @Valid CategoryEntity category, 
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Dữ liệu nhập không hợp lệ!");
            return "admin/manage_category";
        }

        if (!categoryService.saveCategory(category)) {
            model.addAttribute("error", "Tên danh mục đã tồn tại!");
            return listCategories(model);
        }

        return "redirect:/admin/category";
    }



    @PostMapping("/update")
    public String updateCategory(@ModelAttribute CategoryEntity category, Model model) {
        if (!categoryService.saveCategory(category)) {
            model.addAttribute("error", "Tên danh mục đã tồn tại!");
            return listCategories(model);
        }
        return "redirect:/admin/category";
    }
}
