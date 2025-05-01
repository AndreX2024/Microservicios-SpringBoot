package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.entities.Supplier;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISupplierService {
    List<Supplier> findAll();
    Optional<Supplier> findById(Long id);
    Optional<Supplier> findByNombre(String nombre);
    Supplier save(Supplier supplier);
    Supplier partialUpdate(Supplier supplier, Map<String, Object> updates);
    void deleteById(Long id);
}