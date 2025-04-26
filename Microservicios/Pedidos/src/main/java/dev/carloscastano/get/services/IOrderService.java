package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> getAll();
    Optional<Order> getById(Long id);
    Order save(Order orden);
    List<Order> obtenerPedidoUsuario(Long idUsuario);

    Order crearPedido(Order pedido);
}
