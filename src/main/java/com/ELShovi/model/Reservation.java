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
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @OneToOne
    @JoinColumn(name = "id_table", nullable = false,
            foreignKey = @ForeignKey(name = "FK_reservation_table"))
    private Table Table;
    @OneToOne
    @JoinColumn(name = "id_user", nullable = false,
            foreignKey = @ForeignKey(name = "FK_reservation_user"))
    private User user;
    private String reservationDate;
    private String reservationTime;
    private String status;
}
