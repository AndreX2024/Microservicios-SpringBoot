package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Color;
import dev.carloscastano.get.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorService implements IColorService {

    @Autowired
    private ColorRepository repository;

    @Override
    public List<Color> getAll() {
        return (List<Color>) repository.findAll();
    }

    @Override
    public Optional<Color> getByName(String name) {
        return repository.findByNombre(name);
    }

    @Override
    public Color saveColor(Color color) {
        return repository.save(color);
    }
}

