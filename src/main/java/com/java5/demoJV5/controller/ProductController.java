package com.java5.demoJV5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.java5.demoJV5.entity.ImageEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.ProductSizeEntity;
import com.java5.demoJV5.jpa.ImageJPA;
import com.java5.demoJV5.jpa.ProductSizeJPA;
import com.java5.demoJV5.service.CartService;
import com.java5.demoJV5.service.CategoryService;
import com.java5.demoJV5.service.ProductService;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    ProductService productService;
    
    @Autowired
    ProductSizeJPA productSizeJPA;
    
    @Autowired
    ImageJPA imageJPA;
    
    @Autowired
    CartService cartService;
    
    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String showProducts(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortPrice,
            Model model) {

        List<ProductEntity> products;

        if (categoryId != null) {
            products = productService.getProductsByCategory(categoryId);
        } else {
            products = productService.findAll();
        }

        // Tìm kiếm theo chuỗi (nếu có)
        if (search != null && !search.isEmpty()) {
            products = productService.searchProductsByName(products, search);
        }

        // Sắp xếp theo giá (nếu có)
        products = productService.findAllSortedByPrice(products, sortPrice);

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user/product.html";
    }




    // Lọc sản phẩm theo khoảng giá
//    @GetMapping("/price")
//    public String showProductsByPrice(
//            @RequestParam long minPrice, 
//            @RequestParam long maxPrice, 
//            Model model) {
//        List<ProductEntity> products = productService.getProductsWithPriceInRange(minPrice, maxPrice);
//        model.addAttribute("products", products);
//        return "user/product.html";
//    }

    @GetMapping("/detail")
    public String showProductDetail(@RequestParam("productId") Integer productId, Model model) {
        ProductEntity product = productService.findById(productId);
        if (product == null) {
            return "redirect:/product";
        }
        List<ProductSizeEntity> productSizeEntity = productSizeJPA.findByProductId(productId);
        List<ImageEntity> imageEntities = imageJPA.findAllImageByProductId(productId);
        model.addAttribute("product", product);
        model.addAttribute("productSizeEntity",productSizeEntity);
        model.addAttribute("imageEntities", imageEntities);
        return "user/product-detail";
    }
    @PostMapping("/cart/add")
    public String addDetail(@RequestParam(name = "productId") int productId, 
    		@RequestParam(name = "quantity", defaultValue = "1") int quantity,
    		@RequestParam(name="size") String size,
    		Model model) {
    	System.out.println("aaaaaaaaaaaaaaa"+productId);
    	System.out.println("aaaaaaaaaaaaaaa"+quantity);
    	System.out.println("aaaaaaaaaaaaaaa"+ size);
    	cartService.addToCart(productId, quantity, size);
        return "redirect:/user/cart";
    }
    

}
