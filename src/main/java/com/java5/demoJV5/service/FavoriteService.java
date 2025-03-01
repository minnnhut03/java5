package com.java5.demoJV5.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.entity.FavoriteEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.FavoriteJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.jpa.UserJPA;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteJPA favoriteJPA;

    @Autowired
    private UserJPA userJPA;

    @Autowired
    private ProductJPA productJPA;

    @Autowired
    private HttpServletRequest request;

    // Lấy User từ Cookie
    private UserEntity getUserEntity() {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                Optional<UserEntity> userOptional = userJPA.findById(Integer.parseInt(cookie.getValue()));
                return userOptional.orElse(null);
            }
        }
        return null;
    }

    // Lấy danh sách yêu thích của user
    public List<FavoriteEntity> getUserFavorites() {
    	UserEntity user = getUserEntity();
        return (user != null) ? favoriteJPA.findByUser(user) : null;
    }

    // Thêm sản phẩm vào yêu thích
    public boolean addToFavorite(int productId) {
        try {
        	UserEntity user = getUserEntity();
            Optional<ProductEntity> productOptional = productJPA.findById(productId);

            if (user == null || productOptional.isEmpty()) {
                return false;
            }

            ProductEntity product = productOptional.get();
            Optional<FavoriteEntity> existingFavorite = favoriteJPA.findByUserAndProduct(user, product);

            if (existingFavorite.isEmpty()) {
                FavoriteEntity favorite = new FavoriteEntity();
                favorite.setUser(user);
                favorite.setProduct(product);
                favoriteJPA.save(favorite);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa sản phẩm khỏi yêu thích
    public boolean removeFromFavorite(int productId) {
        try {
        	UserEntity user = getUserEntity();
            Optional<ProductEntity> productOptional = productJPA.findById(productId);

            if (user == null || productOptional.isEmpty()) {
                return false;
            }

            ProductEntity product = productOptional.get();
            favoriteJPA.deleteByUserAndProduct(user, product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

