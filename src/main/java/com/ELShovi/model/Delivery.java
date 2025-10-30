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
    private int id;

    @OneToOne
    @JoinColumn(name = "id_order", nullable = false,
    foreignKey = @ForeignKey(name = "FK_delivery_order"))
    private Order order;

    private String address;
    private String phone;
    private String driverName;
    private String vehiclePLate;

    private DeliveryStatus status;

    private LocalDateTime deliveryTime;


}
