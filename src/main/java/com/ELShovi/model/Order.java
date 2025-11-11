package com.ELShovi.model;

import com.ELShovi.model.enums.OrderStatus;
import com.ELShovi.model.enums.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@jakarta.persistence.Table(name = "Orden")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idOrder;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false,
            foreignKey = @ForeignKey(name = "FK_order_user"))
    private User user;
    @OneToOne
    @JoinColumn(name = "id_table", nullable = false,
            foreignKey = @ForeignKey(name = "FK_order_table"))
    private Table table;
    @Column(nullable = false)
    private OrderType orderType;
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private double totalAmount;
    @Column(length = 200)
    private String notes;
    private LocalDateTime createdAt=LocalDateTime.now();

    @OneToMany
    @JoinColumn(name = "id_items", nullable = false,
            foreignKey = @ForeignKey(name = "FK_order_items"))
    private List<OrderItem> items = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "id_payment", nullable = false,
            foreignKey = @ForeignKey(name = "FK_order_payment"))
    private Payment payment;
}
