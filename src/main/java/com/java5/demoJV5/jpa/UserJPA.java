package com.java5.demoJV5.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.java5.demoJV5.entity.UserEntity;

@Repository
public interface UserJPA  extends JpaRepository<UserEntity, Integer>{
	
	Optional<UserEntity> findByEmail(String email);
    
    // Kiểm tra email đã tồn tại hay chưa
    boolean existsByEmail(String email);
}

