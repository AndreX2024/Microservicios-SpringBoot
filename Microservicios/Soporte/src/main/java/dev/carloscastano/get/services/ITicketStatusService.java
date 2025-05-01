package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.entities.TicketStatus;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITicketStatusService {
    // Métodos GET
    List<TicketStatus> findAll();
    Optional<TicketStatus> findById(Integer id);
    Optional<TicketStatus> findByNombreEstado(String nombreEstado);
    List<Ticket> findTicketsByStatusId(Integer statusId);

    // Métodos POST
    TicketStatus save(TicketStatus ticketStatus);

    // Métodos PATCH
    TicketStatus partialUpdate(TicketStatus ticketStatus, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Integer id);
}