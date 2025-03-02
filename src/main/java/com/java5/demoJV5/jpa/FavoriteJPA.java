package com.java5.demoJV5.jpa;


import org.springframework.data.jpa.repository.JpaRepository;

import com.java5.demoJV5.entity.FavoriteEntity;
import com.java5.demoJV5.entity.ProductEntity;
import com.java5.demoJV5.entity.UserEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface FavoriteJPA extends JpaRepository<FavoriteEntity, Integer> {

    Optional<FavoriteEntity> findByUserAndProduct(UserEntity user, ProductEntity product);

    List<FavoriteEntity> findByUser(UserEntity user);

    @Transactional
    @Modifying
    @Query("DELETE FROM FavoriteEntity f WHERE f.user = :user AND f.product = :product")
    void deleteByUserAndProduct(@Param("user") UserEntity user, @Param("product") ProductEntity product);
}
