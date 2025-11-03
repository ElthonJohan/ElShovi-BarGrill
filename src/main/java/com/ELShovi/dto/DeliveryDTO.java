package com.ELShovi.dto;

import com.ELShovi.model.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryDTO {
    private Integer id;
    @NotNull
    private Integer idOrder;
    @NotNull
    private String address;
    @NotNull
    private String phone;
    @NotNull
    private String driverName;
    @NotNull
    private String vehiclePLate;
    @NotNull

    private DeliveryStatus status;
    @NotNull
    private LocalDateTime deliveryTime;
}