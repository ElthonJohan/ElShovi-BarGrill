package com.ELShovi.repository;

import com.ELShovi.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepository extends IGenericRepository<Order,Integer> {

    @Query("""
    SELECT o FROM Pedido o 
    WHERE o.table.idTable = :idTable
    AND o.status <> 'COMPLETADA'
    AND (:excludeId IS NULL OR o.idOrder <> :excludeId)
""")
    List<Order> findActiveOrdersByTable(@Param("idTable") Integer idTable,
                                        @Param("excludeId") Integer excludeId);

    @Query("""
    SELECT COUNT(o) > 0 FROM Pedido o
    WHERE o.table.idTable = :idMesa 
    AND o.status <> 'COMPLETADA'
""")
    boolean existsByTableAndStatusNot(@Param("idMesa") Integer idMesa,
                                      @Param("status") String status);



}
