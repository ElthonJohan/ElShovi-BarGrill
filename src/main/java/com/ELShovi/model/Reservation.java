package com.ELShovi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idReservation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_table", nullable = false,
            foreignKey = @ForeignKey(name = "FK_reservation_table"))
    private RestaurantTable table;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", nullable = false,
            foreignKey = @ForeignKey(name = "FK_reservation_user"))
    private User user;
    @Column(nullable = false)
    private LocalDate reservationDate;
    @Column(nullable = false)
    private LocalTime reservationTimeStart;
    @Column(nullable = false)
    private LocalTime reservationTimeEnd;
    @Column(nullable = false)
    private String status;
}
