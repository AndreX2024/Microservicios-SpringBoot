package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayStatus;
import dev.carloscastano.get.repository.PayRepository;
import dev.carloscastano.get.repository.PayStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PayStatusService implements IPayStatusService {

    @Autowired
    private PayStatusRepository payStatusRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    public List<PayStatus> findAll() {
        return (List<PayStatus>) payStatusRepository.findAll();
    }

    @Override
    public Optional<PayStatus> findById(Long id) {
        return payStatusRepository.findById(id);
    }

    @Override
    public Optional<PayStatus> findByEstado(String estado) {
        return payStatusRepository.findByEstado(estado);
    }

    @Override
    public List<Pay> findPaysByEstadoId(Long estadoId) {
        return payRepository.findByEstadoPago_IdEstadoPago(estadoId);
    }

    @Override
    public PayStatus save(PayStatus payStatus) {
        return payStatusRepository.save(payStatus);
    }

    @Override
    public PayStatus partialUpdate(PayStatus payStatus, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(PayStatus.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, payStatus, value);
            }
        });
        return payStatusRepository.save(payStatus);
    }

    @Override
    public void deleteById(Long id) {
        payStatusRepository.deleteById(id);
    }
}