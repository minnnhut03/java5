package com.java5.demoJV5.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.entity.CartDetailEntity;
import com.java5.demoJV5.entity.CartEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.CartDetailJPA;
import com.java5.demoJV5.jpa.CartJPA;
import com.java5.demoJV5.jpa.ProductJPA;
import com.java5.demoJV5.jpa.UserJPA;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CartService {
    @Autowired
    HttpServletRequest request;

    @Autowired
    CartJPA cartJPA;

    @Autowired
    UserJPA userJPA;

    @Autowired
    CartDetailJPA cartDetailJPA;

    @Autowired
    ProductJPA productJPA;

    public boolean clearCart() {
        try {
            CartEntity cartEntity = this.getCart();
            if (cartEntity == null) {
                return false;
            }
            cartDetailJPA.clearByCartId(cartEntity.getId());  // Đổi từ getUser().getId() thành getId()
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private UserEntity getUserEntity(){
    	Cookie[] cookies = request.getCookies();

        if(cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("id")){
                Optional<UserEntity> userOptional = userJPA.findById(Integer.parseInt(cookie.getValue()));
                return userOptional.orElse(null);
            }
        }
        return null;
    }

    private CartEntity getCart() {
        try {
            UserEntity userEntity = this.getUserEntity();
            if (userEntity == null) {
                return null;
            }
            Optional<CartEntity> optionalCart = cartJPA.findByIdUser(userEntity.getId());
            if (optionalCart.isPresent()) {
                return optionalCart.get();
            }
            CartEntity cartEntity = new CartEntity();
            cartEntity.setUser(userEntity);
            
            CartEntity savedCart = cartJPA.save(cartEntity);
            return savedCart;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CartDetailEntity> getCartItemsByIds(List<Integer> cartDetailIds) {
        return cartDetailJPA.findAllById(cartDetailIds);
    }
    public List<CartDetailEntity> getCartItem(){
        CartEntity cartEntity = this.getCart();
        return cartEntity != null ? cartEntity.getCartDetails() : new ArrayList<>();
    }
    


    public boolean addToCart(int prodId, int quantity, String size) {
        try {
            CartEntity cartEntity = this.getCart();
            if (cartEntity == null) {
                return false;
            }

            Optional<CartDetailEntity> cartItemOptional = cartDetailJPA.findByCartIdAndProdIdAndSize(cartEntity.getId(), prodId, size);

            if (cartItemOptional.isPresent()) {
            	Optional<ProductEntity> productOptional = productJPA.findById(prodId);
            	
                CartDetailEntity cartDetail = cartItemOptional.get();
                cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
                cartDetail.setUnitPrice((cartDetail.getQuantity() + quantity)*productOptional.get().getPrice());
                cartDetailJPA.save(cartDetail);
            } else {
                Optional<ProductEntity> productOptional = productJPA.findById(prodId);
                if (productOptional.isEmpty()) {
                    return false;
                }
                CartDetailEntity cartItemEntity = new CartDetailEntity();
                cartItemEntity.setQuantity(quantity);
                cartItemEntity.setCart(cartEntity);
                cartItemEntity.setSize(size);
                cartItemEntity.setProduct(productOptional.get());
                cartItemEntity.setUnitPrice(quantity*productOptional.get().getPrice());
                
                cartDetailJPA.save(cartItemEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean deleteCartItem(int cartItemId){
        try{
            CartEntity cartEntity = this.getCart();
            Optional<CartDetailEntity> cartItemOptional = cartDetailJPA.findByCartIdAndId(cartEntity.getId(), cartItemId);

            if(cartItemOptional.isPresent()){
                cartDetailJPA.deleteById(cartItemId);
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateQuantityCartItem(int cartItemId, int quantity, int prodId){
        try{
            Optional<CartDetailEntity> cartItemOptional = cartDetailJPA.findById(cartItemId);
            Optional<ProductEntity> productOptional = productJPA.findById(prodId);
            if(cartItemOptional.isPresent()){
                CartDetailEntity cartItemEntity = cartItemOptional.get();
                cartItemEntity.setQuantity(quantity);
                cartItemEntity.setUnitPrice(quantity*productOptional.get().getPrice());
                cartDetailJPA.save(cartItemEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    
}
