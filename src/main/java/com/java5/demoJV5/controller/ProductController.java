package com.java5.demoJV5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.service.CategoryService;
import com.java5.demoJV5.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;
    
    @Autowired
    CategoryService categoryService;

    // Hiển thị danh sách sản phẩm + lọc theo danh mục + tìm kiếm theo tên
    @GetMapping("")
    public String showProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String search,
            Model model) {
        
        List<ProductEntity> products;
        
        if (categoryId != null) {
            products = productService.getProductsByCategory(categoryId);
        } else if (search != null && !search.isEmpty()) {
            products = productService.searchProductsByName(search);
        } else {
            products = productService.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user/product.html";
    }

    // Lọc sản phẩm theo khoảng giá
    @GetMapping("/price")
    public String showProductsByPrice(
            @RequestParam long minPrice, 
            @RequestParam long maxPrice, 
            Model model) {
        List<ProductEntity> products = productService.getProductsWithPriceInRange(minPrice, maxPrice);
        model.addAttribute("products", products);
        return "user/product.html";
    }

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer productId, Model model) {
        ProductEntity product = productService.findById(productId);
        if (product == null) {
            return "redirect:/product";
        }
        model.addAttribute("product", product);
        return "user/product-detail";
    }

}
