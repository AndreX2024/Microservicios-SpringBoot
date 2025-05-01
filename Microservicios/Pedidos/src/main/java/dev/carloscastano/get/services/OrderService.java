package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.OrderStatus;
import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.repository.OrderRepository;
import dev.carloscastano.get.repository.OrderStatusRepository;
import dev.carloscastano.get.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByIdUsuario(userId);
    }

    @Override
    public List<Order> findByEstado_IdEstado(Long estadoId) {
        return orderRepository.findByEstado_IdEstado(estadoId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order partialUpdate(Order order, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Order.class, key);
            if (field != null) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                if (value instanceof Map<?, ?> mapValue) {
                    if (fieldType.equals(OrderStatus.class)) {
                        Long idEstado = Long.valueOf(mapValue.get("idEstado").toString());
                        OrderStatus estado = orderStatusRepository.findById(idEstado)
                                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
                        ReflectionUtils.setField(field, order, estado);
                    } else if (fieldType.equals(Pay.class)) {
                        Long idPago = Long.valueOf(mapValue.get("idPago").toString());
                        Pay pago = payRepository.findById(idPago)
                                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
                        ReflectionUtils.setField(field, order, pago);
                    }
                } else {
                    ReflectionUtils.setField(field, order, value);
                }
            }
        });

        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}