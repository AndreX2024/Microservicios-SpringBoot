package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.OrderDetails;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailsService {
    List<OrderDetails> getByOrderId(Long idPedido);
    Optional<OrderDetails> getById(Long idDetalle);
    OrderDetails agregarItemAlPedido(Long idPedido, OrderDetails item);
}
