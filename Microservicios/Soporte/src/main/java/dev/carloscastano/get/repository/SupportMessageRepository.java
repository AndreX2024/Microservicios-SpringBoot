package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.SupportMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupportMessageRepository extends CrudRepository<SupportMessage, Long> {
    List<SupportMessage> findByTicket_IdTicket(Long ticketId);
}