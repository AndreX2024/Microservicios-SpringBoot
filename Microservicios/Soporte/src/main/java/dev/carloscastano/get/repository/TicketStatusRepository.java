package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.TicketStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TicketStatusRepository extends CrudRepository<TicketStatus, Integer> {
    Optional<TicketStatus> findByNombreEstado(String nombreEstado);
}