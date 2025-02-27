package com.java5.demoJV5.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

import com.java5.demoJV5.entity.FavoriteEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface FavoriteJPA extends JpaRepository<FavoriteEntity, Integer> {

    Optional<FavoriteEntity> findByUserAndProduct(UserEntity user, ProductEntity product);

    List<FavoriteEntity> findByUser(UserEntity user);

    void deleteByUserAndProduct(UserEntity user, ProductEntity product);
}
