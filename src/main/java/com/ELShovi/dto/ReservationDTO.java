package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;


//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Integer idReservation;
    @NotNull
    private Integer idTable;
    @NotNull
    private Integer idUser;
    @NotNull
    private LocalDate reservationDate;
    @NotNull
    private LocalTime reservationTimeStart;
    @NotNull
    private LocalTime reservationTimeEnd;
    @NotNull
    private String status;
}