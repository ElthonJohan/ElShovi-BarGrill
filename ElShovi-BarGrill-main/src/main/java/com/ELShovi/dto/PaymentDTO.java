package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private int id;
    @NotNull
    private String paymentMethod;
    @NotNull
    private double amount;
    @NotNull
    private String paymentDate;
    @NotNull
    private String status;
}
