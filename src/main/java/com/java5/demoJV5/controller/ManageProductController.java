package com.java5.demoJV5.controller;

import java.util.List;
import java.util.Optional;

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
	public String adminProductForm(@RequestParam(name="productId") Optional<Integer> id, Model model) {
		ProductBean productBean = new ProductBean();
		if(id.isPresent()) {
			Optional<ProductEntity> productEntity = productJPA.findById(id.get());
			List<ImageEntity> imageEntities = imageJPA.findAllImageByProductId(id.get());
			
			if(productEntity.isPresent()) {
				productBean.setId(id);
				productBean.setName(productEntity.get().getName());
				productBean.setDesc(productEntity.get().getDesc());
				productBean.setPrice(productEntity.get().getPrice());
				productBean.setQuantity(productEntity.get().getQuantity());
				productBean.setCategory(productEntity.get().getCategory().getId());
				productBean.setStatus(productEntity.get().isStatus());
				
				for(ProductSizeEntity size : productEntity.get().getProductSizes()) {
	                switch (size.getSize()) {
	                    case "38": productBean.setSize38(size.getStock()); break;
	                    case "39": productBean.setSize39(size.getStock()); break;
	                    case "40": productBean.setSize40(size.getStock()); break;
	                    case "41": productBean.setSize41(size.getStock()); break;
	                    case "42": productBean.setSize42(size.getStock()); break;
	                    case "43": productBean.setSize43(size.getStock()); break;
	                }
	            }
				model.addAttribute("imageEntities",imageEntities);
			}
		} 
		
		
		
		List<CategoryEntity> categories = categoryJPA.findAll();
		model.addAttribute("category",categories);
		model.addAttribute("productBean", productBean);
		return "admin/form/product_form.html";
	}
	
	
	@PostMapping("/admin/product/add")
	public String adminProductAdd(@Valid @ModelAttribute("productBean") ProductBean productBean,
			Errors errors,
			Model model) 
	{
		if (productJPA.existsByName(productBean.getName()) && !productBean.getId().isPresent()) {
            model.addAttribute("errorName", "Tên sản phẩm đã tồn tại!");
            List<CategoryEntity> categories = categoryJPA.findAll();
			model.addAttribute("category",categories);
            return "admin/form/product_form.html"; 
        }
		
		if(errors.hasErrors()) {
			List<CategoryEntity> categories = categoryJPA.findAll();
			model.addAttribute("category",categories);
			
			return "admin/form/product_form.html";
		}
		if(productBean.getId() != null && productBean.getId().isPresent()) {
			productService.UpdateProduct(productBean);
		} else {
			if(productBean.validateImageFiles() != null) {
				List<CategoryEntity> categories = categoryJPA.findAll();
				model.addAttribute("category",categories);
				model.addAttribute("errorImage", productBean.validateImageFiles());
				return "admin/form/product_form.html";
			}
			productService.insertProduct(productBean);
		}
		
		return "redirect:/admin/product";
	}
	
	
	@GetMapping("/admin/product/size")
	public String adminQuantity(@RequestParam(name="productId") int id, Model model) {
		List<ProductSizeEntity> productSizeEntities = productSizeJPA.findByProductId(id);
		model.addAttribute("productSizeEntities",productSizeEntities);
		return "admin/Manage_quantityDetail.html";
	}
	
	@PostMapping("/admin/product/size/update")
	public String updateProductSize(@RequestParam(name="productId") int id, 
			@RequestParam(name="stock") int stock
	    ) {
	    try {
	    	ProductSizeEntity productSize = productSizeJPA.findById(id).orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm có ID: " + id));
	            
	        productSize.setStock(stock);
	        productSizeJPA.save(productSize);

	        
	        ProductEntity product = productSize.getProduct();
	        int totalStock = productSizeJPA.getTotalStockByProductId(product.getId());
	        product.setQuantity(totalStock);
	        productJPA.save(product);

	        return "redirect:/admin/product/size?productId=" + product.getId();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	    
	}

	
	
	
	@GetMapping("/admin/product/image")
	public String adminImage(@RequestParam(name="productId") int id, Model model) {
		List<ImageEntity> imageEntities = imageJPA.findAllImageByProductId(id);
		model.addAttribute("imageEntities",imageEntities);
		model.addAttribute("productId", id);
		return "admin/manage_image.html";
	}
	@PostMapping("/admin/deleteImage")
	public String deleteImage(@RequestParam(name="imageId") int id ,
			@RequestParam(name="productId") int productiId) {
		boolean delete = imageService.deleteImage(id);
		return "redirect:/admin/product/image?productId=" + productiId;
	}
}
