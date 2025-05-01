package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.entities.Supplier;
import dev.carloscastano.get.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private ISupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Optional<Supplier> supplier = supplierService.findById(id);
        return supplier.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Supplier> getSupplierByNombre(@PathVariable String nombre) {
        Optional<Supplier> supplier = supplierService.findByNombre(nombre);
        return supplier.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@Validated @RequestBody Supplier supplier) {
        Supplier newSupplier = supplierService.save(supplier);
        return new ResponseEntity<>(newSupplier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @Validated @RequestBody Supplier updatedSupplier) {
        Optional<Supplier> existingSupplier = supplierService.findById(id);
        if (existingSupplier.isPresent()) {
            updatedSupplier.setIdProveedor(id);
            Supplier savedSupplier = supplierService.save(updatedSupplier);
            return new ResponseEntity<>(savedSupplier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Supplier> partialUpdateSupplier(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Supplier> existingSupplier = supplierService.findById(id);
        if (existingSupplier.isPresent()) {
            Supplier supplierToUpdate = existingSupplier.get();
            Supplier updatedSupplier = supplierService.partialUpdate(supplierToUpdate, updates);
            return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        if (supplierService.findById(id).isPresent()) {
            supplierService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}