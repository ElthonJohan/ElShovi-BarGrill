package com.ELShovi.service.implementation;

import com.ELShovi.model.Reservation;
import com.ELShovi.repository.IGenericRepository;
import com.ELShovi.repository.IReservationRepository;
import com.ELShovi.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService extends GenericService<Reservation,Integer> implements IReservationService {
    private final IReservationRepository repo;
    @Override
    protected IGenericRepository<Reservation,Integer> getRepo() {
        return repo;
    }

    @Override
    public Reservation save(Reservation reservation) {
        List<Reservation> conflicts =
                repo.findConflicts(
                        reservation.getTable().getIdTable(),
                        reservation.getReservationDate(),
                        reservation.getReservationTimeStart(),
                        reservation.getReservationTimeEnd()
                );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("La mesa ya está reservada en ese horario.");
        }

        return repo.save(reservation);
    }
    @Override
    public Reservation update(Reservation reservation, Integer id) throws Exception {

        reservation.setIdReservation(id);

        List<Reservation> conflicts =
                repo.findConflicts(
                                reservation.getTable().getIdTable(),
                                reservation.getReservationDate(),
                                reservation.getReservationTimeStart(),
                                reservation.getReservationTimeEnd()
                        ).stream()
                        .filter(r -> !r.getIdReservation().equals(id)) // evitar conflicto consigo misma
                        .toList();

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("La mesa ya está reservada en ese horario.");
        }

        return repo.save(reservation);
    }

}