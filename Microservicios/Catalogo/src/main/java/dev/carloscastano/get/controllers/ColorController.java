package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Color;
import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.services.IColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/colors")
public class ColorController {

    @Autowired
    private IColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getAllColors() {
        return new ResponseEntity<>(colorService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id) {
        Optional<Color> color = colorService.findById(id);
        return color.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Color> getColorByNombre(@PathVariable String nombre) {
        Optional<Color> color = colorService.findByNombre(nombre);
        return color.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Color> createColor(@Validated @RequestBody Color color) {
        Color newColor = colorService.save(color);
        return new ResponseEntity<>(newColor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Long id, @Validated @RequestBody Color updatedColor) {
        Optional<Color> existingColor = colorService.findById(id);
        if (existingColor.isPresent()) {
            updatedColor.setIdColor(id);
            Color savedColor = colorService.save(updatedColor);
            return new ResponseEntity<>(savedColor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Color> partialUpdateColor(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Color> existingColor = colorService.findById(id);
        if (existingColor.isPresent()) {
            Color colorToUpdate = existingColor.get();
            Color updatedColor = colorService.partialUpdate(colorToUpdate, updates);
            return new ResponseEntity<>(updatedColor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        if (colorService.findById(id).isPresent()) {
            colorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}