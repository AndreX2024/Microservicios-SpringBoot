package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.entities.Size;
import dev.carloscastano.get.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SizeService implements ISizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> findAll() {
        return (List<Size>) sizeRepository.findAll();
    }

    @Override
    public Optional<Size> findById(Long id) {
        return sizeRepository.findById(id);
    }

    @Override
    public Optional<Size> findByNombre(String nombre) {
        return sizeRepository.findByNombre(nombre);
    }

    @Override
    public Size save(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Size partialUpdate(Size size, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Size.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, size, value);
            }
        });
        return sizeRepository.save(size);
    }

    @Override
    public void deleteById(Long id) {
        sizeRepository.deleteById(id);
    }
}