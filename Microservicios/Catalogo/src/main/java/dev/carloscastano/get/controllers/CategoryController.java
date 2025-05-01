package dev.carloscastano.get.controllers;

import dev.carloscastano.get.entities.Category;
import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return category.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Category> getCategoryByNombre(@PathVariable String nombre) {
        Optional<Category> category = categoryService.findByNombre(nombre);
        return category.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Validated @RequestBody Category category) {
        Category newCategory = categoryService.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Validated @RequestBody Category updatedCategory) {
        Optional<Category> existingCategory = categoryService.findById(id);
        if (existingCategory.isPresent()) {
            updatedCategory.setIdCategoria(id);
            Category savedCategory = categoryService.save(updatedCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> partialUpdateCategory(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Category> existingCategory = categoryService.findById(id);
        if (existingCategory.isPresent()) {
            Category categoryToUpdate = existingCategory.get();
            Category updatedCategory = categoryService.partialUpdate(categoryToUpdate, updates);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}