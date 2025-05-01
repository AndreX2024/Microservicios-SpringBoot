package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayMethod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPayMethodService {
    // Métodos GET
    List<PayMethod> findAll();
    Optional<PayMethod> findById(Long id);
    Optional<PayMethod> findByMetodo(String metodo);
    List<Pay> findPaysByMetodoId(Long metodoId);

    // Métodos POST
    PayMethod save(PayMethod payMethod);

    // Métodos PATCH
    PayMethod partialUpdate(PayMethod payMethod, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}