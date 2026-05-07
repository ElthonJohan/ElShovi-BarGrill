package com.ELShovi.dto;

import com.ELShovi.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull
    private PaymentMethod paymentMethod;
}
