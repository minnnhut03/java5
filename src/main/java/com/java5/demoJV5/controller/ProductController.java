package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.java5.demoJV5.bean.ProductBean;
import com.java5.demoJV5.entity.CategoryEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.jpa.CategoryJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	ProductService productService;
	
	
	@GetMapping("/admin/product")
	public String adminProduct(Model model) {
		List<ProductEntity> products = productJPA.findAll();
		model.addAttribute("products", products);
		return "admin/manage_product.html";
	}
	@GetMapping("/admin/product/form")
	public String adminProductForm(Model model) {
		List<CategoryEntity> categories = categoryJPA.findAll();
		model.addAttribute("category",categories);
		model.addAttribute("productBean", new ProductBean());
		return "admin/form/add_product.html";
	}
	
	
	@PostMapping("/admin/product/add")
	public String adminProductAdd(@Valid @ModelAttribute("productBean") ProductBean productBean,
			Errors errors,
			Model model) 
	{
		
		if(errors.hasErrors() || productBean.validateImageFiles() != null) {
			List<CategoryEntity> categories = categoryJPA.findAll();
			model.addAttribute("category",categories);
			model.addAttribute("errorImage", productBean.validateImageFiles());
			return "admin/form/add_product.html";
		}
		productService.insertProduct(productBean);
		return "redirect:/admin/product";
	}
	
	
	@GetMapping("/admin/product/quantity")
	public String adminQuantity() {
		return "admin/Manage_quantityDetail.html";
	}

	@GetMapping("/admin/product/image")
	public String adminImage() {
		return "admin/manage_image.html";
	}
}
