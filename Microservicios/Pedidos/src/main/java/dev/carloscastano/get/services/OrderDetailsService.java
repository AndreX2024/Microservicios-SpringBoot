package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.OrderDetails;
import dev.carloscastano.get.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderDetailsService implements IOrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<OrderDetails> findAll() {
        return (List<OrderDetails>) orderDetailsRepository.findAll();
    }

    @Override
    public Optional<OrderDetails> findById(Long id) {
        return orderDetailsRepository.findById(id);
    }

    @Override
    public List<OrderDetails> findByPedido_IdPedido(Long pedidoId) {
        return orderDetailsRepository.findByPedido_IdPedido(pedidoId);
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public OrderDetails partialUpdate(OrderDetails orderDetails, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(OrderDetails.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, orderDetails, value);
            }
        });
        return orderDetailsRepository.save(orderDetails);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailsRepository.deleteById(id);
    }
}