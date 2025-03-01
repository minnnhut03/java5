package com.java5.demoJV5.controller;

import com.java5.demoJV5.bean.CategoryBean;
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
    private CategoryService categoryService;

    @GetMapping("")
    public String listCategories(Model model) {
        List<CategoryEntity> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("categoryBean", new CategoryBean()); // Thêm object để binding form
        return "admin/manage_category";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        CategoryEntity category = categoryService.getCategoryById(id);
        if (category == null) {
            return "redirect:/admin/category";
        }

        // Chuyển đổi từ CategoryEntity sang CategoryBean
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setId(category.getId());
        categoryBean.setName(category.getName());
        categoryBean.setStatus(category.getStatus());

        model.addAttribute("categoryBean", categoryBean);
        model.addAttribute("isEditing", true);
        return "admin/manage_category"; 
    }

    @PostMapping("/insert")
    public String insertCategory(@ModelAttribute("categoryBean") @Valid CategoryBean categoryBean, 
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Dữ liệu nhập không hợp lệ!");
            return listCategories(model);
        }

        String saveResult = categoryService.saveCategory(categoryBean);
        if (!saveResult.equals("Lưu danh mục thành công!")) {
            model.addAttribute("error", saveResult);
            return listCategories(model);
        }

        return "redirect:/admin/category";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute("categoryBean") @Valid CategoryBean categoryBean, 
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Dữ liệu nhập không hợp lệ!");
            return listCategories(model);
        }

        String saveResult = categoryService.saveCategory(categoryBean);
        if (!saveResult.equals("Lưu danh mục thành công!")) {
            model.addAttribute("error", saveResult);
            return listCategories(model);
        }

        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/category";
    }
}
