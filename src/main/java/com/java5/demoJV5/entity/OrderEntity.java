package com.java5.demoJV5.entity;
import java.math.BigDecimal;
import java.time.Instant;

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
@Table(name = "orders")
public class OrderEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @ColumnDefault("getdate()")
    @Column(name = "date_created", nullable = false)
    private Instant dateCreated;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "total_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalAmount;

}