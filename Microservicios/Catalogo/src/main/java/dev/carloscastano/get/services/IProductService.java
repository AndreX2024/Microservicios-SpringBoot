package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByNombre(String nombre);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySupplierId(Long supplierId);
    Product save(Product product);
    Product partialUpdate(Product product, Map<String, Object> updates);
    void deleteById(Long id);
}