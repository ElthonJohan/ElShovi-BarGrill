package com.ELShovi.service.implementation;

import com.ELShovi.dto.dashboard.*;
import com.ELShovi.repository.IDashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IDashboardRepository repo;

    public DashboardStatsDTO stats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();

        dto.setVentasHoy( repo.ventasHoy() != null ? repo.ventasHoy() : 0.0 );
        dto.setPedidosActivos( repo.pedidosActivos() != null ? repo.pedidosActivos() : 0 );
        dto.setClientesHoy( repo.clientesHoy() != null ? repo.clientesHoy() : 0 );
        dto.setReservasHoy( repo.reservasHoy() != null ? repo.reservasHoy() : 0 );

        return dto;
    }


    //public List<VentasDiariasDTO> ventasDiarias() { return repo.ventasDiarias(); }

    public List<VentasDiariasDTO> ventasDiarias() {
        List<Object[]> results = repo.ventasDiarias();

        return results.stream().map(r -> {

            Object fechaObj = r[0];
            Object totalObj = r[1];

            // --- FECHA ---
            java.util.Date fecha;
            if (fechaObj instanceof Date d) {
                fecha = new java.util.Date(d.getTime());
            } else if (fechaObj instanceof Timestamp t) {
                fecha = new java.util.Date(t.getTime());
            } else {
                // fallback si viene como string
                fecha = java.sql.Date.valueOf(fechaObj.toString());
            }

            // --- TOTAL ---
            BigDecimal total;
            if (totalObj instanceof BigDecimal bd) {
                total = bd;
            } else if (totalObj instanceof Number n) {
                total = BigDecimal.valueOf(n.doubleValue());
            } else {
                total = new BigDecimal(totalObj.toString());
            }

            return new VentasDiariasDTO(fecha, total);

        }).toList();
    }


    public List<CategoriaVentasDTO> categoriasMasVendidas() { return repo.categoriasMasVendidas(); }

    public List<ProductoMasVendidoDTO> productosMasVendidos() { return repo.productosMasVendidos(); }

    public List<PedidosPorTipoDTO> pedidosPorTipo() { return repo.pedidosPorTipo(); }

    public List<IngresosMensualesDTO> ingresosMensuales() { return repo.ingresosMensuales(); }

    public List<MetodoPagoDTO> metodosPago() { return repo.metodosPago(); }

    public List<VentasPorUsuarioDTO> ventasPorUsuario() { return repo.ventasPorUsuario(); }
}
