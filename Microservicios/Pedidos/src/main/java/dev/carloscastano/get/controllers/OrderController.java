package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Order;
import dev.carloscastano.get.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.findById(id);
        return order.map(o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.findByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Order>> getOrdersByStatusId(@PathVariable Long statusId) {
        List<Order> orders = orderService.findByEstado_IdEstado(statusId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<List<dev.carloscastano.get.entities.OrderDetails>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        Optional<Order> order = orderService.findById(orderId);
        return order.map(o -> new ResponseEntity<>(o.getDetalles(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{orderId}/pay")
    public ResponseEntity<dev.carloscastano.get.entities.Pay> getPayByOrderId(@PathVariable Long orderId) {
        Optional<Order> order = orderService.findById(orderId);
        return order.map(Order::getPago)
                .map(pay -> new ResponseEntity<>(pay, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<Order> createOrder(@Validated @RequestBody Order order) {
        Order newOrder = orderService.save(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Validated @RequestBody Order updatedOrder) {
        Optional<Order> existingOrder = orderService.findById(id);
        if (existingOrder.isPresent()) {
            updatedOrder.setIdPedido(id);
            Order savedOrder = orderService.save(updatedOrder);
            return new ResponseEntity<>(savedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Order> partialUpdateOrder(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Order> existingOrder = orderService.findById(id);
        if (existingOrder.isPresent()) {
            Order orderToUpdate = existingOrder.get();
            Order updatedOrder = orderService.partialUpdate(orderToUpdate, updates);
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.findById(id).isPresent()) {
            orderService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}