package dev.carloscastano.get.repository;

import dev.carloscastano.get.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    Optional<Supplier> findByNombre(String nombre);
}

