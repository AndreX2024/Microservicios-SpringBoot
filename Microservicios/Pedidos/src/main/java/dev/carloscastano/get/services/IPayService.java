package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Pay;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPayService {
    // Métodos GET
    List<Pay> findAll();
    Optional<Pay> findById(Long id);
    Optional<Pay> findByPedido_IdPedido(Long pedidoId);
    List<Pay> findByMetodoPago_IdMetodo(Long metodoId);
    List<Pay> findByEstadoPago_IdEstadoPago(Long estadoPagoId);
    Optional<Pay> findByIdPagoExterno(String externalId);

    // Métodos POST
    Pay save(Pay pay);

    // Métodos PATCH
    Pay partialUpdate(Pay pay, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}