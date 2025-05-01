package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Category;
import dev.carloscastano.get.entities.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Optional<Category> findByNombre(String nombre);
    Category save(Category category);
    Category partialUpdate(Category category, Map<String, Object> updates);
    void deleteById(Long id);
}