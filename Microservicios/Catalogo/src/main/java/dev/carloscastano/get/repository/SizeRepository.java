package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Inventory;
import dev.carloscastano.get.entities.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends CrudRepository<Size, Long> {
    Optional<Size> findByNombre(String nombre);
}