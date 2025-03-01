package com.java5.demoJV5.component;

import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.FavoriteJPA;
import com.java5.demoJV5.jpa.UserJPA;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;

@Component
public class FavoriteComponent {

    private final FavoriteJPA favoriteJPA;
    private final UserJPA userJPA;

    public FavoriteComponent(FavoriteJPA favoriteJPA, UserJPA userJPA) {
        this.favoriteJPA = favoriteJPA;
        this.userJPA = userJPA;
    }

    public void addFavoriteAttributes(Model model, HttpServletRequest request) {
        String userId = getUserIdFromCookies(request);

        if (userId != null) {
            Optional<UserEntity> userOpt = userJPA.findById(Integer.parseInt(userId));
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();

                // Lấy danh sách sản phẩm yêu thích của user
                List<Integer> favoriteProductIds = favoriteJPA.findByUser(user)
                        .stream()
                        .map(fav -> fav.getProduct().getId())
                        .toList();

                model.addAttribute("favoriteProductIds", favoriteProductIds);
                return;
            }
        }

        model.addAttribute("favoriteProductIds", List.of()); // Trả về danh sách rỗng nếu chưa đăng nhập
    }

    private String getUserIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
