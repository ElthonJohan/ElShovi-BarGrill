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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idRole;
    @Column(nullable = false, unique = true,length = 50)
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_module",
            joinColumns = @JoinColumn(name = "id_role", referencedColumnName = "idRole"),
            inverseJoinColumns = @JoinColumn(name = "id_module", referencedColumnName = "idModule")
    )
    private List<Module> modules;
}
