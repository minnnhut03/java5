package com.java5.demoJV5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.java5.demoJV5.bean.UserBean;
import com.java5.demoJV5.entity.UserEntity;
import com.java5.demoJV5.jpa.UserJPA;

import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Service
public class UserService {

    @Autowired
    UserJPA userJPA;

    @Autowired
    Validator validator; // Used for manual validation

    public List<UserEntity> getAllUsers() {
        return userJPA.findAll();
    }

    public UserBean getUserById(int userId) {
        return userJPA.findById(userId).map(this::convertToBean).orElse(null);
    }

    public String saveUser(UserBean userBean) {

        try {
            // Check if the email already exists
            if (userJPA.existsByEmail(userBean.getEmail())) {
                return "Lỗi: Email đã tồn tại!";
            }

            // Set default values for role and status if not provided
            if (userBean.getRole() == null) {
                userBean.setRole(2); 
            }

            if (userBean.getStatus() == null) {
                userBean.setStatus(true); // Set default status to true
            }

            // Convert UserBean to UserEntity
            UserEntity userEntity = convertToEntity(userBean);

            // Save the user to the database
            userJPA.save(userEntity);

            return "User added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    public String updateUserStatus(int userId, boolean newStatus) {
        try {
            UserEntity existingUser = userJPA.findById(userId).orElse(null);
            if (existingUser == null) {
                return "Lỗi: Người dùng không tồn tại!";
            }

            existingUser.setStatus(newStatus);

            userJPA.save(existingUser);
            return "Trạng thái người dùng được cập nhật thành công!";
        } catch (Exception e) {
            return "Lỗi: " + e.getMessage();
        }
    }


    public String updateUser(@Valid UserBean userBean, BindingResult result) {
        // Validate the UserBean object
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder("Errors: ");
            for (ObjectError error : result.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append("; ");
            }
            return errors.toString();
        }

        try {
            UserEntity existingUser = userJPA.findById(userBean.getUserId()).orElse(null);
            if (existingUser == null) {
                return "Lỗi: Người dùng không tồn tại!";
            }

            if (!existingUser.getEmail().equals(userBean.getEmail()) && userJPA.existsByEmail(userBean.getEmail())) {
                return "Lỗi: Email đã tồn tại!";
            }

            // Convert UserBean to UserEntity and save
            UserEntity userEntity = convertToEntity(userBean);

            if (userBean.getPassword() == null || userBean.getPassword().isEmpty()) {
                userEntity.setPassword(existingUser.getPassword()); // Keep existing password if not provided
            }

            userJPA.save(userEntity);
            return "User updated successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
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
        
        // Do not set ID for new users
        if (bean.getUserId() != 0) {
            entity.setId(bean.getUserId());
        }

        entity.setPassword(bean.getPassword());
        entity.setName(bean.getName());
        entity.setEmail(bean.getEmail());
        entity.setAddress(bean.getAddress());
        entity.setRole(bean.getRole());
        entity.setStatus(bean.getStatus());

        return entity;
    }
}
