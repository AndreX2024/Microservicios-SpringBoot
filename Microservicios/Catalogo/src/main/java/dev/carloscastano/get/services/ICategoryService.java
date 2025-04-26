package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAll();
    Optional<Category> getByName(String name);
    Category saveCategory(Category category);
}
