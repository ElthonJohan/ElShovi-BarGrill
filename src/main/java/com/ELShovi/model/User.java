package com.ELShovi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String email;
    private String username;
    private String password;
    private String fullName;
    private boolean active;
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "id_role",nullable = false,
        foreignKey = @ForeignKey(name = "FK_user_role"))
    private Role role;

}
