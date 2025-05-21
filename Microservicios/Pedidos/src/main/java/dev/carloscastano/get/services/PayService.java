package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.entities.PayMethod;
import dev.carloscastano.get.entities.PayStatus;
import dev.carloscastano.get.repository.OrderRepository;
import dev.carloscastano.get.repository.PayMethodRepository;
import dev.carloscastano.get.repository.PayRepository;
import dev.carloscastano.get.repository.PayStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PayService implements IPayService {

    @Autowired
    private PayRepository payRepository;

    @Autowired
    private PayStatusRepository payStatusRepository;

    @Autowired
    private PayMethodRepository payMethodRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Pay> findAll() {
        return (List<Pay>) payRepository.findAll();
    }

    @Override
    public Optional<Pay> findById(Long id) {
        return payRepository.findById(id);
    }

    @Override
    public Optional<Pay> findByPedido_IdPedido(Long pedidoId) {
        return payRepository.findByPedido_IdPedido(pedidoId);
    }

    @Override
    public List<Pay> findByMetodoPago_IdMetodo(Long metodoId) {
        return payRepository.findByMetodoPago_IdMetodo(metodoId);
    }

    @Override
    public List<Pay> findByEstadoPago_IdEstadoPago(Long estadoPagoId) {
        return payRepository.findByEstadoPago_IdEstadoPago(estadoPagoId);
    }

    @Override
    public Optional<Pay> findByIdPagoExterno(String externalId) {
        return payRepository.findByIdPagoExterno(externalId);
    }


    @Override
    public Pay save(Pay pay) {
        return payRepository.save(pay);
    }

    @Override
    public Pay partialUpdate(Pay pay, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Pay.class, key);
            if (field != null) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                // Si el valor es un mapa, lo tratamos como una relación con otra entidad
                if (value instanceof Map<?, ?> mapValue) {
                    // Si el campo es de tipo PayStatus (estado del pago)
                    if (fieldType.equals(PayStatus.class)) {
                        Long idEstadoPago = Long.valueOf(mapValue.get("idEstadoPago").toString());
                        PayStatus estadoPago = payStatusRepository.findById(idEstadoPago)
                                .orElseThrow(() -> new RuntimeException("Estado de pago no encontrado"));
                        ReflectionUtils.setField(field, pay, estadoPago);

                        // Si el campo es de tipo PayMethod (método de pago)
                    } else if (fieldType.equals(PayMethod.class)) {
                        Long idMetodoPago = Long.valueOf(mapValue.get("idMetodoPago").toString());
                        PayMethod metodoPago = payMethodRepository.findById(idMetodoPago)
                                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
                        ReflectionUtils.setField(field, pay, metodoPago);

                        // Si el campo es de tipo Order (pedido relacionado)
                    } else if (fieldType.equals(Order.class)) {
                        Long idPedido = Long.valueOf(mapValue.get("idPedido").toString());
                        Order pedido = orderRepository.findById(idPedido)
                                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
                        ReflectionUtils.setField(field, pay, pedido);
                    }
                } else {
                    if ("idPagoExterno".equals(key)) {
                        pay.setIdPagoExterno((String) value); // Manejo directo
                    } else {
                        ReflectionUtils.setField(field, pay, value);
                    }
                }
            }
        });

        // Guardar la entidad Pay con los cambios realizados
        return payRepository.save(pay);
    }


    @Override
    public void deleteById(Long id) {
        payRepository.deleteById(id);
    }
}