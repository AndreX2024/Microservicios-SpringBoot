package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.entities.SupportMessage;
import dev.carloscastano.get.entities.TicketStatus;
import dev.carloscastano.get.repository.SupportMessageRepository;
import dev.carloscastano.get.repository.TicketRepository;
import dev.carloscastano.get.repository.TicketStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService implements ITicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private SupportMessageRepository supportMessageRepository;

    // Métodos GET
    @Override
    public List<Ticket> findAll() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> findByUserId(Long userId) {
        return ticketRepository.findByIdUsuario(userId);
    }

    @Override
    public List<Ticket> findByStatusId(Integer statusId) {
        return ticketRepository.findByEstado_IdEstado(statusId);
    }

    @Override
    public List<SupportMessage> findMessagesByTicketId(Long ticketId) {
        return supportMessageRepository.findByTicket_IdTicket(ticketId);
    }

    // Métodos POST
    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Métodos PATCH
    @Override
    public Ticket partialUpdate(Ticket ticket, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Ticket.class, key);
            if (field != null) {
                field.setAccessible(true);
                if (key.equals("estado")) {
                    // Manejar la actualización del estado de forma especial
                    if (value instanceof Map) {
                        Integer idEstado = (Integer) ((Map<?, ?>) value).get("idEstado");
                        if (idEstado != null) {
                            Optional<TicketStatus> statusOptional = ticketStatusRepository.findById(idEstado);
                            statusOptional.ifPresent(ticket::setEstado);
                        }
                    }
                } else {
                    ReflectionUtils.setField(field, ticket, value);
                }
            }
        });
        return ticketRepository.save(ticket);
    }

    // Métodos DELETE
    @Override
    public void deleteById(Long id) {
        ticketRepository.deleteById(id);
    }
}