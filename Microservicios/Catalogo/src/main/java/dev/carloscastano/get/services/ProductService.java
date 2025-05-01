package dev.carloscastano.get.services;

import dev.carloscastano.get.entities.Product;
import dev.carloscastano.get.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByNombre(String nombre) {
        return productRepository.findByNombre(nombre);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoria_IdCategoria(categoryId);
    }

    @Override
    public List<Product> findBySupplierId(Long supplierId) {
        return productRepository.findByProveedor_IdProveedor(supplierId);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product partialUpdate(Product product, Map<String, Object> updates) {
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Product.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, product, value);
            }
        });
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}