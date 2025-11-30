package com.ELShovi.repository;

import com.ELShovi.dto.dashboard.*;
import com.ELShovi.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDashboardRepository extends IGenericRepository<Order, Integer> {

    // --- ESTADÍSTICAS PRINCIPALES ---
    @Query("""
        SELECT SUM(o.totalAmount)
        FROM Pedido o
        JOIN o.payment p
        WHERE DATE(o.createdAt) = CURRENT_DATE
        AND p.status = 'COMPLETADO'
    """)
    Double ventasHoy();

    @Query("""
        SELECT COUNT(o)
        FROM Pedido o
        WHERE o.status IN ('PENDIENTE', 'EN_PREPARACION', 'LISTO', 'ENTREGADO', 'CANCELADO')
    """)
    Integer pedidosActivos();

    @Query("""
        SELECT COUNT(DISTINCT o.user.id)
        FROM Pedido o
        WHERE DATE(o.createdAt) = CURRENT_DATE
    """)
    Integer clientesHoy();

    @Query("""
        SELECT COUNT(r)
        FROM Reservation r
        WHERE r.reservationDate = CURRENT_DATE
    """)
    Integer reservasHoy();


    // --- VENTAS ÚLTIMOS 7 DÍAS ---
//    @Query("""
//    SELECT new com.ELShovi.dto.dashboard.VentasDiariasDTO(
//        FUNCTION('date', o.createdAt),
//        SUM(o.totalAmount)
//    )
//    FROM Order o
//    JOIN o.payment p
//    WHERE p.status = 'PAID'
//    GROUP BY FUNCTION('date', o.createdAt)
//    ORDER BY FUNCTION('date', o.createdAt) DESC
//""")
//    List<VentasDiariasDTO> ventasDiarias();


    @Query(value = """
    SELECT DATE(o.created_at) AS fecha, SUM(o.total_amount) AS total
    FROM orden o
    JOIN payment p ON o.id_payment = p.id_payment
    WHERE p.status = 'COMPLETADO'
    GROUP BY DATE(o.created_at)
    ORDER BY DATE(o.created_at) DESC
    LIMIT 7
""", nativeQuery = true)
    List<Object[]> ventasDiarias();


    // --- CATEGORÍAS MÁS VENDIDAS ---
    @Query("""
        SELECT new com.ELShovi.dto.dashboard.CategoriaVentasDTO(
            c.name,
            SUM(oi.quantity)
        )
        FROM OrderItem oi
        JOIN oi.menuItem mi
        JOIN mi.category c
        GROUP BY c.name
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<CategoriaVentasDTO> categoriasMasVendidas();


    // --- PRODUCTOS MÁS VENDIDOS ---
    @Query("""
        SELECT new com.ELShovi.dto.dashboard.ProductoMasVendidoDTO(
            mi.name,
            SUM(oi.quantity)
        )
        FROM OrderItem oi
        JOIN oi.menuItem mi
        GROUP BY mi.name
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<ProductoMasVendidoDTO> productosMasVendidos();


    // --- PEDIDOS POR TIPO ---
    @Query("""
        SELECT new com.ELShovi.dto.dashboard.PedidosPorTipoDTO(
            o.orderType,
            COUNT(o)
        )
        FROM Pedido o
        GROUP BY o.orderType
    """)
    List<PedidosPorTipoDTO> pedidosPorTipo();


    // --- INGRESOS MENSUALES ---
    @Query("""
        SELECT new com.ELShovi.dto.dashboard.IngresosMensualesDTO(
            MONTH(o.createdAt),
            SUM(o.totalAmount)
        )
        FROM Pedido o
        JOIN o.payment p
        WHERE p.status = 'PAID'
        GROUP BY MONTH(o.createdAt)
        ORDER BY MONTH(o.createdAt)
    """)
    List<IngresosMensualesDTO> ingresosMensuales();


    // --- MÉTODOS DE PAGO ---
    @Query("""
        SELECT new com.ELShovi.dto.dashboard.MetodoPagoDTO(
            p.paymentMethod,
            COUNT(p)
        )
        FROM Payment p
        WHERE p.status = 'COMPLETADO'
        GROUP BY p.paymentMethod
    """)
    List<MetodoPagoDTO> metodosPago();


    // --- VENTAS POR USUARIO ---
    @Query("""
        SELECT new com.ELShovi.dto.dashboard.VentasPorUsuarioDTO(
            u.userName,
            SUM(o.totalAmount)
        )
        FROM Pedido o
        JOIN o.user u
        JOIN o.payment p
        WHERE p.status = 'COMPLETADO'
        GROUP BY u.userName
        ORDER BY SUM(o.totalAmount) DESC
    """)
    List<VentasPorUsuarioDTO> ventasPorUsuario();
}
