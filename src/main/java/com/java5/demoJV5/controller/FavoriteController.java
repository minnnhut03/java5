package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

import com.java5.demoJV5.entity.FavoriteEntity;
import com.java5.demoJV5.service.FavoriteService;

@Controller
@RequestMapping("/user/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // Hiển thị danh sách yêu thích
    @GetMapping
    public String favoriteList(Model model) {
        List<FavoriteEntity> favorites = favoriteService.getUserFavorites();
        System.out.println("Danh sách sản phẩm yêu thích: " + favorites); // Debug
        model.addAttribute("favorites", favorites);
        return "user/favorite";
    }


    @GetMapping("/add/{productId}")
    @ResponseBody
    public Map<String, Object> addToFavorite(@PathVariable int productId) {
        boolean success = favoriteService.addToFavorite(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Đã thêm vào yêu thích" : "Lỗi thêm");
        return response;
    }

    @GetMapping("/remove/{productId}")
    @ResponseBody
    public Map<String, Object> removeFromFavorite(@PathVariable int productId) {
        boolean success = favoriteService.removeFromFavorite(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Đã xóa khỏi yêu thích" : "Lỗi xóa");
        return response;
    }

}


