package com.java5.demoJV5.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java5.demoJV5.bean.UserBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;

@Service
public class UserService {

    @Autowired
    private UserJPA userJPA;

    public List<UserBean> getAllUsers() {
        return userJPA.findAll().stream().map(this::convertToBean).collect(Collectors.toList());
    }

    public UserBean getUserById(int userId) {
        return userJPA.findById(userId).map(this::convertToBean).orElse(null);
    }

    public String saveUser(UserBean userBean) {
        try {

            if (userJPA.existsByEmail(userBean.getEmail())) {
                return "Lỗi: Email đã tồn tại!";
            }

            UserEntity userEntity = convertToEntity(userBean);
            userJPA.save(userEntity);
            return "User added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String updateUser(UserBean userBean) {
        try {
            UserEntity existingUser = userJPA.findById(userBean.getUserId()).orElse(null);
            if (existingUser == null) {
                return "Lỗi: Người dùng không tồn tại!";
            }

            if (!existingUser.getEmail().equals(userBean.getEmail()) && userJPA.existsByEmail(userBean.getEmail())) {
                return "Lỗi: Email đã tồn tại!";
            }

            UserEntity userEntity = convertToEntity(userBean);
            if (userBean.getPassword() == null || userBean.getPassword().isEmpty()) {
                userEntity.setPassword(existingUser.getPassword());
            }

            userJPA.save(userEntity);
            return "User updated successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public boolean deleteUser(int id) {
        if (!userJPA.existsById(id)) {
            return false;
        }
        try {
            userJPA.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private UserBean convertToBean(UserEntity entity) {
        return new UserBean(
            entity.getId(),
            entity.getPassword(),
            entity.getName(),
            entity.getEmail(),
            entity.getAddress(),
            entity.getRole(),
            entity.getStatus()
        );
    }

    private UserEntity convertToEntity(UserBean bean) {
        UserEntity entity = new UserEntity();
        entity.setId(bean.getUserId());
        entity.setPassword(bean.getPassword());
        entity.setName(bean.getFullName());
        entity.setEmail(bean.getEmail());
        entity.setAddress(bean.getAddress());
        entity.setRole(bean.getRole());
entity.setStatus(bean.getStatus());
        return entity;
    }
}