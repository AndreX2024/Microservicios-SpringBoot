package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.PayStatus;
import dev.carloscastano.get.services.IPayStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pay-statuses")
public class PayStatusController {

    @Autowired
    private IPayStatusService payStatusService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<PayStatus>> getAllPayStatuses() {
        return new ResponseEntity<>(payStatusService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayStatus> getPayStatusById(@PathVariable Long id) {
        Optional<PayStatus> payStatus = payStatusService.findById(id);
        return payStatus.map(ps -> new ResponseEntity<>(ps, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PayStatus> getPayStatusByName(@PathVariable String name) {
        Optional<PayStatus> payStatus = payStatusService.findByEstado(name);
        return payStatus.map(ps -> new ResponseEntity<>(ps, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{statusId}/pays")
    public ResponseEntity<List<dev.carloscastano.get.entities.Pay>> getPaysByStatusId(@PathVariable Long statusId) {
        List<dev.carloscastano.get.entities.Pay> pays = payStatusService.findPaysByEstadoId(statusId);
        return new ResponseEntity<>(pays, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<PayStatus> createPayStatus(@Validated @RequestBody PayStatus payStatus) {
        PayStatus newPayStatus = payStatusService.save(payStatus);
        return new ResponseEntity<>(newPayStatus, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<PayStatus> updatePayStatus(@PathVariable Long id, @Validated @RequestBody PayStatus updatedPayStatus) {
        Optional<PayStatus> existingPayStatus = payStatusService.findById(id);
        if (existingPayStatus.isPresent()) {
            updatedPayStatus.setIdEstadoPago(id);
            PayStatus savedPayStatus = payStatusService.save(updatedPayStatus);
            return new ResponseEntity<>(savedPayStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<PayStatus> partialUpdatePayStatus(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<PayStatus> existingPayStatus = payStatusService.findById(id);
        if (existingPayStatus.isPresent()) {
            PayStatus payStatusToUpdate = existingPayStatus.get();
            PayStatus updatedPayStatus = payStatusService.partialUpdate(payStatusToUpdate, updates);
            return new ResponseEntity<>(updatedPayStatus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayStatus(@PathVariable Long id) {
        if (payStatusService.findById(id).isPresent()) {
            payStatusService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}