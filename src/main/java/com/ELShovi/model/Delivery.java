package com.ELShovi.model;

import com.ELShovi.model.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idDelivery;

    @OneToOne
    @JoinColumn(name = "id_order", nullable = false,
    foreignKey = @ForeignKey(name = "FK_delivery_order"))
    private Order order;

    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false, length = 9)
    private String phone;
    @Column(nullable = false, length = 50)
    private String driverName;
    @Column(nullable = false, length = 15)
    private String vehiclePlate;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime deliveryTime;


}
