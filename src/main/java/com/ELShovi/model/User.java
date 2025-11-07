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
    private Integer idUser;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 100)
    private String userName;
    @Column(nullable = false, length = 50)
    private String password;
    @Column( length = 100)
    private String fullName;
    @Column(nullable = false)
    private boolean active;
    private LocalDateTime createdAt=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "id_role",nullable = false,
        foreignKey = @ForeignKey(name = "FK_user_role"))
    private Role role;

}
