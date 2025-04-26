package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Size;

import java.util.List;
import java.util.Optional;

public interface ISizeService {
    List<Size> getAll();
    Optional<Size> getByName(String name);
    Size saveSize(Size size);
}
