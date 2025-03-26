package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository repository;

    @Override
    public List<Order> getAll() {
        return (List<Order>) repository.findAll();
    }

    @Override
    public List<Order> obtenerPedidoUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }



}


