package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.entities.Supplier;
import dev.carloscastano.get.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> findAll() {
        return (List<Supplier>) supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Optional<Supplier> findByNombre(String nombre) {
        return supplierRepository.findByNombre(nombre);
    }

    @Override
    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier partialUpdate(Supplier supplier, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Supplier.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, supplier, value);
            }
        });
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteById(Long id) {
        supplierRepository.deleteById(id);
    }
}