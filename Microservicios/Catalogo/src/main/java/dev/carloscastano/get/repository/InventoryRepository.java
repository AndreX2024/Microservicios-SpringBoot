package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    List<Inventory> findByProducto_IdProducto(Long productId);
    List<Inventory> findByTalla_IdTalla(Long sizeId);
    List<Inventory> findByColor_IdColor(Long colorId);
}