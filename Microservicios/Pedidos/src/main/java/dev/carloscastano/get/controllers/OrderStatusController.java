package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.OrderStatus;
import dev.carloscastano.get.services.IOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/order-statuses")
public class OrderStatusController {

    @Autowired
    private IOrderStatusService orderStatusService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAllOrderStatuses() {
        return new ResponseEntity<>(orderStatusService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStatus> getOrderStatusById(@PathVariable Long id) {
        Optional<OrderStatus> orderStatus = orderStatusService.findById(id);
        return orderStatus.map(os -> new ResponseEntity<>(os, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<OrderStatus> getOrderStatusByName(@PathVariable String name) {
        Optional<OrderStatus> orderStatus = orderStatusService.findByEstado(name);
        return orderStatus.map(os -> new ResponseEntity<>(os, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{statusId}/orders")
    public ResponseEntity<List<dev.carloscastano.get.entities.Order>> getOrdersByStatusId(@PathVariable Long statusId) {
        List<dev.carloscastano.get.entities.Order> orders = orderStatusService.findOrdersByEstadoId(statusId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<OrderStatus> createOrderStatus(@Validated @RequestBody OrderStatus orderStatus) {
        OrderStatus newOrderStatus = orderStatusService.save(orderStatus);
        return new ResponseEntity<>(newOrderStatus, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<OrderStatus> updateOrderStatus(@PathVariable Long id, @Validated @RequestBody OrderStatus updatedOrderStatus) {
        Optional<OrderStatus> existingOrderStatus = orderStatusService.findById(id);
        if (existingOrderStatus.isPresent()) {
            updatedOrderStatus.setIdEstado(id);
            OrderStatus savedOrderStatus = orderStatusService.save(updatedOrderStatus);
            return new ResponseEntity<>(savedOrderStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<OrderStatus> partialUpdateOrderStatus(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<OrderStatus> existingOrderStatus = orderStatusService.findById(id);
        if (existingOrderStatus.isPresent()) {
            OrderStatus orderStatusToUpdate = existingOrderStatus.get();
            OrderStatus updatedOrderStatus = orderStatusService.partialUpdate(orderStatusToUpdate, updates);
            return new ResponseEntity<>(updatedOrderStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderStatus(@PathVariable Long id) {
        if (orderStatusService.findById(id).isPresent()) {
            orderStatusService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}