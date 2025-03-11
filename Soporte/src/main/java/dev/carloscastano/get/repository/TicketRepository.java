package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findByIdUsuario(Long idUsuario);
}
