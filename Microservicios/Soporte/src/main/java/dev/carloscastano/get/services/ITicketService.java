package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.entities.SupportMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITicketService {
    // Métodos GET
    List<Ticket> findAll();
    Optional<Ticket> findById(Long id);
    List<Ticket> findByUserId(Long userId);
    List<Ticket> findByStatusId(Integer statusId);
    List<SupportMessage> findMessagesByTicketId(Long ticketId);

    // Métodos POST
    Ticket save(Ticket ticket);

    // Métodos PATCH
    Ticket partialUpdate(Ticket ticket, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}