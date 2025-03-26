package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;

import java.util.List;

public interface IOrderService {
    List<Order> getAll();

    List<Order> obtenerPedidoUsuario(Long idUsuario);
}
