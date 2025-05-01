package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.OrderStatus;
import dev.carloscastano.get.repository.OrderStatusRepository;
import dev.carloscastano.get.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderStatusService implements IOrderStatusService {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderStatus> findAll() {
        return (List<OrderStatus>) orderStatusRepository.findAll();
    }

    @Override
    public Optional<OrderStatus> findById(Long id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public Optional<OrderStatus> findByEstado(String estado) {
        return orderStatusRepository.findByEstado(estado);
    }

    @Override
    public List<Order> findOrdersByEstadoId(Long statusId) {
        return orderRepository.findByEstado_IdEstado(statusId);
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public OrderStatus partialUpdate(OrderStatus orderStatus, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(OrderStatus.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, orderStatus, value);
            }
        });
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public void deleteById(Long id) {
        orderStatusRepository.deleteById(id);
    }
}