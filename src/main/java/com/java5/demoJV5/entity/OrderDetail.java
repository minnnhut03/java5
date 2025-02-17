package com.java5.demoJV5.entity;
import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "product_id", nullable = false)
    private OrderEntity product;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Size(max = 10)
    @NotNull
    @Column(name = "\"size\"", nullable = false, length = 10)
    private String size;

    @NotNull
    @Column(name = "unit_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal unitPrice;

}