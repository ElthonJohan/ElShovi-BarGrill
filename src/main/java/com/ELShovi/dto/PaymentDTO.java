package com.ELShovi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Integer idPayment;
    private String paymentMethod;
    private double amount;
    private LocalDateTime paymentDate ;
    private String status;
    private String message;
}
