package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Supplier;
import dev.carloscastano.get.repository.SupplierRepository;
import dev.carloscastano.get.services.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/suppliers")
public class SupplierController {

    @Autowired
    private ISupplierService service;

    @Autowired
    private SupplierRepository supplierRepository;


    // Métodos GET
    @GetMapping
    public List<Supplier> getAll() {
        return service.getAll();
    }

    @GetMapping("/{name}")
    public Optional<Supplier> getByName(@PathVariable String name) {
        return service.getByName(name);
    }


    // Métodos POST
    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return service.saveSupplier(supplier);
    }


    // Métodos PUT
    @PutMapping("/{id}/update")
    public ResponseEntity<Supplier> updateSupplier(
            @PathVariable("id") Long id,
            @RequestBody Supplier supplier) {

        Optional<Supplier> supplierData = supplierRepository.findById(id);

        if (supplierData.isPresent()) {
            Supplier existingSupplier = supplierData.get();

            // Update all fields
            existingSupplier.setNombre(supplier.getNombre());
            existingSupplier.setTelefono(supplier.getTelefono());

            return new ResponseEntity<>(supplierRepository.save(existingSupplier), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Supplier> patchSupplier(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Supplier> supplierData = supplierRepository.findById(id);

        if (supplierData.isPresent()) {
            Supplier existingSupplier = supplierData.get();

            // Update only provided fields
            if (updates.containsKey("nombre")) {
                existingSupplier.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("telefono")) {
                existingSupplier.setTelefono((String) updates.get("telefono"));
            }

            return new ResponseEntity<>(service.saveSupplier(existingSupplier), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

