package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Pay;
import dev.carloscastano.get.services.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pays")
public class PayController {

    @Autowired
    private IPayService payService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<Pay>> getAllPays() {
        return new ResponseEntity<>(payService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pay> getPayById(@PathVariable Long id) {
        Optional<Pay> pay = payService.findById(id);
        return pay.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Pay> getPayByOrderId(@PathVariable Long orderId) {
        Optional<Pay> pay = payService.findByPedido_IdPedido(orderId);
        return pay.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/method/{methodId}")
    public ResponseEntity<List<Pay>> getPaysByMethodId(@PathVariable Long methodId) {
        List<Pay> pays = payService.findByMetodoPago_IdMetodo(methodId);
        return new ResponseEntity<>(pays, HttpStatus.OK);
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<List<Pay>> getPaysByStatusId(@PathVariable Long statusId) {
        List<Pay> pays = payService.findByEstadoPago_IdEstadoPago(statusId);
        return new ResponseEntity<>(pays, HttpStatus.OK);
    }

    @GetMapping("/external/{externalId}")
    public ResponseEntity<Pay> getPayByExternalId(@PathVariable String externalId) {
        Optional<Pay> pay = payService.findByIdPagoExterno(externalId);
        return pay.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<Pay> createPay(@Validated @RequestBody Pay pay) {
        Pay newPay = payService.save(pay);
        return new ResponseEntity<>(newPay, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<Pay> updatePay(@PathVariable Long id, @Validated @RequestBody Pay updatedPay) {
        Optional<Pay> existingPay = payService.findById(id);
        if (existingPay.isPresent()) {
            updatedPay.setIdPago(id);
            Pay savedPay = payService.save(updatedPay);
            return new ResponseEntity<>(savedPay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Pay> partialUpdatePay(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Pay> existingPay = payService.findById(id);
        if (existingPay.isPresent()) {
            Pay payToUpdate = existingPay.get();
            Pay updatedPay = payService.partialUpdate(payToUpdate, updates);
            return new ResponseEntity<>(updatedPay, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        if (payService.findById(id).isPresent()) {
            payService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}