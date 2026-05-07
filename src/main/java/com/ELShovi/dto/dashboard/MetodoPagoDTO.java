package com.ELShovi.dto.dashboard;

import com.ELShovi.model.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPagoDTO {
    private PaymentMethod metodo;
    private Long total;
}
