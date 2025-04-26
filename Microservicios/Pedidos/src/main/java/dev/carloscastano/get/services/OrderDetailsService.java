package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.OrderDetails;
import dev.carloscastano.get.repository.OrderDetailsRepository;
import dev.carloscastano.get.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    @Override
    public List<OrderDetails> getByOrderId(Long idPedido) {
        Optional<Order> pedido = orderRepository.findById(idPedido);
        return pedido.map(orderDetailsRepository::findByPedido).orElse(Collections.emptyList());
    }

    @Override
    public Optional<OrderDetails> getById(Long idDetalle) {
        return orderDetailsRepository.findById(idDetalle);
    }

    @Override
    public OrderDetails agregarItemAlPedido(Long idPedido, OrderDetails item) {
        Order pedido = orderRepository.findById(idPedido).orElse(null);
        if (pedido != null) {
            item.setPedido(pedido);
            return orderDetailsRepository.save(item);
        }
        return null;
    }
}
