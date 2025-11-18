package com.ELShovi.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {
    private double ventasHoy;
    private int pedidosActivos;
    private int clientesHoy;
    private int reservasHoy;
}