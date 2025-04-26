package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Size;
import dev.carloscastano.get.repository.SizeRepository;
import dev.carloscastano.get.services.ISizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("catalog/sizes")
public class SizeController {

    @Autowired
    private ISizeService service;

    @Autowired
    private SizeRepository sizeRepository;


    // Métodos GET
    @GetMapping
    public List<Size> getAll() {
        return service.getAll();
    }

    @GetMapping("/{name}")
    public Optional<Size> getByName(@PathVariable String name) {
        return service.getByName(name);
    }


    // Métodos POST
    @PostMapping
    public Size createSize(@RequestBody Size size) {
        return service.saveSize(size);
    }


    // Métodos PUT
    @PutMapping("/{id}/update")
    public ResponseEntity<Size> updateSize(
            @PathVariable("id") Long id,
            @RequestBody Size size) {

        Optional<Size> sizeData = sizeRepository.findById(id);

        if (sizeData.isPresent()) {
            Size existingSize = sizeData.get();

            // Update all fields
            existingSize.setNombre(size.getNombre());

            return new ResponseEntity<>(sizeRepository.save(existingSize), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PatchMapping("/{id}")
    public ResponseEntity<Size> patchSize(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Size> sizeData = sizeRepository.findById(id);

        if (sizeData.isPresent()) {
            Size existingSize = sizeData.get();

            // Update only provided fields
            if (updates.containsKey("nombre")) {
                existingSize.setNombre((String) updates.get("nombre"));
            }

            return new ResponseEntity<>(service.saveSize(existingSize), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
