package com.ELShovi.controller;

import com.ELShovi.dto.dashboard.*;
import com.ELShovi.service.implementation.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('administrador','mesero')")

public class DashboardController {
    private final DashboardService service;

    @GetMapping("/stats")
    public DashboardStatsDTO stats() { return service.stats(); }

    @GetMapping("/ventas-diarias")
    public List<VentasDiariasDTO> ventasDiarias() { return service.ventasDiarias(); }

    @GetMapping("/categorias-mas-vendidas")
    public List<CategoriaVentasDTO> categoriasMasVendidas() { return service.categoriasMasVendidas(); }

    @GetMapping("/productos-mas-vendidos")
    public List<ProductoMasVendidoDTO> productosMasVendido() { return service.productosMasVendidos(); }

    @GetMapping("/pedidos-por-tipo")
    public List<PedidosPorTipoDTO> pedidosPorTipo() { return service.pedidosPorTipo(); }

    @GetMapping("/ingresos-mensuales")
    public List<IngresosMensualesDTO> ingresosMensuales() { return service.ingresosMensuales(); }

    @GetMapping("/metodos-pago")
    public List<MetodoPagoDTO> metodosPago() { return service.metodosPago(); }

    @GetMapping("/ventas-por-usuario")
    public List<VentasPorUsuarioDTO> ventasPorUsuario() { return service.ventasPorUsuario(); }
}
