package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Color;
import dev.carloscastano.get.entities.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends CrudRepository<Color, Long> {
    Optional<Color> findByNombre(String nombre);
}