package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public List<Order> obtenerPedidoUsuario(Long idUsuario) {
        return orderRepository.findByIdUsuario(idUsuario);
    }

    @Override
    public Order crearPedido(Order pedido) {
        return orderRepository.save(pedido);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order orden) {
        return orderRepository.save(orden);
    }

}
