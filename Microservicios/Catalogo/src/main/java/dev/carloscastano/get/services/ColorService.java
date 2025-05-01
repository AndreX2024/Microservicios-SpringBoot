package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Color;
import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ColorService implements IColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return (List<Color>) colorRepository.findAll();
    }

    @Override
    public Optional<Color> findById(Long id) {
        return colorRepository.findById(id);
    }

    @Override
    public Optional<Color> findByNombre(String nombre) {
        return colorRepository.findByNombre(nombre);
    }

    @Override
    public Color save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Color partialUpdate(Color color, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Color.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, color, value);
            }
        });
        return colorRepository.save(color);
    }

    @Override
    public void deleteById(Long id) {
        colorRepository.deleteById(id);
    }
}