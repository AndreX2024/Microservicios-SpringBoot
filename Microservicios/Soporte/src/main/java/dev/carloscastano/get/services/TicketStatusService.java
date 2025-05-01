package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Ticket;
import dev.carloscastano.get.entities.TicketStatus;
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
public class TicketStatusService implements ITicketStatusService {

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // Métodos GET
    @Override
    public List<TicketStatus> findAll() {
        return (List<TicketStatus>) ticketStatusRepository.findAll();
    }

    @Override
    public Optional<TicketStatus> findById(Integer id) {
        return ticketStatusRepository.findById(id);
    }

    @Override
    public Optional<TicketStatus> findByNombreEstado(String nombreEstado) {
        return ticketStatusRepository.findByNombreEstado(nombreEstado);
    }

    @Override
    public List<Ticket> findTicketsByStatusId(Integer statusId) {
        return ticketRepository.findByEstado_IdEstado(statusId);
    }

    // Métodos POST
    @Override
    public TicketStatus save(TicketStatus ticketStatus) {
        return ticketStatusRepository.save(ticketStatus);
    }

    // Métodos PATCH
    @Override
    public TicketStatus partialUpdate(TicketStatus ticketStatus, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(TicketStatus.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, ticketStatus, value);
            }
        });
        return ticketStatusRepository.save(ticketStatus);
    }

    // Métodos DELETE
    @Override
    public void deleteById(Integer id) {
        ticketStatusRepository.deleteById(id);
    }
}