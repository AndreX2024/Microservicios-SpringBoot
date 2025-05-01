package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Color;
import dev.carloscastano.get.entities.Inventory;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IColorService {
    List<Color> findAll();
    Optional<Color> findById(Long id);
    Optional<Color> findByNombre(String nombre);
    Color save(Color color);
    Color partialUpdate(Color color, Map<String, Object> updates);
    void deleteById(Long id);
}