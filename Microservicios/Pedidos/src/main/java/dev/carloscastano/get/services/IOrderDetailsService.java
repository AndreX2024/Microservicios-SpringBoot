package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.OrderDetails;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IOrderDetailsService {
    // Métodos GET
    List<OrderDetails> findAll();
    Optional<OrderDetails> findById(Long id);
    List<OrderDetails> findByPedido_IdPedido(Long pedidoId);

    // Métodos POST
    OrderDetails save(OrderDetails orderDetails);

    // Métodos PATCH
    OrderDetails partialUpdate(OrderDetails orderDetails, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}