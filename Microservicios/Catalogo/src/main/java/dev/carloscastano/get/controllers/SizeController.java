package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.entities.Size;
import dev.carloscastano.get.services.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sizes")
public class SizeController {

    @Autowired
    private ISizeService sizeService;

    @GetMapping
    public ResponseEntity<List<Size>> getAllSizes() {
        return new ResponseEntity<>(sizeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable Long id) {
        Optional<Size> size = sizeService.findById(id);
        return size.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Size> getSizeByNombre(@PathVariable String nombre) {
        Optional<Size> size = sizeService.findByNombre(nombre);
        return size.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Size> createSize(@Validated @RequestBody Size size) {
        Size newSize = sizeService.save(size);
        return new ResponseEntity<>(newSize, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable Long id, @Validated @RequestBody Size updatedSize) {
        Optional<Size> existingSize = sizeService.findById(id);
        if (existingSize.isPresent()) {
            updatedSize.setIdTalla(id);
            Size savedSize = sizeService.save(updatedSize);
            return new ResponseEntity<>(savedSize, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Size> partialUpdateSize(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Size> existingSize = sizeService.findById(id);
        if (existingSize.isPresent()) {
            Size sizeToUpdate = existingSize.get();
            Size updatedSize = sizeService.partialUpdate(sizeToUpdate, updates);
            return new ResponseEntity<>(updatedSize, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        if (sizeService.findById(id).isPresent()) {
            sizeService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}