package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.SupportMessage;
import dev.carloscastano.get.repository.SupportMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupportMessageService implements ISupportMessageService {

    @Autowired
    private SupportMessageRepository supportMessageRepository;

    // Métodos GET
    @Override
    public List<SupportMessage> findAll() {
        return (List<SupportMessage>) supportMessageRepository.findAll();
    }

    @Override
    public Optional<SupportMessage> findById(Long id) {
        return supportMessageRepository.findById(id);
    }

    @Override
    public List<SupportMessage> findByTicketId(Long ticketId) {
        return supportMessageRepository.findByTicket_IdTicket(ticketId);
    }

    // Métodos POST
    @Override
    public SupportMessage save(SupportMessage supportMessage) {
        return supportMessageRepository.save(supportMessage);
    }

    // Métodos PATCH
    @Override
    public SupportMessage partialUpdate(SupportMessage supportMessage, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(SupportMessage.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, supportMessage, value);
            }
        });
        return supportMessageRepository.save(supportMessage);
    }

    // Métodos DELETE
    @Override
    public void deleteById(Long id) {
        supportMessageRepository.deleteById(id);
    }
}