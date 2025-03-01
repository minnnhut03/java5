package com.java5.demoJV5.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	

//    public List<ProductEntity> getProductsWithPriceInRange(BigDecimal minPrice, BigDecimal maxPrice) {
//        return productJPA.getProductsWithPriceInRange(minPrice, maxPrice);
//    }
	
    public List<ProductEntity> findAll() {
        return productJPA.findAll();
    }

    public ProductEntity findById(Integer id) {
        return productJPA.findById(id).orElse(null);
    }

    public List<ProductEntity> getProductsByCategory(Integer categoryId) {
        return productJPA.findByCategoryId(categoryId);
    }
    
    public List<ProductEntity> searchProductsByName(String name) {
        return productJPA.findByNameContainingIgnoreCase(name);
    }
    
    public List<ProductEntity> findAllSortedByPrice(List<ProductEntity> products, String sortOrder) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        if ("asc".equalsIgnoreCase(sortOrder)) {
            products.sort(Comparator.comparing(ProductEntity::getPrice));
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            products.sort(Comparator.comparing(ProductEntity::getPrice).reversed());
        }

        return products;
    }
    
    public List<ProductEntity> searchProductsByName(List<ProductEntity> products, String search) {
        return products.stream()
                .filter(product -> product.getName().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());
    }
	
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
				product.setStatus(productBean.getStatus());
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
	public String UpdateProduct(ProductBean productBean) {
		try {
			List<String> fileNames = imageService.saveImages(productBean.getImages());
			Optional<CategoryEntity> category = categoryJPA.findById(productBean.getCategory());
			
			ProductEntity product = new ProductEntity();
			product.setId(productBean.getId().get());
			product.setName(productBean.getName());
			product.setDesc(productBean.getDesc());
			product.setPrice(productBean.getPrice());
			product.setQuantity(productBean.getQuantity());
			product.setCategory(category.get());
			product.setStatus(productBean.getStatus());
			productJPA.save(product);
			
			try {				
				if(fileNames.size() >= 1) {
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
					imageJPA.deleteByProductID(product.getId()); 
				}
		    	
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
			
			List<ImageEntity> images = new ArrayList<ImageEntity>();
			for(String fileName : fileNames) {
				ImageEntity image = new ImageEntity();
				image.setName(fileName);
				image.setProduct(product);
				images.add(image);
			}
			
			imageJPA.saveAll(images); 
			
			
			
			try {
	            productSizeJPA.deleteByProductId(product.getId()); 
	            System.out.println("Đã xóa size cũ thành công!");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
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
}
