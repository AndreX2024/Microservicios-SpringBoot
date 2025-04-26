package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.ImageProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageProductRepository extends CrudRepository<ImageProduct, Long> {
    List<ImageProduct> findByProducto_IdProducto(Long idProducto);
}
