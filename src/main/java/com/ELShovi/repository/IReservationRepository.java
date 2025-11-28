package com.ELShovi.repository;

import com.ELShovi.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IReservationRepository extends IGenericRepository<Reservation,Integer> {
    @Query("""
    SELECT r FROM Reservation r
    WHERE r.Table.idTable = :idTable
      AND r.reservationDate = :date
      AND (
            r.reservationTimeStart < :endTime
        AND :startTime < r.reservationTimeEnd
      )
    """)
    List<Reservation> findConflicts(
            @Param("idTable") Integer idTable,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

}
