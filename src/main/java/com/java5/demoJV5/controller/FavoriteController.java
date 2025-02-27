package com.java5.demoJV5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java5.demoJV5.entity.FavoriteEntity;
import com.java5.demoJV5.service.FavoriteService;

@Controller
@RequestMapping("/favorite")
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
    public String addToFavorite(@PathVariable int productId) {
        favoriteService.addToFavorite(productId);
        return "redirect:/favorite";
    }

}


