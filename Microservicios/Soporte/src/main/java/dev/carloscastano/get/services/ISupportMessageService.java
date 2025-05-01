package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.SupportMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISupportMessageService {
    // Métodos GET
    List<SupportMessage> findAll();
    Optional<SupportMessage> findById(Long id);
    List<SupportMessage> findByTicketId(Long ticketId);

    // Métodos POST
    SupportMessage save(SupportMessage supportMessage);

    // Métodos PATCH
    SupportMessage partialUpdate(SupportMessage supportMessage, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}