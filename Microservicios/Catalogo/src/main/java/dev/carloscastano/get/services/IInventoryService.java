package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Inventory;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IInventoryService {
    List<Inventory> findAll();
    Optional<Inventory> findById(Long id);
    List<Inventory> findByProductId(Long productId);
    List<Inventory> findBySizeId(Long sizeId);
    List<Inventory> findByColorId(Long colorId);
    Inventory save(Inventory inventory);
    Inventory partialUpdate(Inventory inventory, Map<String, Object> updates);
    void deleteById(Long id);
}