package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    List<ImageProduct> findByProducto_IdProducto(Long productId);
}