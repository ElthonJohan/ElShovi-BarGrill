package com.ELShovi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "modulos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idModule;

    @Column(nullable = false, unique = true, length = 100)
    private String name; // ej: "USUARIOS", "PEDIDOS", "MESAS", "REPORTES"

    @Column(nullable = false)
    private String description; // ej: "Gestión de usuarios del sistema"

    @Column(nullable = false)
    private String path; // ej: "/users", "/orders", "/tables"

    @Column(nullable = false)
    private boolean active = true;

    @ManyToMany(mappedBy = "modules", fetch = FetchType.LAZY)
    private List<Role> roles;
}

