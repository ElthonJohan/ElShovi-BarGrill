package com.ELShovi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMenuItem;
    @Column(nullable = false, length = 150)
    private String name;
    private String description;
    @Column(nullable = false)
    private double price;
    @Column(length = 500)

    private String imageUrl;
    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false,
        foreignKey = @ForeignKey(name = "FK_menuItem_category"))
    private Category category;
}
