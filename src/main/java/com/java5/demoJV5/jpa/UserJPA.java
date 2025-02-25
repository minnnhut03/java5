package com.java5.demoJV5.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.java5.demoJV5.entity.UserEntity;

@Repository
public interface UserJPA  extends JpaRepository<UserEntity, Integer>{
	
	Optional<UserEntity> findByEmail(String email);
    
    // Kiểm tra email đã tồn tại hay chưa
    boolean existsByEmail(String email);
    
    Optional<UserEntity> findByResetToken(String resetToken);
}

