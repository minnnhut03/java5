package com.java5.demoJV5.bean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Integer userId;
    private Integer addressId;
    private List<OrderDetailRequest> orderDetails;
}
