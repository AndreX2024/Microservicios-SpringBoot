package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Supplier;

import java.util.List;
import java.util.Optional;

public interface ISupplierService {
    List<Supplier> getAll();
    Optional<Supplier> getByName(String name);
    Supplier saveSupplier(Supplier supplier);
}
