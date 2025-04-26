package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Size;
import dev.carloscastano.get.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeService implements ISizeService {

    @Autowired
    private SizeRepository repository;

    @Override
    public List<Size> getAll() {
        return (List<Size>) repository.findAll();
    }

    @Override
    public Optional<Size> getByName(String name) {
        return repository.findByNombre(name);
    }

    @Override
    public Size saveSize(Size size) {
        return repository.save(size);
    }
}

