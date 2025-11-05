package com.ELShovi.dto;

import com.ELShovi.model.enums.DeliveryStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryDTO {
    private Integer idDelivery;
    private Integer idOrder;
    @NotNull
    @Size(min = 3, max = 100)
    private String address;
    @NotNull
    @Pattern(regexp = "[0-9]{9}", message = "Phone number must be 9 digits")
    private String phone;
    @NotNull
    @Size(min = 3, max = 50)
    private String driverName;
    @NotNull
    @Size(min = 6, max = 15)
    private String vehiclePlate;
    @NotNull

    private DeliveryStatus status;
    @NotNull
    private LocalDateTime deliveryTime;
}