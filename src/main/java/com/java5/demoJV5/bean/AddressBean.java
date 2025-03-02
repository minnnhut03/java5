package com.java5.demoJV5.bean;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBean {
	private Integer id;
	
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 6, max = 50, message = "Tên phải từ 6 đến 50 ký tự")
    private String customerName; 

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(min = 6, max = 300, message = "Địa chỉ không được quá 300 ký tự")
    private String address;
}
