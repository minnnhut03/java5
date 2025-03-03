package com.java5.demoJV5.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private Integer productId;
    private Integer quantity;
    private String size;
    private long unitPrice;
}
