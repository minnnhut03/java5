package com.java5.demoJV5.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.bean.ProductBean;
import com.java5.demoJV5.entity.CategoryEntity;
import com.java5.demoJV5.entity.ImageEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.jpa.CategoryJPA;
import com.java5.demoJV5.jpa.ImageJPA;
import com.java5.demoJV5.jpa.ProductJPA;


@Service
public class ProductService {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	CategoryJPA categoryJPA;
	
	@Autowired
	ImageJPA imageJPA;
	
	@Autowired
	ImageService imageService;
	
	public String insertProduct(ProductBean productBean) {
		try {
			List<String> fileNames = imageService.saveImages(productBean.getImages());
			Optional<CategoryEntity> category = categoryJPA.findById(productBean.getCategory());
			
			ProductEntity product = new ProductEntity();
			product.setName(productBean.getName());
			product.setDesc(productBean.getDesc());
			product.setPrice(productBean.getPrice());
			product.setQuantity(productBean.getQuantity());
			product.setCategory(category.get());
			productJPA.save(product);
			
			List<ImageEntity> images = new ArrayList<ImageEntity>();
			for(String fileName : fileNames) {
				ImageEntity image = new ImageEntity();
				image.setName(fileName);
				image.setProduct(product);
				images.add(image);	
			}
			imageJPA.saveAll(images);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
