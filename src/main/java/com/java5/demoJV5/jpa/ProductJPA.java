package com.java5.demoJV5.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import com.java5.demoJV5.entity.ProductEntity;

public interface ProductJPA extends JpaRepository<ProductEntity, Integer> {
    
    boolean existsByName(String name);
    
    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường) -> Dùng Spring Data JPA
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
    
    // Lọc sản phẩm theo khoảng giá
    List<ProductEntity> findByPriceBetweenOrderByPriceAsc(long minPrice, long maxPrice);

    // Tìm sản phẩm theo danh mục
    List<ProductEntity> findByCategoryId(Integer categoryId);
}

