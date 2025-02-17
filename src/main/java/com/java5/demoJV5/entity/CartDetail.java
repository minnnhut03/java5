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
@Table(name = "cart_details")
public class CartDetail {
    @Id
    @Column(name = "cart_detail_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @NotNull
    @ColumnDefault("1")
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
