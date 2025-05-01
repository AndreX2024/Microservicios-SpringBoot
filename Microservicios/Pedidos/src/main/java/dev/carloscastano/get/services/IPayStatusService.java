package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPayStatusService {
    // Métodos GET
    List<PayStatus> findAll();
    Optional<PayStatus> findById(Long id);
    Optional<PayStatus> findByEstado(String estado);
    List<Pay> findPaysByEstadoId(Long estadoId);

    // Métodos POST
    PayStatus save(PayStatus payStatus);

    // Métodos PATCH
    PayStatus partialUpdate(PayStatus payStatus, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}