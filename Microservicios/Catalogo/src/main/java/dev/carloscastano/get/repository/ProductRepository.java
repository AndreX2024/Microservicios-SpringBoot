package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByCategoria_IdCategoria(Long categoryId);
    List<Product> findByProveedor_IdProveedor(Long supplierId);
    Optional<Product> findByNombre(String nombre);
}