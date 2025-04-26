package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Category;
import dev.carloscastano.get.repository.CategoryRepository;
import dev.carloscastano.get.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/categories")
public class CategoryController {

    @Autowired
    private ICategoryService service;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAll() {
        return service.getAll();
    }

    @GetMapping("/{name}")
    public Optional<Category> getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("id") Long id,
            @RequestBody Category category) {

        Optional<Category> categoryData = categoryRepository.findById(id);

        if (categoryData.isPresent()) {
            Category existingCategory = categoryData.get();

            // Actualizar todos los campos
            existingCategory.setNombre(category.getNombre());
            existingCategory.setDescripcion(category.getDescripcion());

            return new ResponseEntity<>(categoryRepository.save(existingCategory), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Category> patchCategory(
            @PathVariable("id") Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Category> categoryData = categoryRepository.findById(id);

        if (categoryData.isPresent()) {
            Category existingCategory = categoryData.get();

            // Actualizar solo los campos proporcionados
            if (updates.containsKey("nombre")) {
                existingCategory.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("descripcion")) {
                existingCategory.setDescripcion((String) updates.get("descripcion"));
            }

            return new ResponseEntity<>(service.saveCategory(existingCategory), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
