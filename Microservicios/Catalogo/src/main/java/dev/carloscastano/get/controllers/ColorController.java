package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Color;
import dev.carloscastano.get.repository.ColorRepository;
import dev.carloscastano.get.services.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/colors")
public class ColorController {

    @Autowired
    private IColorService service;

    @Autowired
    private ColorRepository colorRepository;

    // Métodos GET
    @GetMapping
    public List<Color> getAll() {
        return service.getAll();
    }

    @GetMapping("/{name}")
    public Optional<Color> getByName(@PathVariable String name) {
        return service.getByName(name);
    }


    // Métodos POST
    @PostMapping
    public Color createColor(@RequestBody Color color) {
        return service.saveColor(color);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Color> updateColor(
            @PathVariable("id") Long id,
            @RequestBody Color color) {

        Optional<Color> colorData = colorRepository.findById(id);

        if (colorData.isPresent()) {
            Color existingColor = colorData.get();

            // Update all fields
            existingColor.setNombre(color.getNombre());

            return new ResponseEntity<>(colorRepository.save(existingColor), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Color> patchColor(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Color> colorData = colorRepository.findById(id);

        if (colorData.isPresent()) {
            Color existingColor = colorData.get();

            // Update only provided fields
            if (updates.containsKey("nombre")) {
                existingColor.setNombre((String) updates.get("nombre"));
            }

            return new ResponseEntity<>(service.saveColor(existingColor), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
