package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.entities.Size;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISizeService {
    List<Size> findAll();
    Optional<Size> findById(Long id);
    Optional<Size> findByNombre(String nombre);
    Size save(Size size);
    Size partialUpdate(Size size, Map<String, Object> updates);
    void deleteById(Long id);
}