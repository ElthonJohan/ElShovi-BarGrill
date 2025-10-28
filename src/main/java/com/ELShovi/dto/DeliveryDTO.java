package com.ELShovi.dto;

import com.ELShovi.model.Order;
import com.ELShovi.model.enums.DeliveryStatus;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

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
