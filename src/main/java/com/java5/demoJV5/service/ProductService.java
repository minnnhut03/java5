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
import com.java5.demoJV5.entity.ProductSizeEntity;
import com.java5.demoJV5.jpa.CategoryJPA;
import com.java5.demoJV5.jpa.ImageJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.jpa.ProductSizeJPA;


@Service
public class ProductService {
	@Autowired
	ProductJPA productJPA;
	
	@Autowired
	ProductSizeJPA productSizeJPA;
	
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
			
			List<ProductSizeEntity> productSizes = new ArrayList<ProductSizeEntity>();
			AddProductSize(productSizes, product, "38", productBean.getSize38());
			AddProductSize(productSizes, product, "39", productBean.getSize39());
			AddProductSize(productSizes, product, "40", productBean.getSize40());
			AddProductSize(productSizes, product, "41", productBean.getSize41());
			AddProductSize(productSizes, product, "42", productBean.getSize42());
			AddProductSize(productSizes, product, "43", productBean.getSize43());
			productSizeJPA.saveAll(productSizes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String AddProductSize(List<ProductSizeEntity> productSizes, 
			ProductEntity productEntity, String size, int stock) {
		ProductSizeEntity productSizeEntity = new ProductSizeEntity();
		productSizeEntity.setProduct(productEntity);
		productSizeEntity.setSize(size);
		productSizeEntity.setStock(stock);
		productSizes.add(productSizeEntity);
		
		return null;
	}
}
