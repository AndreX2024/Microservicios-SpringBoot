package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Supplier;
import dev.carloscastano.get.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return (List<Supplier>) supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> getByName(String name) {
        return supplierRepository.findByNombre(name);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
}
