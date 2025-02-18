package com.java5.demoJV5.bean;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBean {
	private Optional<Integer> id;
	@NotBlank(message = "Tên không được bỏ trống")
	private String name;
	
	@Length(min = 20, max = 200, message = "Mô tả sản phẩm phải từ 20 đến 200 ký tự")
	private String desc;
	
	@Min(value = 10000, message = "Giá tiền phải lớn hơn hoặc bằng 10000")
	private long price;
	
	@Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
	private int quantity;

	@Min(value = 0, message = "Danh mục không được bỏ trống")
	private int category;

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    private Boolean status = true;
    
    private List<MultipartFile> images;

    public String validateImageFiles() {
        if (images == null || images.isEmpty()) {
            return "Bạn cần tải lên ít nhất một hình ảnh.";
        }
        
        long totalSize = 0;
        if (images.size() > 3) {
            return "Bạn chỉ được tải lên tối đa 3 ảnh.";
        }

        for (MultipartFile file : images) {
            totalSize += file.getSize();
            if (file.getSize() > 5 * 1024 * 1024) { 
                return "Mỗi ảnh không được vượt quá 5MB.";
            }
        }

        if (totalSize > 20 * 1024 * 1024) {
            return "Tổng dung lượng ảnh không được vượt quá 20MB.";
        }

        return null;
    }
}
