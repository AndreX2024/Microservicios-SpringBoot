package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.PayMethod;
import dev.carloscastano.get.services.IPayMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pay-methods")
public class PayMethodController {

    @Autowired
    private IPayMethodService payMethodService;

    // Métodos GET
    @GetMapping
    public ResponseEntity<List<PayMethod>> getAllPayMethods() {
        return new ResponseEntity<>(payMethodService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayMethod> getPayMethodById(@PathVariable Long id) {
        Optional<PayMethod> payMethod = payMethodService.findById(id);
        return payMethod.map(pm -> new ResponseEntity<>(pm, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PayMethod> getPayMethodByName(@PathVariable String name) {
        Optional<PayMethod> payMethod = payMethodService.findByMetodo(name);
        return payMethod.map(pm -> new ResponseEntity<>(pm, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{methodId}/pays")
    public ResponseEntity<List<dev.carloscastano.get.entities.Pay>> getPaysByMethodId(@PathVariable Long methodId) {
        List<dev.carloscastano.get.entities.Pay> pays = payMethodService.findPaysByMetodoId(methodId);
        return new ResponseEntity<>(pays, HttpStatus.OK);
    }

    // Métodos POST
    @PostMapping
    public ResponseEntity<PayMethod> createPayMethod(@Validated @RequestBody PayMethod payMethod) {
        PayMethod newPayMethod = payMethodService.save(payMethod);
        return new ResponseEntity<>(newPayMethod, HttpStatus.CREATED);
    }

    // Métodos PUT
    @PutMapping("/{id}")
    public ResponseEntity<PayMethod> updatePayMethod(@PathVariable Long id, @Validated @RequestBody PayMethod updatedPayMethod) {
        Optional<PayMethod> existingPayMethod = payMethodService.findById(id);
        if (existingPayMethod.isPresent()) {
            updatedPayMethod.setIdMetodo(id);
            PayMethod savedPayMethod = payMethodService.save(updatedPayMethod);
            return new ResponseEntity<>(savedPayMethod, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<PayMethod> partialUpdatePayMethod(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<PayMethod> existingPayMethod = payMethodService.findById(id);
        if (existingPayMethod.isPresent()) {
            PayMethod payMethodToUpdate = existingPayMethod.get();
            PayMethod updatedPayMethod = payMethodService.partialUpdate(payMethodToUpdate, updates);
            return new ResponseEntity<>(updatedPayMethod, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Métodos DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayMethod(@PathVariable Long id) {
        if (payMethodService.findById(id).isPresent()) {
            payMethodService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}