package com.java5.demoJV5.entity;
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
@Table(name = "product_sizes")
public class ProductSizeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_size_id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "size", nullable = false, length = 10)
    private String size;

    @Column(name = "stock", nullable = false)
    private int stock;

}