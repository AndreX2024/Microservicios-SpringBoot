package dev.carloscastano.get.controllers;


import dev.carloscastano.get.entities.*;
import dev.carloscastano.get.repository.OrderRepository;
import dev.carloscastano.get.repository.PayMethodRepository;
import dev.carloscastano.get.repository.PayStatusRepository;
import dev.carloscastano.get.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService service;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    PayMethodRepository payMethodRepository;

    @Autowired
    PayStatusRepository payStatusRepository;

    @GetMapping
    public List<Order> getAll() {return service.getAll();}

    @GetMapping("/user/{idUsuario}")
    public List<Order> getOrdersByUser(@PathVariable Long idUsuario) {
        return service.obtenerPedidoUsuario(idUsuario);
    }

    @PostMapping
    public Order crearPedido(@RequestBody Order pedido) {
        return service.crearPedido(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order pedido) {
        Optional<Order> orderData = service.getById(id);
        if (orderData.isPresent()) {
            Order existingOrder = orderData.get();

            // Actualizar campos básicos
            existingOrder.setIdUsuario(pedido.getIdUsuario());
            existingOrder.setFecha(pedido.getFecha());
            existingOrder.setTotal(pedido.getTotal());

            // Manejar estado del pedido
            if (pedido.getEstado() != null) {
                OrderStatus estado = new OrderStatus();
                estado.setIdEstado(pedido.getEstado().getIdEstado());
                existingOrder.setEstado(estado);
            }

            // Manejar pago
            if (pedido.getPago() != null) {
                Pay existingPago = existingOrder.getPago() != null ?
                        existingOrder.getPago() : new Pay();

                existingPago.setFecha_pago(pedido.getPago().getFecha_pago());
                existingPago.setMonto(pedido.getPago().getMonto());

                if (pedido.getPago().getMetodoPago() != null) {
                    PayMethod metodoPago = payMethodRepository.findById(pedido.getPago().getMetodoPago().getIdMetodo())
                            .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
                    existingPago.setMetodoPago(metodoPago);
                }

                if (pedido.getPago().getEstadoPago() != null) {
                    PayStatus estadoPago = payStatusRepository.findById(pedido.getPago().getEstadoPago().getIdEstadoPago())
                            .orElseThrow(() -> new RuntimeException("Estado de pago no encontrado"));
                    existingPago.setEstadoPago(estadoPago);
                }

                existingPago.setPedido(existingOrder);
                existingOrder.setPago(existingPago);
            }

            return new ResponseEntity<>(service.save(existingOrder), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Optional<Order> orderData = service.getById(id);
        if (orderData.isPresent()) {
            Order existingOrder = orderData.get();

            // Actualizar campos básicos
            if (updates.containsKey("idUsuario")) {
                existingOrder.setIdUsuario(Long.valueOf(updates.get("idUsuario").toString()));
            }
            if (updates.containsKey("fecha")) {
                existingOrder.setFecha((Date) updates.get("fecha"));
            }
            if (updates.containsKey("total")) {
                existingOrder.setTotal(Double.valueOf(updates.get("total").toString()));
            }

            // Manejar estado del pedido
            if (updates.containsKey("estado")) {
                Map<String, Object> estadoMap = (Map<String, Object>) updates.get("estado");
                OrderStatus estado = new OrderStatus();
                estado.setIdEstado(Long.valueOf(estadoMap.get("idEstado").toString()));
                existingOrder.setEstado(estado);
            }

            // Manejar pago
            if (updates.containsKey("pago")) {
                Map<String, Object> pagoMap = (Map<String, Object>) updates.get("pago");
                Pay existingPago = existingOrder.getPago() != null ?
                        existingOrder.getPago() : new Pay();

                if (pagoMap.containsKey("fecha_pago")) {
                    existingPago.setFecha_pago((Date) pagoMap.get("fecha_pago"));
                }
                if (pagoMap.containsKey("monto")) {
                    existingPago.setMonto(Double.valueOf(pagoMap.get("monto").toString()));
                }

                // Manejar método de pago
                if (pagoMap.containsKey("metodoPago")) {
                    Map<String, Object> metodoMap = (Map<String, Object>) pagoMap.get("metodoPago");
                    PayMethod metodoPago = payMethodRepository.findById(Long.valueOf(metodoMap.get("idMetodo").toString()))
                            .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
                    existingPago.setMetodoPago(metodoPago);
                }

                // Manejar estado de pago
                if (pagoMap.containsKey("estadoPago")) {
                    Map<String, Object> estadoMap = (Map<String, Object>) pagoMap.get("estadoPago");
                    PayStatus estadoPago = payStatusRepository.findById(Long.valueOf(estadoMap.get("idEstadoPago").toString()))
                            .orElseThrow(() -> new RuntimeException("Estado de pago no encontrado"));
                    existingPago.setEstadoPago(estadoPago);
                }

                existingPago.setPedido(existingOrder);
                existingOrder.setPago(existingPago);
            }

            return new ResponseEntity<>(service.save(existingOrder), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
