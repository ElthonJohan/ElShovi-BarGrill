package com.ELShovi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idOrderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menuItem", nullable = false,
            foreignKey = @ForeignKey(name = "FK_orderitem_menuitem"))
    private MenuItem menuItem;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false,
            foreignKey = @ForeignKey(name = "FK_orderitem_order"))
    private Order order;
}

