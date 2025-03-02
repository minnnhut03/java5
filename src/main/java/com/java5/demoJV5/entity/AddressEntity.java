package com.java5.demoJV5.entity;
import java.util.List;

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
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "customer_name", nullable = false, columnDefinition = "NVARCHAR(50)")
    private String customerName;

    @Size(max = 14)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 14)
    private String phoneNumber;

    @NotNull
    @Column(name = "address", nullable = false, columnDefinition = "NVARCHAR(300)")
    private String address;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderEntity> orders;
}
