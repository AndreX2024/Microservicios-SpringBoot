package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Color;

import java.util.List;
import java.util.Optional;

public interface IColorService {
    List<Color> getAll();
    Optional<Color> getByName(String name);
    Color saveColor(Color color);
}
