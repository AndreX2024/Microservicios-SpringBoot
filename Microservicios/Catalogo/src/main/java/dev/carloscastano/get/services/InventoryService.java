package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> findAll() {
        return (List<Inventory>) inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public List<Inventory> findByProductId(Long productId) {
        return inventoryRepository.findByProducto_IdProducto(productId);
    }

    @Override
    public List<Inventory> findBySizeId(Long sizeId) {
        return inventoryRepository.findByTalla_IdTalla(sizeId);
    }

    @Override
    public List<Inventory> findByColorId(Long colorId) {
        return inventoryRepository.findByColor_IdColor(colorId);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory partialUpdate(Inventory inventory, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Inventory.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, inventory, value);
            }
        });
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}