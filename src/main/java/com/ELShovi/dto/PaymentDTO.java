package com.ELShovi.dto;

import com.ELShovi.model.enums.PaymentMethod;
import com.ELShovi.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;


//import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Integer idPayment;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;
    private LocalDateTime paymentDate ;
    private PaymentStatus status;
    private String message;
}
