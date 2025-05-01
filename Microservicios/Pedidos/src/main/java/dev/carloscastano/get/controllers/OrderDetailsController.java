package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.OrderDetails;
import dev.carloscastano.get.services.IOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {

    @Autowired
    private IOrderDetailsService orderDetailsService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrderDetails() {
        return new ResponseEntity<>(orderDetailsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrderDetailsById(@PathVariable Long id) {
        Optional<OrderDetails> orderDetails = orderDetailsService.findById(id);
        return orderDetails.map(od -> new ResponseEntity<>(od, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderDetails> orderDetails = orderDetailsService.findByPedido_IdPedido(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<OrderDetails> createOrderDetail(@Validated @RequestBody OrderDetails orderDetails) {
        OrderDetails newOrderDetail = orderDetailsService.save(orderDetails);
        return new ResponseEntity<>(newOrderDetail, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetail(@PathVariable Long id, @Validated @RequestBody OrderDetails updatedOrderDetails) {
        Optional<OrderDetails> existingOrderDetail = orderDetailsService.findById(id);
        if (existingOrderDetail.isPresent()) {
            updatedOrderDetails.setIdDetalle(id);
            OrderDetails savedOrderDetail = orderDetailsService.save(updatedOrderDetails);
            return new ResponseEntity<>(savedOrderDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<OrderDetails> partialUpdateOrderDetail(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<OrderDetails> existingOrderDetail = orderDetailsService.findById(id);
        if (existingOrderDetail.isPresent()) {
            OrderDetails orderDetailToUpdate = existingOrderDetail.get();
            OrderDetails updatedOrderDetail = orderDetailsService.partialUpdate(orderDetailToUpdate, updates);
            return new ResponseEntity<>(updatedOrderDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        if (orderDetailsService.findById(id).isPresent()) {
            orderDetailsService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}