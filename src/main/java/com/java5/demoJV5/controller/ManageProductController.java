package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java5.demoJV5.bean.ProductBean;
import com.java5.demoJV5.entity.CategoryEntity;
import com.java5.demoJV5.entity.ImageEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.ProductSizeEntity;
import com.java5.demoJV5.jpa.CategoryJPA;
import com.java5.demoJV5.jpa.ImageJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.jpa.ProductSizeJPA;
import com.java5.demoJV5.service.ImageService;
import com.java5.demoJV5.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ManageProductController {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ImageJPA imageJPA;
	
	@Autowired
	ImageService imageService;
	
	@Autowired
	ProductSizeJPA productSizeJPA;
	
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
		return "admin/form/product_form.html";
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
			return "admin/form/product_form.html";
		}
		productService.insertProduct(productBean);
		return "redirect:/admin/product";
	}
	
	
	@GetMapping("/admin/product/size")
	public String adminQuantity(@RequestParam(name="productId") int id, Model model) {
		List<ProductSizeEntity> productSizeEntities = productSizeJPA.findByProductId(id);
		model.addAttribute("productSizeEntities",productSizeEntities);
		return "admin/Manage_quantityDetail.html";
	}
	
	
	
	@GetMapping("/admin/product/image")
	public String adminImage(@RequestParam(name="productId") int id, Model model) {
		List<ImageEntity> imageEntities = imageJPA.findAllImageByProductId(id);
		model.addAttribute("imageEntities",imageEntities);
		return "admin/manage_image.html";
	}
	@PostMapping("/admin/deleteImage")
	public String deleteImage(@RequestParam(name="imageId") int id ,
			@RequestParam(name="productId") int productiId) {
		boolean delete = imageService.deleteImage(id);
		return "redirect:/admin/product/image?productId=" + productiId;
	}
}
