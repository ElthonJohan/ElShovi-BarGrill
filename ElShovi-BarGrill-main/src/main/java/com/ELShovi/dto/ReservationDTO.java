package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private int id;
    @NotNull
    private int tableId;
    @NotNull
    private int userId;
    @NotNull
    private String reservationDate;
    @NotNull
    private String reservationTime;
    @NotNull
    private String status;
}