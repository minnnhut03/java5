package com.java5.demoJV5.bean;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBean {

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 6, max = 50, message = "Tên phải từ 6 đến 50 ký tự")
    private String customer_name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,13}$", message = "Số điện thoại phải có từ 10 đến 13 chữ số")
    private String phone_number;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min = 6, max = 300, message = "Địa chỉ không được quá 300 ký tự")
    private String address;

    @NotNull(message = "User ID không được bỏ trống")
    private Integer user_id;
}
