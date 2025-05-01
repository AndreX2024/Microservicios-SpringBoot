package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Category;
import dev.carloscastano.get.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByNombre(String nombre);
}