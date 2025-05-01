package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IOrderStatusService {
    // Métodos GET
    List<OrderStatus> findAll();
    Optional<OrderStatus> findById(Long id);
    Optional<OrderStatus> findByEstado(String estado);
    List<Order> findOrdersByEstadoId(Long statusId);

    // Métodos POST
    OrderStatus save(OrderStatus orderStatus);

    // Métodos PATCH
    OrderStatus partialUpdate(OrderStatus orderStatus, Map<String, Object> updates);

    // Métodos DELETE
    void deleteById(Long id);
}