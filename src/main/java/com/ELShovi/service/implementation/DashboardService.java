package com.ELShovi.service.implementation;

import com.ELShovi.dto.dashboard.*;
import com.ELShovi.repository.IDashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IDashboardRepository repo;

    public DashboardStatsDTO stats() {
        return new DashboardStatsDTO(
                repo.ventasHoy(),
                repo.pedidosActivos(),
                repo.clientesHoy(),
                repo.reservasHoy()
        );
    }


    //public List<VentasDiariasDTO> ventasDiarias() { return repo.ventasDiarias(); }

    public List<VentasDiariasDTO> ventasDiarias() {
        List<Object[]> results = repo.ventasDiarias();
        return results.stream()
                .map(r -> new VentasDiariasDTO(
                        ((java.sql.Date) r[0]),
                        (BigDecimal) r[1]
                ))
                .toList();
    }


    public List<CategoriaVentasDTO> categoriasMasVendidas() { return repo.categoriasMasVendidas(); }

    public List<ProductoMasVendidoDTO> productosMasVendidos() { return repo.productosMasVendidos(); }

    public List<PedidosPorTipoDTO> pedidosPorTipo() { return repo.pedidosPorTipo(); }

    public List<IngresosMensualesDTO> ingresosMensuales() { return repo.ingresosMensuales(); }

    public List<MetodoPagoDTO> metodosPago() { return repo.metodosPago(); }

    public List<VentasPorUsuarioDTO> ventasPorUsuario() { return repo.ventasPorUsuario(); }
}
