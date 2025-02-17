package com.java5.demoJV5.bean;

import java.util.List;

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
    private Integer productId; 

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Length(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String productName;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description; 

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 10000, message = "Giá sản phẩm phải lớn hơn hoặc bằng 10.000")
    private Double price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng sản phẩm phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Danh mục không được để trống")
    @Min(value = 1, message = "Vui lòng chọn một danh mục hợp lệ")
    private Integer categoryId;

    @NotEmpty(message = "Bạn cần tải lên ít nhất một hình ảnh.")
    private List<String> imageUrls; 

    @NotNull(message = "Trạng thái sản phẩm không được để trống")
    private Boolean status = true;
    
    private List<MultipartFile> imageFiles;

    public String validateImageFiles() {
        if (imageFiles == null || imageFiles.isEmpty()) {
            return "Bạn cần tải lên ít nhất một hình ảnh.";
        }
        
        long totalSize = 0;
        if (imageFiles.size() > 3) {
            return "Bạn chỉ được tải lên tối đa 3 ảnh.";
        }

        for (MultipartFile file : imageFiles) {
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
