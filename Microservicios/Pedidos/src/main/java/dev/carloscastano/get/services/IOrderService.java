package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IOrderService {
    // Métodos GET
    List<Order> findAll();
    Optional<Order> findById(Long id);
    List<Order> findByUserId(Long userId);
    List<Order> findByEstado_IdEstado(Long estadoId);

    // Métodos POST
    Order save(Order order);

    // Métodos PATCH
    Order partialUpdate(Order order, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}